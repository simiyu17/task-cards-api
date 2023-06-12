FROM eclipse-temurin:17-alpine
COPY . /home/Cards
WORKDIR /home/Cards
RUN ./gradlew clean -x test bootJar
RUN mv /home/Cards/build/libs/cards-1.0.0.jar /home/cards-1.0.0.jar && rm -rf /home/Cards
ENTRYPOINT ["java"]
CMD ["-jar", "/home/cards-1.0.0.jar"]
EXPOSE 8082
