### mg-back ###
** Introduction **

mg-back is the back-end project of Mister Gifts version 2. It includes Rest API and database access. Used technologies are Java EE 8 with Spring 4 (Core, Security and Data (with JPA)) and QueryDSL. The used DBMS is currently MySQL 5. We have not the need of a node sql database yet. Maybe later.

** Install **

1) Install MySQL, create the schema "mistergift" and launch it

2) Download Tomcat 8: http://tomcat.apache.org/download-80.cgi#8.0.30 (prefer the tar.gz)

3) Start Tomcat server like this,
```
$ ./path/to/tomcat8/bin/startup.sh
```

4) Deploy web app (see bellow)

** Deploy **
```
$ ./path/to/mg-back/deploynow.sh
```

Tendresse & chocolate.
