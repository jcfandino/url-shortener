web java $JAVA_OPTS -Ddw.database.url=$JDBC_DATABASE_URL -jar target/url-shortener-app-1.0.0-SNAPSHOT.jar db migrate src/main/resources/config-heroku.yml && java $JAVA_OPTS -Ddw.server.connector.port=$PORT -Ddw.database.url=$JDBC_DATABASE_URL -jar target/url-shortener-app-1.0.0-SNAPSHOT.jar server src/main/resources/config-heroku.yml

