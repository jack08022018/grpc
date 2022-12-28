FROM openjdk:17
MAINTAINER Jack
COPY build/libs/grpc-0.0.1.jar grpc-0.0.1.jar
ENTRYPOINT ["java", "-jar", "grpc-0.0.1.jar"]

#CMD java -Xmx300m -Xms300m -XX:TieredStopAtLevel=1 -noverify -jar microservice-istio-invoicing-0.0.1-SNAPSHOT.jar
