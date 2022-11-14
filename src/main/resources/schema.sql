Create Table if not exists pet(
 pet_id bigint not null auto_increment,
 pet_name varchar(255) not null,
 pet_status varchar(255),
 PRIMARY KEY(pet_id)
);

Create Table if not exists category (
 category_id bigint not null auto_increment,
 pet_id bigint,
 category_name varchar(255),
 PRIMARY KEY (category_id),
 FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);

Create Table if not exists tag(
 tag_id bigint not null auto_increment,
 pet_id bigint,
 tag_name varchar(255),
 PRIMARY KEY(tag_id),
 FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);

Create Table if not exists petPhotoUrl(
 photo_url_id bigint not null auto_increment,
 pet_id bigint,
 photo_url varchar(255),
 PRIMARY KEY(photo_url_id),
 FOREIGN KEY (pet_id) REFERENCES pet(pet_id)
);

Create Table if not exists order_table(
    order_id bigint not null auto_increment,
    order_pet_id bigint,
    quantity bigint,
    ship_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    order_status varchar(255),
    complete_status BIT(1),
    PRIMARY KEY(order_id)
);

