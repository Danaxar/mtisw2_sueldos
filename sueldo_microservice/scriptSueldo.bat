:: Actualizar sueldos
call cd "C:\Users\danie\OneDrive\Escritorio\Técnicas de ingeniería de software\Evaluaciones\Evaluación 2\mtisw2_sueldos\sueldo_microservice"
call git add *
call git commit -m "avance"
call git push
call mvnw clean install -DskipTests
call docker build -t danaxar/sueldo-microservice .
call docker push danaxar/sueldo-microservice