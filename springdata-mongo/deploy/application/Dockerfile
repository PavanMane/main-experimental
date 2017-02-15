FROM jeanblanchard/java:8
MAINTAINER manep82@gmail.com

RUN mkdir -p DockerImage

# the jar name should match the one in the build.gradle file
ADD dist/moi-user-v0.2.1.jar DockerImage

ENTRYPOINT java -jar DockerImage/moi-user-v0.2.1.jar

EXPOSE 8080

