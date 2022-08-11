# recipe-manager-service
[![Test and Build](https://github.com/ck29/recipe-manager-service/actions/workflows/build.yml/badge.svg?branch=development)](https://github.com/ck29/recipe-manager-service/actions/workflows/build.yml)

<div id="top"></div>
<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->




<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="##installation-manual">Installation</a></li>
        <li><a href="##integration-test">Integration Test</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project

The `recipe-manager-service` is an application to manage recipes. The operations provided the services can be easily integrated with any UI. 
The service also provides flexibility in performing advanced level filtering of recipes. 

The service is built using spring boot with AWS dynamodb as backing database. Dynamodb being a no-sql database provides flexibility for finding data easily. It can also run as a persitent database on local machine.


<p align="right">(<a href="#top">back to top</a>)</p>



### Built With

The service is build using following frameworks/languages.

* Java
* Junit
* Spring boot
* Git
* Maven
* AWS DynamoDB
* JSON
* Docker
* Python (Integration testing)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

Clone the project and get the prequisites setup. 

### Prerequisites

Make sure the following tools are installed on your local machine.
* Java
  ```sh
  apt-get install openjdk-8-jdk
  ```
* Python
  ```shell
  apt-get install python3
  apt-get install python3-pip
  ```
* Git
  ```shell
  apt-get install git
  ```

* Maven
  ```shell
  apt-get install maven
  ```
* Docker
  ```shell
   apt-get install docker.io
  ```
* AWS CLI
  ```shell
  pip3 install awscli
  aws configure set aws_access_key_id 'some_random_key_without_quotes'
  aws configure set aws_secret_access_key 'some_random_secret_without_quotes'
  ```
### Installation (Manual)
  1. Clone the project.
     ```
     git clone https://github.com/ck29/recipe-manager-service.git
     ```
  2. Clean and build
     ```shell
     cd recipe-manager-service
     mvn clean install
     ```
  3. Start application
     ```shell
     java -jar target/recipe-manager-service-0.0.1-SNAPSHOT.jar
     ```
  4. Open new terminal and navigate to project directory,
     ```shell
     docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -dbPath . -sharedDb
     ```
### Installation using jar
1. Download jar by navigating to Gitactions. Open latest build and download artifact.
2. Extract the zip file and start application
   ```shell
   java -jar recipe-manager-service-0.0.1-SNAPSHOT.jar
   ```
3. Open new terminal and navigate to project directory,
   ```shell
   docker run -p 8000:8000 amazon/dynamodb-local -jar DynamoDBLocal.jar -dbPath . -sharedDb
   ```

### Installation via docker
    coming soon
### Integration Test
Make sure the database and application is running before starting the integration tests.

1. Create table
   ```shell
   cd recipe-manager-service
   aws dynamodb create-table --cli-input-json file://data/data_model/recipes.json --endpoint-url http://localhost:8000
   ```
2. Run tests
   ```
   python3 integration/tests/integration_tests.py
   ```


<!-- USAGE EXAMPLES -->
## Usage

1. Once the application is running, we can query the API using various method. The details about the endpoints are available using openapi specification. The specification can be downloaded using following link.
   
    [OAS](https://github.com/ck29/recipe-manager-service/blob/master/data/swagger/swagger.yml)


2. Run application using POSTMAN. Download and import POSTMAN package.

   [Postman package](https://github.com/ck29/recipe-manager-service/blob/master/data/postman/Recipe%20Management.postman_collection.json)

### Add Recipe
```shell
   POST /recipe/ HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   
   {
    "name": "popcorn",
    "type": "veg",
    "ingredients": [
        "corn",
        "salt"
    ],
    "serves": 4,
    "instructions": ""

}
   ```

### Get Recipe


1. Get all veg recipes.
   ```shell
   GET /recipe/ HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   ```
2. Get recipe named "omlette".
    ```shell
   GET /recipe/omlette HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   ```

4. Serves 1 person and contains mushroom.
   ```shell
   GET /recipe?maximum-serves=1&ingredients-contains=mushroom HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   ```

5. Doesn't contain potato and instruction has oven.
    ```shell
   GET /recipe/ HTTP/1.1
   Host: localhost:8080?ingredients-not-contains=potato&instructions-contains=oven
   Content-Type: application/json
   ```
   
### Update Recipe
  ```shell
   PUT /recipe/omlette HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   {
    "name": "omlette",
    "type": "non veg",
    "ingredients": [
        "eggs",
        "onions",
        "salt"
    ],
    "serves": 1,
    "instructions": "fry on pan."

  }
   ```

### Delete Recipe
```shell
   DELETE /recipe/omlette HTTP/1.1
   Host: localhost:8080
   Content-Type: application/json
   ```


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Add docker support


<p align="right">(<a href="#top">back to top</a>)</p>
