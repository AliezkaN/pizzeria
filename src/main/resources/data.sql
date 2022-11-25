-- -----------------------------------------------------
-- Data for table customers
-- -----------------------------------------------------

insert into customers (id,email,first_name,last_name,phone,address_line) values (default,'alex@gmail.com','Alex','Smith','(066) 666-6666','st. Horodotska, 38');

-- -----------------------------------------------------
-- Data for table ingredients
-- -----------------------------------------------------

insert into ingredients (id,name,price) values (default,'Bechamel sauce',35);
insert into ingredients (id,name,price) values (default,'Mozzarella',60);
insert into ingredients (id,name,price) values (default,'Chicken',45);
insert into ingredients (id,name,price) values (default,'Ham',55);

-- -----------------------------------------------------
-- Data for table pizza
-- -----------------------------------------------------

insert into pizza (id,name) values (default,'Italiana');

insert into pizza_ingredients (pizza_id,ingredients_id) values (1,1);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,2);

