#!/bin/sh

cd /home/ec2-user/case-yellow/

java -jar case-yellow-gateway.jar > /dev/null 2> /dev/null < /dev/null &
java -jar case-yellow-web.jar > /dev/null 2> /dev/null < /dev/null &