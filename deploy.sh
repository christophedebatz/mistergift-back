#!/bin/bash

echo "[INFO] Compiling project..."
mvn clean install

echo "[INFO] Uploading ROOT.war..."
rsync -P -vrltD mistergift-api/target/ROOT.war mgadmin@163.172.25.140:/opt/tomcat8/webapps/

echo "[INFO] Tomcat is shutting down..."
ssh mgadmin@163.172.25.140 "/opt/tomcat8/bin/shutdown.sh" 2>/dev/null

echo "[INFO] Tomcat is starting up..."
ssh mgadmin@163.172.25.140 "/opt/tomcat8/bin/startup.sh" 2>/dev/null
