# Task cards API
- The api allows users to create and manage tasks in form of cards

## API Constraints
1) Application users are identified uniquely by their mail address, have a role (Member or Admin) and use a password to authenticate
themselves before accessing cards
    - Members have access to cards they created
    - Admins have access to all cards

2) A user creates a card by providing a name for it and, optionally, a description and a color
    - Name is mandatory
    - Color, if provided, should conform to a “6 alphanumeric characters prefixed with a #“ format
    - Upon creation, the status of a card is To Do

3) A user can search through cards they have access to
   - Filters include name, color, status and date of creation
   - Optionally limit results using page & size or offset & limit options
   - Results may be sorted by name, color, status, date of creation

4) A user can request a single card they have access to
5) A user can update the name, the description, the color and/or the status of a card they have access to
    - Contents of the description and color fields can be cleared out
    - Available statuses are To Do, In Progress and Done

6) A user can delete a card they have access to

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
- Look for authentication endpoint and use the above credentials to obtain an auth token.
- Click on `Authorize` button at the top and you will be able to access the other endpoints.