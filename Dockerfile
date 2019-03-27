FROM openjdk:8
MAINTAINER waltertan waltertan1988@163.com
ARG jarFile=app.jar
ARG containerPath=/work
ARG port=8080
ENV jarFile ${jarFile}
ENV containerPath ${containerPath}
ENV port ${port}
COPY ${jarFile} ${containerPath}/${jarFile}
EXPOSE ${port}
CMD java -jar ${containerPath}/${jarFile} --server.port=${port}
