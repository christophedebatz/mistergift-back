#!/bin/bash
# server-ip: 163.172.25.140

if [[ $1 -ne "dev" || $1 -ne "stg" || $1 -ne "prd" ]]; then
    echo "[ERROR] No environment configuration has been defined..."
else

    echo "[INFO] Compiling project..."
    #cd ../ && mvn clean install && cd deploy/

    echo "[INFO] Uploading ROOT.war..."
    #rsync -P -vrltD ../mistergift-api/target/ROOT.war mgadmin@mistergift.io:/opt/tomcat8/webapps/

    #echo "[INFO] Computing SQL deltas..."
    #rsync -P -vrltD ../mistergift-data/src/main/resources/deltas.sql mgadmin@mistergift.io:/home/mgadmin/
    #ssh -f mgadmin@mistergift.io -L 3307:localhost:3306 -N
    #ssh mgadmin@mistergift.io "mysql -u root -p -h 127.0.0.1 -P 3307 mistergift < /home/mgadmin/deltas.sql"
    #ssh mgadmin@mistergift.io "kill -9 $(ps aux | grep ssh | grep 3307 | awk '{print $2}')"

    echo "[INFO] Set Catalina options as \"-Xms512M -Xmx1024M -Dserver.role=$1\""
    ssh mgadmin@mistergift.io "truncate --size=0 /opt/tomcat8/bin/setenv.sh"
    ssh mgadmin@mistergift.io "echo 'export CATALINA_OPTS=\"\$CATALINA_OPTS -Xms512M -Xmx1024M -Dserver.role=$1\"' > /opt/tomcat8/bin/setenv.sh"

    echo "[INFO] Tomcat is shutting down..."
    ssh mgadmin@mistergift.io "/opt/tomcat8/bin/shutdown.sh" 2>/dev/null

    echo "[INFO] Tomcat is starting up..."
    ssh mgadmin@mistergift.io "/opt/tomcat8/bin/startup.sh" 2>/dev/null

fi
