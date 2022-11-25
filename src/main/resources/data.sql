-- -----------------------------------------------------
-- Data for table customers
-- -----------------------------------------------------

insert into customers (id,email,first_name,last_name,phone,address_line) values (default,'alex@gmail.com','Alex','Smith','(066) 666-6666','st. Horodotska, 38');

-- -----------------------------------------------------
-- Data for table ingredients
-- -----------------------------------------------------

insert into ingredients (id,name,price) values (default,'Bechamel sauce',35);
insert into ingredients (id,name,price) values (default,'Pesto sauce',50);
insert into ingredients (id,name,price) values (default,'Mozzarella',60);
insert into ingredients (id,name,price) values (default,'Chicken',45);
insert into ingredients (id,name,price) values (default,'Ham',55);
insert into ingredients (id,name,price) values (default,'Mushrooms',27);
insert into ingredients (id,name,price) values (default,'Paprika',35);
insert into ingredients (id,name,price) values (default,'Сhili pepper',25);
insert into ingredients (id,name,price) values (default,'Onion',15);
insert into ingredients (id,name,price) values (default,'Tomatoes',25);
insert into ingredients (id,name,price) values (default,'Olives',50);
insert into ingredients (id,name,price) values (default,'Corn',22);
insert into ingredients (id,name,price) values (default,'Parmesan cheese',70);
insert into ingredients (id,name,price) values (default,'Pineapples',35);
insert into ingredients (id,name,price) values (default,'Pepperoni',50);
insert into ingredients (id,name,price) values (default,'Arugula',20);
insert into ingredients (id,name,price) values (default,'Dorblu cheese',60);
insert into ingredients (id,name,price) values (default,'Сherry tomatoes',30);


-- -----------------------------------------------------
-- Data for table pizza
-- -----------------------------------------------------

insert into pizza (id,name) values (default,'Italiana');

insert into pizza_ingredients (pizza_id,ingredients_id) values (1,1);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,2);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,3);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,4);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,6);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,18);

insert into pizza (id,name) values (default,'Hawaiian');

insert into pizza_ingredients (pizza_id,ingredients_id) values (2,1);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,3);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,4);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,12);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,14);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,11);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,7);