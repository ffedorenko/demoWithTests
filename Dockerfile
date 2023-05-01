FROM openjdk:11
ADD target/demo-with-tests.jar demo-with-tests.jar
ENTRYPOINT ["java", "-jar", "demo-with-tests.jar"]