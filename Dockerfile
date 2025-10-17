FROM openjdk:25
ADD target/basement-friends.jar basement-friends.jar
ENTRYPOINT ["java","-jar","/AIReconCommand-0.0.1-SNAPSHOT.jar"]