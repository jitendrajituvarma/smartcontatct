FROM openjdk:11
ADD smartcontactmanager-0.0.1-SNAPSHOT.jar jitu.jar
EXPOSE 6666
ENTRYPOINT ["java","-jar","jitu.jar"]