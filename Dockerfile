FROM openjdk:8
EXPOSE 8080
ADD target/Covid19Daily.jar Covid19Daily.jar
ENTRYPOINT [ "java", "-jar", "Covid19Daily.jar" ]
