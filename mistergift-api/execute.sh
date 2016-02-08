#!/bin/bash
# file: execute.sh

export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home

cd /Users/christophedebatz/Documents/workspace/mistergiftsv2/mg-back

cd ./mistergift-data
mvn clean install

cd ../mistergift-api
mvn clean install && mvn tomcat7:run-war-only
