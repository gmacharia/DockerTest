# To change this license header, choose License Headers in Project Properties.
# To change this template file, choose Tools | Templates
# and open the template in the editor.
FROM openjdk:11
ADD target/docker-spring-boot.jar docker-spring-boot.jar
EXPOSE 9098
ENTRYPOINT ["java", "-jar", "docker-spring-boot.jar"]
