-- -----------------------------------------------------
-- Data for table customers
-- -----------------------------------------------------

insert into customers (email,first_name,last_name,phone,address_line) values ('alex@gmail.com','Alex','Smith','(066) 666-6666','st. Horodotska, 38');

-- -----------------------------------------------------
-- Data for table ingredients
-- -----------------------------------------------------

insert into ingredients (name,price) values ('Bechamel sauce',35);
insert into ingredients (name,price) values ('Pesto sauce',50);
insert into ingredients (name,price) values ('Mozzarella',60);
insert into ingredients (name,price) values ('Chicken',45);
insert into ingredients (name,price) values ('Ham',55);
insert into ingredients (name,price) values ('Mushrooms',27);
insert into ingredients (name,price) values ('Paprika',35);
insert into ingredients (name,price) values ('Сhili pepper',25);
insert into ingredients (name,price) values ('Onion',15);
insert into ingredients (name,price) values ('Tomatoes',25);
insert into ingredients (name,price) values ('Olives',50);
insert into ingredients (name,price) values ('Corn',22);
insert into ingredients (name,price) values ('Parmesan cheese',70);
insert into ingredients (name,price) values ('Pineapples',35);
insert into ingredients (name,price) values ('Pepperoni',50);
insert into ingredients (name,price) values ('Arugula',20);
insert into ingredients (name,price) values ('Dorblu cheese',60);
insert into ingredients (name,price) values ('Сherry tomatoes',30);


-- -----------------------------------------------------
-- Data for table pizza
-- -----------------------------------------------------

insert into pizza (name) values ('Italiana');

insert into pizza_ingredients (pizza_id,ingredients_id) values (1,1);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,2);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,3);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,4);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,6);
insert into pizza_ingredients (pizza_id,ingredients_id) values (1,18);

insert into pizza (name) values ('Hawaiian');

insert into pizza_ingredients (pizza_id,ingredients_id) values (2,1);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,3);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,4);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,12);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,14);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,11);
insert into pizza_ingredients (pizza_id,ingredients_id) values (2,7);