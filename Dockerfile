ARG jarFile=app.jar
ARG containerPath=/work
ARG port=8080
FROM openjdk:8
MAINTAINER waltertan1988@163.com
COPY ${jarFile} ${containerPath}/${jarFile}
EXPOSE ${port}
CMD java -jar ${containerPath}/${jarName} --server.port=${port}
