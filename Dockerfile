FROM java:8
EXPOSE 8080
VOLUME /tmp
ADD reader.jar /app.jar
RUN bash -c 'touch /app.jar'
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
ENTRYPOINT java $JVM_OPTS -Dfile.encoding=UTF-8 -jar "/app.jar"
