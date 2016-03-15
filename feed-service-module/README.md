# Backbase Training Exercises

## Portal Frontend - Module 2: Feed Service

An Apache Camel service allowing you to retrieve and transform XML data from a remote RSS feed.

### Installation

 - If there is no folder called **services** at the root of your project, create it and paste the **feed-service-module** folder in there.
 - Run `mvn clean install` in the **services/feed-service-module** folder.
 - In **webapps/portalserver/pom.xml**, add the following dependency (**hint**: search for the line that says _"Include here all Apache Camel services from services module that need to be deployed in final WAR"_):

  ```xml
  <dependency>
      <groupId>com.backbase.training</groupId>
      <artifactId>feed-service-module</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
  ```

 - Restart the Portal module.
