# Forbes-OA

For the coding challenge Java Spring Boot was used.

Prerequisites:
  * Latest Java Development Kit (JDK)
  * Apache Maven 3.5 or higher 
  
To Run:
  * Open a terminal and navigate to the root directory of your Spring Boot API project.
  * Run `mvn clean package`. This will build a JAR file.
  * Run `java -jar target/coding.jar`
  * To test the API on http://localhost:8080/ either postman or browser can be used

 Plan to deploy:
  * There are many cloud platforms that support deploying Spring Boot applications, such as Amazon Web Services (AWS), Microsoft Azure, and Google Cloud Platform. These platforms typically offer a range of options for deploying and scaling your application, such as containers (Docker), virtual machines, or serverless functions. We have to create an instance on one of the cloud platforms, ssh into the instance to copy the jar file and use the run command from above to start the API server. THis is more scalable option, if required multiple instances can be run and traffic can be routed based on the load a server can take.
