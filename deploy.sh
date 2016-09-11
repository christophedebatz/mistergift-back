#!/bin/bash

echo "[INFO] Compiling project..."
mvn clean install

echo "[INFO] Uploading ROOT.war..."
rsync -P -vrltD mistergift-api/target/ROOT.war mgadmin@163.172.25.140:/opt/tomcat8/webapps/

if [[ $1 -neq "dev" or $1 -neq "stg" or $1 -neq "prd" ]]; then
    echo "[ERROR] No environment config has been defined..."
else

    echo "[INFO] Set Catalina options as $env"
    ssh mgadmin@163.172.25.140 "export CATALINA_OPTS=\"-Xms512M -Xmx1024M -Denv=$1\""

    #echo [INFO] Computing SQL deltas..."

    echo "[INFO] Tomcat is shutting down..."
    ssh mgadmin@163.172.25.140 "/opt/tomcat8/bin/shutdown.sh" 2>/dev/null

    echo "[INFO] Tomcat is starting up..."
    ssh mgadmin@163.172.25.140 "/opt/tomcat8/bin/startup.sh" 2>/dev/null

fi