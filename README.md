# Task cards API
- The api allows users to create and manage tasks in form of cards


## How to start the application
- Docker.
    - Change directory to app and run `docker-compose up -d --build` the first time.
    - Or just `docker-compose up -d` if you already have images created. 
    - The second command assumes you have images and containers already build but the first one start by building the images before creating containers from them.
    - Run `docker-compose down` to stop the containers

- Build jar and run.
    - Update database settings in [src/main/resources/application.properties](./src/main/resources/application.properties) to match the database you want to use
    - Change directory to app directory and run `./gradlew clean -x test bootJar` to generate jar file while skipping test
    - Then run `java -jar build/libs/cards-1.0.0.jar` to start the application


## API usage from local machine
- Go to your browser and access the API documentation at 
 [http://localhost:8082/api/v1/cards-openapi.html](http://localhost:8082/api/v1/cards-openapi.html).
- On startup the server creates 2 dummy users an ADMIN user and a MEMBER user.
    - ADMIN user
        - username: admin.admin@yahoo.com
        - password: admin@123
    - MEMBER user
        - username: member.member@yahoo.com
        - password: member@123
- Look for authentication endpoint `/auth/authenticate` and use the above credentials to obtain an auth token.
- Click on `Authorize` button at the top and enter the obtained token, and you will be able to access the other endpoints.

# Run Tests
- Change directory to app directory and run `./gradlew test` to run tests