FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/PaymentApiCustomerLogin-0.0.1-SNAPSHOT.jar PaymentApiCustomerLogin.jar
ENTRYPOINT ["java","-jar","PaymentApiCustomerLogin.jar"]