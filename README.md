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
     

### Installation via docker
    coming soon


<!-- USAGE EXAMPLES -->
## Usage
Import POSTMAN requests available in `data` directory.



<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Add docker support


<p align="right">(<a href="#top">back to top</a>)</p>
