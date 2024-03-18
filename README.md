# SchemaSpy comands

$ docker pull schemaspy/schemaspy

$ java -jar schemaspy.jar -vizjs -t pgsql -db ifconnectdb -host localhost -port 5432 -dp postgresjdbcdriver.jar -u postgres -p postgres -o schemaspy

# How run Ngrok

$ ./ngrok http http://localhost:8080

# Swagger acess

-> http://localhost:8080/swagger-ui/index.html#/

-> The application will be available at http://localhost:8080.

# Railway properties

```properties
spring_profiles_active=prod
PROD_DB_HOST=HOST_HERE
PROD_DB_PORT=POST_HERE
PROD_DB_NAME=railway
PROD_DB_PASSWORD=PASSWORD_HERE
PROD_DB_USERNAME=postgres
PROD_APP_HOST=HOST_HERE
PROD_EMAIL_HOST=smtp.gmail.com
PROD_EMAIL_PORT=465
PROD_EMAIL_USER=USER_HERE
PROD_EMAIL_PASSWORD=PASSWORD_HERE
```
