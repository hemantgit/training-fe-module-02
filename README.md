# Backbase Training Exercises

## Portal Frontend - Module 2: Widget Extras

In this module, we dive into more advanced widget concepts. We will first look at Camel routes and how they can help us transforming and rendering external content. We will then talk about developing your own ICE templates to render custom content. Finally, we will learn everything about widget chromes and how to develop your own.

### Contents

 - **pf2e1a**: How to consume from a Camel route and g:include the response in a widget ([solution]())
 - **pf2e1b**: How to consume from a Camel route through an ajax call ([solution](solutions/pf2e1b-feed-reader-widget))
 - **pf2e2**: How to create ICE templates ([solution](solutions/pf2e2-content-widget))
 - **pf2e3**: How to create widget chromes ([solution]())

### Apache Camel Service Installation

In order to perform the first two exercises of this module, you will need to install the provided [**feed-service-module**](./feed-service-module), an Apache Camel service allowing you to retrieve and transform XML data from a remote RSS feed:

 - If there is no folder called **services** at the root of your project, create it and paste the **feed-service-module** folder in there
 - Run `mvn clean install` in the **services/feed-service-module** folder
 - In **webapps/portal/pom.xml**, add the following dependency (**hint**: search for the line that says _"Include here all Apache Camel services from services module that need to be deployed in final WAR"_):

   ```xml
   <dependency>
       <groupId>com.backbase.training</groupId>
       <artifactId>feed-service-module</artifactId>
       <version>1.0-SNAPSHOT</version>
   </dependency>
   ```

 - Restart the Portal module
