DROP SCHEMA IF EXISTS `ecommerce_project`;

CREATE SCHEMA `ecommerce_project`;

use `ecommerce_project`;


CREATE TABLE user (
    user_name VARCHAR(255)  PRIMARY KEY,
    email VARCHAR(100) NOT NULL UNIQUE, -- Unique email for login
    password VARCHAR(68) NOT NULL, -- Store hashed passwords
    default_address_id int DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Record creation time
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Record last update time
    last_login DATETIME DEFAULT NULL -- Last login time, defaults to NULL
);

CREATE TABLE role (
    user_id VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES user(user_name)
       ON DELETE CASCADE ON update cascade
);
CREATE TABLE profile (
   
    user_id  VARCHAR(255) PRIMARY KEY, -- Foreign key to User
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    date_of_birth DATE,
    gender ENUM('MALE', 'FEMALE'),
    phone_number VARCHAR(15),
    FOREIGN KEY (user_id) REFERENCES User(user_name) ON DELETE CASCADE ON update cascade
);


CREATE TABLE address (
    
	address_id int NOT NULL AUTO_INCREMENT primary key ,
    user_id VARCHAR(255),
    country VARCHAR(100),
    city VARCHAR(100),
    address_line VARCHAR(255),
    postal_code VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES user(user_name) ON DELETE CASCADE ON update cascade
    
)  ;
CREATE TABLE cart (
    cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cart_status ENUM('ACTIVE', 'CHECKEDOUT') ,
    FOREIGN KEY (user_id) REFERENCES user(user_name) ON DELETE CASCADE ON update cascade
);
CREATE TABLE product (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) ,
    price DECIMAL(10, 2) NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE cart_item (
    item_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT ,
    price DECIMAL(10, 2) NOT NULL,
    total_price DECIMAL(10, 2) GENERATED ALWAYS AS (quantity * price) STORED,
    FOREIGN KEY (cart_id) REFERENCES cart(cart_id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES product(product_id)
);
CREATE TABLE orders (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    deliver_address VARCHAR(255) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    payment_method ENUM('CREDIT_CARD', 'CASH_ON_DELIVERY') NOT NULL,
    order_status ENUM('CONFIRMED', 'SHIPPED', 'DELIVERED', 'CANCELLED') NOT NULL
);
Create table user_order(
user_id varchar(255),
order_id bigint,
PRIMARY KEY (user_id, order_id),
 FOREIGN KEY (user_id) REFERENCES user(user_name),
FOREIGN KEY (order_id) REFERENCES orders(id)

);

