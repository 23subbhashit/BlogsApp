FROM openjdk:17

WORKDIR /app

COPY target/blogs-0.0.1-SNAPSHOT.jar /app/

COPY wait-for-it.sh ./wait-for-it.sh
RUN chmod +x wait-for-it.sh

EXPOSE 9090

CMD ["./wait-for-it.sh", "mysql-container:3306", "--timeout=30", "--", "java", "-jar", "blogs-0.0.1-SNAPSHOT.jar"]
