# Activity 2

- Author:  Cody Crosby
- Date:  2 November 2025

---

## Introduction

- The 
 
---

## Azure Deployment

 - [Screencast](https://www.loom.com/share/aa0e80a8c6574574a8bbb9b8187f82b8) demonstrating the application running on Azure
 
### 1. Provision the MySQL Database

 1. Log into the Azure Portal and search for **Azure Database for MySQL**
 
 2. Create a **Flexible Server** instance using Azure for Students subscription
 
 3. Configure the database:
    
    - Set database name, admin username, and password
 	
    - Leave defaults for region, version, and workload type
 	
    - Enable public access and add firewall rule `0.0.0.0 – 255.255.255.255`
 
 4. Confirm and create the database
 
 ![Created database](images/azure-db.png)
 
  - Screenshot of the created Azure Database. I began the how-to before seeing the instruction to capture screenshots during the creation process
 
### 2. Initialize the Database Schema

 1. Navigate to **Databases** and find **+ Add** to create the schema
 
 2. Connect using MySQL Workbench and the provided connection details
 
 3. Execute the DDL script to create tables
 
 
 - No image was captured of this step. I began the how-to before seeing the instruction to capture screenshots during the creation process.
 
### 3. Configure the Spring Boot Application

 1. Update `application.properties` with Azure MySQL credentials:
 
 ```properties
spring.datasource.url=jdbc:mysql://cst323contacts.mysql.database.azure.com:3306/cst323activity
spring.datasource.username=cody
spring.datasource.password=Password1
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 ```
 2. Ensure the Java version in pom.xml matches Azure runtime (Java 17)
 
 3. Build the application using Maven and rename the output to app.jar.
 
 ![Maven console output](images/azure-maven.png)
 
### 4. Deploy the Web Application in Azure

 1. Create a Web App: choose Java 17 + Tomcat 9, Windows OS, and code publish
 
 2. Use Advanced Tools (Kudu) to access site/wwwroot and remove existing files
 
 3. Upload app.jar and web.config to the root of wwwroot as zip file
 
  ![Azure web app](images/azure-app.png)
 	
  - Screenshot of the created Azure application
  
  ![Azure wwwroot](images/azure-root.png)
  
  - Kudu console showing uploaded files
  
### 5. Verify Application

 1. Open the web app URL from Azure
 
 2. Confirm the home page loads with proper styling and dynamic navbar
 
 ![App running in Azure](images/azure-phonebook.PNG)
 
 - Test application running in Azure
 
### Challenges Encountered
 
 1. Static Content Not Rendering
 
    - CSS and navbar initially did not appear. IIS/HTTPPlatformHandler was serving files incorrectly. 
     
 2. Server Port Issues
 
    - Using %HTTP_PLATFORM_PORT% in web.config caused a NumberFormatException
 
 3. web.config XML Formatting
 
    - Initial 500.19 error due to malformed XML. Corrected quotes and ensured <httpPlatform> syntax.
 
---

## Heroku Deployment

 - [Screencast](https://www.loom.com/share/aa0e80a8c6574574a8bbb9b8187f82b8) demonstrating the application running on Heroku
 
### 1. Provision the MySQL Database

 1. Log into the Azure Portal and search for **Azure Database for MySQL**
 
 2. Create a **Flexible Server** instance using Azure for Students subscription
 
 3. Configure the database:
    
    - Set database name, admin username, and password
 	
    - Leave defaults for region, version, and workload type
 	
    - Enable public access and add firewall rule `0.0.0.0 – 255.255.255.255`
 
 4. Confirm and create the database
 
 ![Created database](images/azure-db.png)
 
  - Screenshot of the created Azure Database. I began the how-to before seeing the instruction to capture screenshots during the creation process
 
### 2. Initialize the Database Schema

 1. Navigate to **Databases** and find **+ Add** to create the schema
 
 2. Connect using MySQL Workbench and the provided connection details
 
 3. Execute the DDL script to create tables
 
 
 - No image was captured of this step. I began the how-to before seeing the instruction to capture screenshots during the creation process.
 
### 3. Configure the Spring Boot Application

 1. Update `application.properties` with Azure MySQL credentials:
 
 ```properties
spring.datasource.url=jdbc:mysql://cst323contacts.mysql.database.azure.com:3306/cst323activity
spring.datasource.username=cody
spring.datasource.password=Password1
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 ```
 2. Ensure the Java version in pom.xml matches Azure runtime (Java 17)
 
 3. Build the application using Maven and rename the output to app.jar.
 
 ![Maven console output](images/azure-maven.png)
 
### 4. Deploy the Web Application in Azure

 1. Create a Web App: choose Java 17 + Tomcat 9, Windows OS, and code publish
 
 2. Use Advanced Tools (Kudu) to access site/wwwroot and remove existing files
 
 3. Upload app.jar and web.config to the root of wwwroot as zip file
 
  ![Azure web app](images/azure-app.png)
 	
  - Screenshot of the created Azure application
  
  ![Azure wwwroot](images/azure-root.png)
  
  - Kudu console showing uploaded files
  
### 5. Verify Application

 1. Open the web app URL from Azure
 
 2. Confirm the home page loads with proper styling and dynamic navbar
 
 ![App running in Azure](images/azure-phonebook.PNG)
 
 - Test application running in Azure
 
### Challenges Encountered
 
 1. Static Content Not Rendering
 
    - CSS and navbar initially did not appear. IIS/HTTPPlatformHandler was serving files incorrectly. 
     
 2. Server Port Issues
 
    - Using %HTTP_PLATFORM_PORT% in web.config caused a NumberFormatException
 
 3. web.config XML Formatting
 
    - Initial 500.19 error due to malformed XML. Corrected quotes and ensured <httpPlatform> syntax.