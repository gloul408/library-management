FROM openjdk
COPY /target/library-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
