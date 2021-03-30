#Currency exchanger info

## Requirements

To run this application, you will need to have installed:

* Java 15 or Above

## Running the application

The following instructions assume that you are running Kafka as a Docker image.

* Go to the application root
* `docker-compose up -d`

* `./mvnw clean package`

* `java -jar currency-exchange-data/target/currency-exchange-data-0.0.1-SNAPSHOT.jar &`

* `java -jar currency-exchange-info/target/currency-exchange-info-0.0.1-SNAPSHOT.jar &`

And then test curl samples
`curl -s http://localhost:8080/currency-exchange/from/USD/to/RUB`

The application will return:
`{"from":"USD","to":"RUB","ratio":72.79,"commission":0.3}`

`curl -s http://localhost:8080/currency-exchange/from/RUB/to/USD`

The application will return:
`{"from":"RUB","to":"USD","ratio":0.01374,"commission":0.3}`