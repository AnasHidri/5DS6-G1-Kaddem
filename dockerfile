FROM openjdk:11
EXPOSE 8089

RUN curl -o kaddem-1.0.11.jar -L "http://nexus:8081/repository/maven-releases/tn/esprit/spring/kaddem/0.0.1/kaddem-0.0.1.jar"

ENTRYPOINT ["java", "-jar", "kaddem-0.0.1.jar"]