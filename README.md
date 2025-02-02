## Running the application
The recommended way is by running the docker compose file
```docker compose up -d ```

Another way is to create a postgreSQL database and user.
Add them to src/main/resources/application.properties
URL format: jdbc:postgresql://localhost:PORT/DATABASE NAME

## Paths
```
GET /state-populations - returns all state populations
GET /state-populations/<STATE NAME> - return a state's population (case insensitive)  
```
