FROM adoptopenjdk/openjdk11
ADD target/*.jar /app/
WORKDIR /app/
RUN mv *.jar startup.jar
RUN mkdir -p /opt/company/conf/resource-management
VOLUME /var/company/log/resource-management
VOLUME /opt/company/conf/resource-management
ENTRYPOINT ["java", "-jar", "startup.jar", "--spring.config.location=/opt/company/conf/resource-management/application.properties"]