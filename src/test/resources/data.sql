set referential_integrity false;
delete from prod_category;
delete from product;

insert into product (id, name, calories, proteins, fats, carbohydrates, category) values (10000, 'product #1', 128.5, 12, 5, 45,1);
insert into product (id, name, calories, proteins, fats, carbohydrates, category) values (10001, 'product #2', 1.1, 45, 2, 12,2);


insert into prod_category (id, name) values (200, 'vegetables');
insert into prod_category (id, name) values (201, 'fruits');

set referential_integrity true;