<div>
  <h1 align="center">AlgaFood API</h1>
</div>

## :question: About
This project has been designed to implement a food delivery app, using the best practices and newest technologies related to Java and Spring. This project is part of the Spring REST Specialist training by Algaworks.

## :white_check_mark: Testing
This project implements the `Maven FailSafe plugin`, that will prevent the application from not being built due to tests failure, so in order to test the application, see the following.

### Unit Tests
```
$ ./mvnw test
```

### Integration Tests
```
$ ./mvnw verify
```

### Building the project without running tests
```
$ ./mvnw clean package
```

## :computer: Technology Stack
- Java 11
- Spring Framework (Boot, Data JPA, Security, JWT)
- Maven
- Docker
- MySQL

## :white_check_mark: Testing
[This is the Postman Collection](https://www.getpostman.com/collections/e01b9cf7fd91bc8ef457) used to test this project's APIs, you can import it to your workspace and start testing as well.

## :clipboard: License
This project is under MIT License, see the [LICENSE](LICENSE) file for more information.
