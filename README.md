# Mancala

## Introduction

This game is developed with a Java Backend and a React frontend. Hope you enjoy it!

## Installation 

After cloning the repository, you will notice that you have a back-end folder with the project in java and a front-end folder for the project in react.


## Running Back-end

Keep in mind that you have to have maven installed before trying to start the application. Here is a maven installation guide https://maven.apache.org/install.html. 

The backend is made with Java and Spring Boot, so we have an embedded Tomcat server to be able to deploy it locally. To start de application we can import the project to Intellij and run MancalaApplication class. Another way to start the application is to open a terminal where the pom.xml file is located and execute "mvn spring-boot:run" command. The application wil be running in localhost:8080.

## Endpoints Information

The backend project has swagger, so when you run the backend application, you can check the information of the endpoints in this url http://localhost:8080/swagger-ui/index.html.

## Running Front-end

Keep in mind that you have to have node installed before trying to start the application. Here is a node installation guide https://nodejs.org/es/download/. 

The frontend is made in React. After cloning the repository you need to run the command "npm install" in a terminal inside the project folder. This command will download and install the dependencies needed in the local node_modules. After installing all the dependencias you need to run the command "npm start" this will start the application in localhost:3000

That´s it! Enjoy the game!







