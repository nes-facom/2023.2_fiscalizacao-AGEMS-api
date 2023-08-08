FROM eclipse-temurin:20-jdk

WORKDIR /apis
COPY . .
RUN apt-get update
RUN apt install maven -y
RUN mvn clean install -DskipTests

 
CMD ["mvn", "spring-boot:run"]