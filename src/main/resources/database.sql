-- Table: users
DROP TABLE users;
CREATE TABLE users (
  id       INT          IDENTITY(1,1) NOT NULL  PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  usermail VARCHAR(255) NOT NULL,
  name varchar(255) NULL,
  phone varchar(255) null,
  password VARCHAR(255) NOT NULL
)

--   ENGINE = InnoDB;

-- Table: roles
CREATE TABLE roles (
  id   INT          NOT NULL IDENTITY(1,1) PRIMARY KEY,
  name VARCHAR(100) NOT NULL
)
--   ENGINE = InnoDB;

-- Table for mapping user and roles: user_roles
drop table user_roles;
CREATE TABLE user_roles (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  FOREIGN KEY (user_id) REFERENCES users (id),
  FOREIGN KEY (role_id) REFERENCES roles (id),

  UNIQUE (user_id, role_id)
);
--   ENGINE = InnoDB;
DROP TABLE products;
CREATE TABLE products (
  id       INT          IDENTITY(1,1) NOT NULL  PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  categoryId INT null,
  categoryName varchar(255) null,
  price varchar(255) null,
  rating varchar(255) null,
  description varchar(255) null
);

drop table category;
CREATE TABLE category(
  id INT IDENTITY(1,1) NOT NULL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

DROP TABLE basket;
CREATE TABLE basket (
  id       INT          IDENTITY(1,1) NOT NULL  PRIMARY KEY,
  userId INT NULL,
  product INT null,
  quantity INT null
);

DROP TABLE orders;
CREATE TABLE orders(
  id       INT          IDENTITY(1,1) NOT NULL  PRIMARY KEY,
  userId INT NULL,
  userName varchar(255) null,
  productId INT null,
  productName VARCHAR(255) null,
  cost varchar(255) null,
  quantity INT null
);

-- Insert data proselyte 12345678

INSERT INTO users VALUES (1, 'proselyte', 'mail@my.com' , '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
INSERT INTO user_roles VALUES (1, 2);

INSERT INTO category values ('Fridges');
INSERT INTO category values ('Washes');
INSERT INTO category values ('Microwaves');
INSERT INTO category values ('Stoves');
INSERT INTO category values ('Vacuum cleaners');
INSERT INTO category values ('Heaters');
INSERT INTO category values ('Other');

select * from users;
select * from user_roles;
select * from products;
select * from category;
select * from basket;
update user_roles set role_id = 2 where user_id = 1;
update products set name = 'name' where id = 2;
delete category;
delete from category where id = 15;