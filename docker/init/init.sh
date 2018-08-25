#!/bin/bash

cd "$( dirname "$0" )"

mysql -uroot -p$MYSQL_ROOT_PASSWORD < /tmp/init.sql
echo "init.sql - done"

mysqld stop
exit 0


