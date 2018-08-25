#!/bin/bash

HOME=$( cd "$( dirname "$0" )" && pwd )
cd $HOME
CONTAINER=camping-mysql-init

docker run --name=$CONTAINER \
           -v $HOME/data:/var/lib/mysql \
           -v $HOME/init//init.sql:/tmp/init.sql \
           -v $HOME/init/init.sh:/docker-entrypoint-initdb.d/init.sh \
           -t \
           -e MYSQL_ROOT_PASSWORD=root \
           mysql/mysql-server:5.6

docker rm $CONTAINER