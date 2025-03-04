set referential_integrity false;
delete from product_categories;
delete from category;
delete from product;

insert into product (id, name, calories, proteins, fats, carbohydrates) values (10000, 'product #1', 128.5, 12, 5, 45);
insert into product (id, name, calories, proteins, fats, carbohydrates) values (10001, 'product #2', 1.1, 45, 2, 12);
insert into product (id, name, calories, proteins, fats, carbohydrates) values (10002, 'product #3', 100.1, 15, 20, 44);

insert into category (id, name) values (200, 'vegetables');
insert into category (id, name) values (201, 'fruits');
insert into category (id, name) values (202, 'sweets');
insert into product_categories (product_id, category_id) values (10000, 200);
insert into product_categories (product_id, category_id) values (10000, 201);
insert into product_categories (product_id, category_id) values (10001, 201);
insert into product_categories (product_id, category_id) values (10002, 202);

insert into dish (id, name, description) values (1, 'Dish #1', 'Description for dish #1');

insert into ingredient (id, comment, amount, product_id, dish_id) values (1, 'Product #1', 155.5, 10000, 1);
insert into ingredient (id, comment, amount, product_id, dish_id) values (2, 'Product #2', 200.1, 10001, 1);


set referential_integrity true;
