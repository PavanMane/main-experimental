FROM jeanblanchard/java:8
MAINTAINER manep82@gmail.com

RUN mkdir -p DockerImage

# the jar name should match the one in the build.gradle file
ADD build/libs/p1-springdata-mongo-0.2.0.jar DockerImage

ENTRYPOINT java -jar DockerImage/p1-springdata-mongo-0.2.0.jar
EXPOSE 8080

