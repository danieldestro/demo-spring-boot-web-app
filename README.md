# demo spring boot web app

Web UI Template:
 * [AdminLTE](https://adminlte.io/)

Configuration:

 Provide a MySQL DB with name "demo" (localhost:3306)
 Change DB details on application-local.properties file
 
Build:
 
 mvn clean install -P flyway-clean-migrate -Dspring.profiles.active=dev,local
 
Run:
 
 cd demo-spring-boot-web
 mvn spring-boot:run -Dspring.profiles.active=dev,local

 
 Access:
 
 http://localhost:8080/demo/
