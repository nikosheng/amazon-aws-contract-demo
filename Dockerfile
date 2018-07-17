FROM openjdk:8-jdk-alpine
VOLUME /tmp
RUN mkdir ~/.aws
#RUN echo -e "[default]\naws_access_key_id=AKIAPUWKILATFWO7EJCQ\naws_secret_access_key=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" > ~/.aws/credentials
RUN echo -e "[default]\naws_access_key_id=$ACCESS_KEY_ID\naws_secret_access_key=$SECRET_ACCESS_KEY" > ~/.aws/credentials
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
EXPOSE 8081
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
