# team-sava-front API Gateway for the Team Sava Test project team-sava-api-gateway
1. pull the code from the repository, clean it and build
     mvn clean install
2. run it
     - mvn spring-boot:run
3. test it: in the POSTMAN or Terminal check the three exposed endpoints
     - GET http://localhost:10786/api/users
     - GET http://localhost:10786/api/users/user_id
     - POST http://localhost:10786/api/users 
        curl --location --request POST 'http://localhost:10786/api/users' \
        --header 'Content-Type: application/json' \
        --data-raw '{ "firstName":"Hannah", "surname":"Standmann", "birthday":"1974-07-28"}'
4. try out the getUsers request parameters column and direction
     - curl --location --request GET 'http://localhost:10786/api/users?column=surname&direction=DESC'  
