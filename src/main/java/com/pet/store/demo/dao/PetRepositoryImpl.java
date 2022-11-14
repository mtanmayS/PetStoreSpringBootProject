package com.pet.store.demo.dao;

import com.pet.store.demo.exception.InvalidDataException;
import com.pet.store.demo.exception.InvalidInputException;
import com.pet.store.demo.model.*;
import com.pet.store.demo.request.PetCategory;
import com.pet.store.demo.request.PetRequest;

import com.pet.store.demo.request.Tags;
import com.pet.store.demo.response.PetResponse;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class PetRepositoryImpl implements PetRepository{

    private static final String INSERT_INTO_PET="Insert into pet(pet_name,pet_status) values(?,?)";
    private static final String DELETE_PET="Delete from pet where pet_id=?";
    private static final String DELETE_PET_CATEGORY="Delete from category where pet_id=?";
    private static final String DELETE_PET_TAG="Delete from tag where pet_id=?";
    private static final String DELETE_PET_PHOTO_URL="Delete from petPhotoUrl where pet_id=?";
    private static final String INSERT_INTO_CATEGORY="Insert into category(pet_id,category_name) values(?,?)";

    private static final String INSERT_INTO_TAG="Insert into tag(pet_id,tag_name) values(?,?)";
    private static final String INSERT_INTO_PET_PHOTO_URL="Insert into petPhotoUrl(pet_id,photo_url) values(?,?)";

    private static final String UPDATE_PET="Update pet set pet_name=?,pet_status=? where pet_id=?";
    private static final String UPDATE_PET_CATEGORY="Update category set category_name=? where pet_id=?";
    private static final String UPDATE_PET_TAG="Update tag set tag_name=? where tag_id=?";



    @Autowired
    private JdbcTemplate jdbcTemplate;

    KeyHolder keyHolder = new GeneratedKeyHolder();

    @Override
    public PetResponse savePet(PetRequest pet) {
        final long petId;
        final long categoryId;
        long tagId;
        List<Long> tagIds = new ArrayList<>();
        Set<Tags> savedTagSet= new HashSet<>();
       try{

            //jdbcTemplate.update(INSERT_INTO_PET,pet.getName(), pet.getStatus().name());
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement(INSERT_INTO_PET,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setNString(1,pet.getName());
                ps.setNString(2,pet.getStatus().name());

                return ps;
            }, keyHolder);
           petId =keyHolder.getKey().longValue();
        //jdbcTemplate.update(INSERT_INTO_CATEGORY,petid, pet.getCategory().getName());
           jdbcTemplate.update(connection -> {
               PreparedStatement ps = connection.prepareStatement(INSERT_INTO_CATEGORY,
                       Statement.RETURN_GENERATED_KEYS);
               ps.setLong(1,petId);
               ps.setNString(2,pet.getCategory().getName());

               return ps;
           }, keyHolder);
           categoryId =keyHolder.getKey().longValue();
        Set<Tags> tagSet= new HashSet<>();

        Tags savedTags= new Tags();
           tagSet=pet.getTags();
           for(Tags tag: tagSet){
               // jdbcTemplate.update(INSERT_INTO_TAG,petid, tag.getName());
               jdbcTemplate.update(connection -> {
                   PreparedStatement ps = connection.prepareStatement(INSERT_INTO_TAG,
                           Statement.RETURN_GENERATED_KEYS);
                   ps.setLong(1,petId);
                   ps.setNString(2,tag.getName());

                   return ps;
               }, keyHolder);
               tagId =keyHolder.getKey().longValue();
               savedTags=persistedTag(tag,tagId);
               savedTagSet.add(savedTags);
               //tagIds.add(tagId);
           }
           pet.getPhotoUrls().forEach(url->jdbcTemplate.update(INSERT_INTO_PET_PHOTO_URL,petId,url));

           //jdbcTemplate.update(INSERT_INTO_PET_PHOTO_URL,petid,pet.);


        }catch (Exception e){

            throw new InvalidDataException("Invalid Input");
        }


        return savedPet(pet,petId,categoryId,savedTagSet);
    }

    private Tags persistedTag(Tags tag,long tagId){
        Tags newTags= new Tags();
        newTags.setId(tagId);
        newTags.setName(tag.getName());

        return  newTags;
    }

    private PetResponse savedPet(PetRequest pet,long petId,long categoryId,Set<Tags> savedTagSet){
        PetResponse petResponse = new PetResponse();
        PetCategory category= new PetCategory();
       Set<Tags> tagSet= new HashSet<>();

        category.setId(categoryId);
        category.setName(pet.getCategory().getName());
        petResponse.setCategory(category);
        petResponse.setName(pet.getName());
        petResponse.setStatus(pet.getStatus());
        petResponse.setId(petId);
        petResponse.setTags(savedTagSet);
        petResponse.setPhotoUrls(pet.getPhotoUrls());



        return petResponse;
    }

    @Override
    public PetResponse updatePet(PetRequest petRequest) {

        jdbcTemplate.update(UPDATE_PET_CATEGORY,petRequest.getCategory().getName(),petRequest.getId());
        List<Tag> tag= jdbcTemplate.query("Select* from tag where pet_id=?",
                new Object[]{petRequest.getId()},(rs, rowNum) ->
                        new Tag(rs.getLong("tag_id"),
                                rs.getLong("pet_id"),
                                rs.getString("tag_name")));
        Set<Tag> tagSet= new LinkedHashSet<>();
        tagSet.addAll(tag);
        tagSet.forEach(tags->jdbcTemplate.update(UPDATE_PET_TAG,tags.getName(),tags.getId()));

        petRequest.getPhotoUrls().forEach(url->jdbcTemplate.update(INSERT_INTO_PET_PHOTO_URL,petRequest.getId(),url));

        jdbcTemplate.update(UPDATE_PET,petRequest.getName(),petRequest.getStatus().name(),petRequest.getId());


        return getPetById(petRequest.getId());
    }

    @Override
    public PetResponse getPetById(long id) {

//        String sql="SELECT pet.pet_id,pet.pet_name,pet.pet_status,category.category_id," +
//                "category.category_name,tag.tag_id,tag.tag_name,petphotourl.photo_url " +
//                "From (((petInner Join category on pet.pet_id=category.pet_id) Inner join tag on pet.pet_id= tag.pet_id) " +
//                "Inner join petphotourl on pet.pet_id= petphotourl.pet_id)";


        Category category=jdbcTemplate.queryForObject("Select* from category where pet_id=?",
                new Object[]{id},(rs, rowNum) ->
                        new Category(rs.getLong("category_id"),
                              rs.getLong("pet_id"),
                                rs.getString("category_name")));

        List<Tag> tag= jdbcTemplate.query("Select* from tag where pet_id=?",
                new Object[]{id},(rs, rowNum) ->
                        new Tag(rs.getLong("tag_id"),
                                rs.getLong("pet_id"),
                                rs.getString("tag_name")));
        Set<Tag> tagSet= new LinkedHashSet<>();
        tagSet.addAll(tag);
        List<PetPhotoUrl> photoUrls= jdbcTemplate.query("Select* from petPhotoUrl where pet_id=?",
                new Object[]{id},(rs, rowNum) ->
                        new PetPhotoUrl(rs.getLong("photo_url_id"),
                                rs.getLong("pet_id"),
                                rs.getString("photo_url")));

        Pet pet= jdbcTemplate.queryForObject("Select* from pet where pet_id=?",
                new Object[]{id},(rs, rowNum) ->
                        new Pet(rs.getLong("pet_id"),
                                rs.getString("pet_name"),
                                rs.getString("pet_status")));


        return fetchPet( category,tagSet, photoUrls, pet);
    }

    private PetResponse fetchPet(Category category,Set<Tag> tagSet,List<PetPhotoUrl> photoUrls, Pet pet){
        PetResponse petResponse = new PetResponse();
        PetCategory petCategory= new PetCategory();
        List<String> photos = new ArrayList<>();
        Set<Tags> tags= new LinkedHashSet<>();

        petResponse.setId(pet.getId());
        petResponse.setName(pet.getName());
        petResponse.setStatus(StatusEnum.valueOf(pet.getStatus()));
        petCategory.setId(category.getId());
        petCategory.setName(category.getName());
        petResponse.setCategory(petCategory);
        photoUrls.forEach(url->photos.add(url.getName()));
        petResponse.setPhotoUrls(photos);
       for(Tag tag:tagSet){
           Tags tags1= new Tags();
           tags1.setName(tag.getName());
           tags1.setId(tag.getId());
           tags.add(tags1);
       }
        petResponse.setTags(tags);
        return petResponse;
    }

    @Override
    public String deletePetById(long id) {
        try{
            jdbcTemplate.update(DELETE_PET_CATEGORY,id);
            jdbcTemplate.update(DELETE_PET_TAG,id);
            jdbcTemplate.update(DELETE_PET_PHOTO_URL,id);
        jdbcTemplate.update(DELETE_PET,id);
    }catch (Exception e){

       throw new InvalidDataException("Invalid Input");
    }
        return "Pet Deleted";
    }

    public PetResponse updatePet(long id,String name, String status){

        try{
            jdbcTemplate.update(UPDATE_PET,name,status,id);
            return  getPetById(id);
        }catch (Exception e){
            throw new InvalidInputException("Invalid Input");
        }



    }


    public List<PetResponse> fetchPetByTags(List<String> tags) {
        List<Tag> tagList= new ArrayList<>();
        List<PetResponse> petResponses= new ArrayList<>();
        PetResponse petResponse= new PetResponse();
        for(String tag:tags){


       List<Tag> fetchedTag= jdbcTemplate.query("Select* from tag where tag_name=?",
                new Object[]{tag},(rs, rowNum) ->
                        new Tag(rs.getLong("tag_id"),
                                rs.getLong("pet_id"),
                                rs.getString("tag_name")));

            tagList.addAll(fetchedTag);

        }
        if(tagList.size()>0){
            for(Tag tag:tagList){
                petResponse= getPetById(tag.getPetId());
                petResponses.add(petResponse);

            }
            return petResponses;
        }else{
            throw new InvalidDataException("Invalid Tag Value");
        }


    }
}
