# Mvn Build Stage
FROM maven:3.8.6-openjdk-21 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

# Jar Package Stage (using openjdk-21)
FROM openjdk:21-jre-slim
COPY --from=build /home/app/target/StudentListSecureDB-0.0.1-SNAPSHOT.jar /usr/local/lib/studentlistsecuredb.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/studentlistsecuredb.jar"]
