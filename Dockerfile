FROM tomcat:8.5.82-jdk11-corretto-al2
LABEL maintainer="viktoria.konstantinova2015@yandex.ru"

ADD /target/app.war /usr/local/tomcat/webapps/

EXPOSE 8080
CMD ["catalina.sh", "run"]