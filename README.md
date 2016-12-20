# url-shortener
Simple Java based URL Shortener.

## Running on Heroku

There is a Procfile to be able to run it on Heroku.

## Running with Docker

The docker image is built using maven:

    mvn clean package docker:build

Also there's a docker image pushed to the Docker Hub: https://hub.docker.com/r/jcfandino/url-shortener/

## Running standalone app

1. Build the project

    mvn clean install

2. Initialize the database schema

    java -jar target/url-shortener-app-1.0.0-SNAPSHOT.jar db migrate src/main/resources/config-local.yml

3. Run the app

    java -jar target/url-shortener-app-1.0.0-SNAPSHOT.jar server src/main/resources/config-local.yml

