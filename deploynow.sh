#!/bin/bash
#file: execute.sh

mvn clean install -DskipTests

cd ./mistergift-data
mvn clean install -DskipTests

cd ../mistergift-api
open http//localhost:8080/mg &
mvn clean install -DskipTests && mvn tomcat7:run-war-only
