call mvnw clean install -DskipTests
call docker build -t danaxar/sueldo-microservice .
call docker push danaxar/sueldo-microservice