FROM openjdk:21-jdk
COPY target/hotel.jar hotel-docker-jar
ENTRYPOINT ["java","-jar","/hotel-docker-jar"]
EXPOSE 8080
ENV SPRING_PROFILES_ACTIVE=docker

