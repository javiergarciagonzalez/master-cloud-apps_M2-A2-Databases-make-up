# master-cloud-apps_M2-A2-Databases-make-up
Learning Database concepts with SpringData and MySQL and NodeJS + MongoDB.

## Java App

### Requirements

An instance of MySQL 8 must be running on your system for the application to work.
The easiest way to get a MySQL instance up and running is running its official Docker image:

``` bash
docker run --name practica-recuperacion-2 -d -e MYSQL_ROOT_USER=root -e MYSQL_USER=root -e MYSQL_HOST=localhost -e MYSQL_DATABASE=test -e MYSQL_ROOT_PASSWORD=pass mysql:8.0
```

### Run

Once connection to MySQL has been established, app can be ran using Maven:

``` bash
mvn spring-boot:run
```

Note: This command needs to be ran on the same directory as `pom.xml` file is located. For this project: `<absolute-path-to-this-project>/Java/`.


## Node App

### Requirements

An instance of MongoDB must be running on your system for the application to work.
The easiest way to get a MongoDB instance up and running is running its official Docker image:

``` bash
docker run --name mongo-db  -p 27017:27017 -d mongo:latest
```

In addition to the DB instance, the application needs all its dependencies to be installed before the running it. For doing so, run:

```bash
npm i
```

Note: Npm needs to be installed globally on your system. More info [here](https://docs.npmjs.com/downloading-and-installing-node-js-and-npm).

### Run

Once connection to MongoDB has been established and all NodeJS dependencies have been installed, the app can be ran using NPM:

``` bash
npm start
```

Note: This command needs to be ran on the same directory as `package.json` file is located. For this project: `<absolute-path-to-this-project>/Node/`.

## Docs

There are postman collections exported for these 2 projects. Also, for the Java app, there is available Swagger documentation.

For Java project, check `/docs` folder. For Node project, check `/postman/` folder.

### Postman
Import the collection json file in your Postman application. Also

### Swagger
Run the project and go to [http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config](http://localhost:8080/swagger-ui/index.html?configUrl=/api-docs/swagger-config)


#### Author
Javier García González <javier@garciagon.com>
