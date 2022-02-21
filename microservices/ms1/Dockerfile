FROM openjdk:11

WORKDIR /usr/app/iot

COPY ./build/libs/message_queue_service.jar ./

CMD ["java", "-jar", "message_queue_service.jar"]
