FROM  openjdk:1.8

WORKDIR /opt/

COPY target/oauth2-pwd.jar /opt/

EXPOSE 8080

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-Xms400m","-Xmx400m","-jar","/opt/oauth2-pwd.jar", "--spring.profiles.active=prod"]
