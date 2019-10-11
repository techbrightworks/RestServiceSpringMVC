FROM tomcat:8-jre8
MAINTAINER "srinivasjastijava@gmail.com"
ADD ./target/RestServiceSpringMVCJson.war /usr/local/tomcat/webapps/
EXPOSE 8080