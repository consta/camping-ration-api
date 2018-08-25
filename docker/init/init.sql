create user 'camping'@'%' identified by 'sql';
create database camping;
grant all on camping.* to 'camping'@'%';
flush privileges;


