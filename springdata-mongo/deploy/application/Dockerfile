FROM jeanblanchard/java:8
MAINTAINER pavan.mane@novopay.in

RUN mkdir -p DockerImage

# the jar name should match the one in the build.gradle file
ADD dist/moi-user-v0.1.0.jar DockerImage

ENTRYPOINT java -jar DockerImage/moi-user-v0.1.0.jar

EXPOSE 8080

