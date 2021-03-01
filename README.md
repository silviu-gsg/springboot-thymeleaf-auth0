# Journaling App

## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Auth0 Setup](#auth0-setup)
4. [Building and running the application](#building-and-running-the-application)

### General Info
A Demo CRUD App that illustrates an authentication flow for an MVC web application using Spring Boot, Thymeleaf and Auth0

### Technologies
The following main technologies were used:
* [Spring Boot](https://docs.spring.io/spring-boot/docs/2.4.3/reference/htmlsingle/): Version 2.4.3
* [Thymeleaf](https://www.thymeleaf.org/apidocs/thymeleaf/3.0.12.RELEASE/): Version 3.0.12.RELEASE 
* [Auth0](https://auth0.com/)

### Auth0 Setup
* Sign Up for a free account on https://auth0.com/ and customize your domain name, or use the default one 
* Login and go to Applications -> Create Application -> Regular Web Applications -> Java Spring Boot -> Settings
* Rename your App as you wish, and also:
  *   copy your Domain, Client ID and Client Secret into the application yaml property file, in the corresponding properties
  *   set Allowed Callback URLs to "http://localhost:3000/login/oauth2/code/auth0"
  *   set Allowed Logout URLs to "http://localhost:3000/"
  *   set Allowed Web Origins to "http://localhost:3000/"

Now back to the app -> inside application.yaml you must set your Auth0 application configuration properties by replacing:
* ${AUTH0_CLIENT_ID}
* ${AUTH0_CLIENT_SECRET}
* ${AUTH0_DOMAIN}

### Building and running the application

The project uses [Gradle](https://gradle.org) as a build tool. It already contains
`./gradlew` wrapper script, so there's no need to install gradle.

To build and run the project just execute the following command:

```bash
  ./gradlew bootRun
```

It will start the SpringBoot module and use the embedded in-memory H2 database.

After the server is up and running, go to:

```
http://localhost:3000/auth0-demo
```
and either use a Google account to login or create a new one which will be managed by Auth0.
  