set referential_integrity false;
delete from product_categories;
delete from categories;
delete from products;

insert into products (id, name, calories, proteins, fats, carbohydrates) values (10000, 'product #1', 128.5, 12, 5, 45);
insert into categories (id, name) values (200, 'vegetables');
insert into categories (id, name) values (201, 'fruits');
insert into product_categories (product_id, category_id) values (10000, 200);
insert into product_categories (product_id, category_id) values (10000, 201);
set referential_integrity true;