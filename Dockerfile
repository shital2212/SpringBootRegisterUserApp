FROM openjdk:17               
ADD SpringBootBasicWebApp-4UserDemo-1-0.0.1-SNAPSHOT.jar SpringBootBasicWebApp-4UserDemo-1-0.0.1-SNAPSHOT.jar 
ENTRYPOINT ["java","-jar","SpringBootBasicWebApp-4UserDemo-1-0.0.1-SNAPSHOT.jar"]   
EXPOSE 8080  