# Build Stage - Maven Build
FROM maven:3.8.6-eclipse-temurin-21-focal AS build
WORKDIR /home/app
COPY pom.xml /home/app
COPY src /home/app/src
RUN mvn clean package -DskipTests

# Runtime Stage - Java Runtime
FROM eclipse-temurin:21-jre-focal
# Replace 'demo-0.0.1-SNAPSHOT.jar' with your artifactId and version from pom.xml
COPY --from=build /home/app/target/demo-0.0.1-SNAPSHOT.jar /usr/local/lib/demo.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/demo.jar"]
