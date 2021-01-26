# Qualification task for int20h 2021
This app can _collect_, _process_ and _provide_ data about buckwheat and other products in such stores:

`Metro`, 
`Екомаркет`, 
`Novus`, 
`Ашан`, 
`Varus`, 
`CityMarket`, 
`MegaMarket`, 
`Фуршет`. 

#### The app is using https://stores-api.zakaz.ua API.

### Heroku
Link: https://int20h-2021-test-task.herokuapp.com/

### Endpoints
[GET /products/search](https://music-akinator-int20h.herokuapp.com/products/search) - provides products that was described in request

[GET /products/chart](https://music-akinator-int20h.herokuapp.com/products/chart) - provides a chart data with average price per kg for buckwheat

[GET /service/collect-data](https://music-akinator-int20h.herokuapp.com/service/collect-data) - scanning stores and saving data about buckwheat in DB for performance reason(this request performs automatically every 4 hours)

### Project setup
There are should be already installed JRE 8+, Maven 3.2+, PostgreSQL 11+ on the local machine to run this project.

`git clone https://github.com/tezzi2001/int20h-2021-test-task.git`

`mvn clean install`

`java -jar target/testtask-1.0.0.war`

All this commands you can execute from your IDE instead of a terminal.
