# Qualification task for int20h 2021 💥
This app can _collect_, _process_ and _provide_ data about buckwheat and other products in such stores:

`Metro`, `Екомаркет`, `Novus`, `Ашан`, `Varus`, `CityMarket`, `MegaMarket`, `Фуршет`. 

#### The app is using https://stores-api.zakaz.ua API.

### Demo 🚀
[Backend API](https://int20h-2021-test-task.herokuapp.com)

[Frontend](https://int20h-buckwheat.herokuapp.com)

### Endpoints ✔
[GET /products/search](https://int20h-2021-test-task.herokuapp.com/products/search) - provides products that was described in request

[GET /products/chart](https://int20h-2021-test-task.herokuapp.com/products/chart) - provides a chart data with average price per kg for buckwheat

[GET /service/collect-data](https://int20h-2021-test-task.herokuapp.com/service/collect-data) - scanning stores and saving data about buckwheat in DB for performance reason (this request performs automatically every 4 hours)

[GET /service/wake-up-poll](https://int20h-2021-test-task.herokuapp.com/service/wake-up-poll) - waking up server as it's running in free mode (this request performs automatically every 25 minutes)

### Project setup 🚚
There are should be already installed [JRE 8+](https://www.java.com/ru/download/manual.jsp), [Maven 3.2+](https://maven.apache.org/download.cgi), [PostgreSQL 11+](https://www.postgresql.org/download/) on the local machine to run this project. 

Open PostgreSQL console and execute command:

`create schema int20h2021;` - create new DB schema

Create the file _src/main/resources/application-dev.properties_ and add necessary properties to it. For example:

```
server.port=${PORT:8080}
host=http://localhost:8080
spring.jackson.serialization.FAIL_ON_EMPTY_BEANS=false

spring.main.banner-mode=off
spring.datasource.initialization-mode=always
spring.datasource.platform=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=un
spring.datasource.password=pw

spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.default_schema=int20h2021

spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

Open terminal in project's root folder and enter these commands:

`$ git clone https://github.com/tezzi2001/int20h-2021-test-task.git` - download project from GitHub

`$ mvn clean install` - build project

`$ java -jar target/testtask-1.0.0.war` - run project

All this commands you can execute from your IDE instead of a terminal.

### Frontend repo 🏛
Check it [here](https://github.com/kaguya3222/int20h-buckwheat).
