# Task cards API
- The api allows users to create and manage tasks in form of cards


## How to start the application
- Docker.
    - Change directory to app and run `docker-compose up -d --build` the first time or just
     `docker-compose up -d`.
    - The second command assumes you have images and containers already build but the first one start by building the images before creating containers from them.

- Build jar and run.
    - Update database settings in [src/main/resources/application.properties](./src/main/resources/application.properties) to match the database you want to use
    - Change directory and run `./gradlew clean -x test bootJar`
    - Then run `java -jar build/libs/cards-1.0.0.jar`

- From the IDE.
    - Update database settings in [src/main/resources/application.properties](./src/main/resources/application.properties) to match the database you want to use
    - If your IDE can run a spring boot application, then run it from IDE.

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