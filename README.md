# springboot-thymeleaf-auth0
Demo MVC project with Spring Boot, Thymeleaf and Auth0

* Sign Up for a free account on https://auth0.com/ and customize your domain name, or use the default one 
* Login and go to Applications -> Create Application -> Regular Web Applications -> Java Spring Boot -> Settings
* Here you can rename your App as you wish, and also you must:
  *   copy your Domain, Client ID and Client Secret into the application yaml property file, in the corresponding properties
  *   set Allowed Callback URLs to "http://localhost:3000/login/oauth2/code/auth0"
  *   set Allowed Logout URLs to "http://localhost:3000/"
  *   set Allowed Web Origins to "http://localhost:3000/"
  