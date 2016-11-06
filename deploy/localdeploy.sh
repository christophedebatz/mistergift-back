#!/bin/bash

cd ../mistergift-api/ && mvn clean install -DskipTests && mvn tomcat7:run-war -P dev
open http//localhost:8080/mg &