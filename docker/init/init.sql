create user 'camping'@'%' identified by 'sql';
create database camping character set UTF8 collate utf8_bin;
grant all on camping.* to 'camping'@'%';
flush privileges;


