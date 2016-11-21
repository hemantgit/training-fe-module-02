# Backbase Training Exercises

## Portal Frontend - Module 2: Feed Service

An Apache Camel service allowing you to retrieve and transform XML data from a remote RSS feed.

### Installation

 - If there is no folder called **services** at the root of your project, create it and paste the **feed-service-module** folder in there.

 - Run `mvn clean install` in the **services/feed-service-module** folder.

 - In **webapps/portalserver/pom.xml**, add the following dependency (**hint**: search for the _<dependencies>_ tag that is a direct decendant of the root _<project>_ tag):

  ```xml
  <dependency>
      <groupId>com.backbase.training</groupId>
      <artifactId>feed-service-module</artifactId>
      <version>1.0-SNAPSHOT</version>
  </dependency>
  ```

 - Restart the Portal module.

### Test
 
Make sure the service is successfully installed by visiting the following URLs:

- [http://localhost:7777/portalserver/services/feed/html?url=http://blog.backbase.com/feed](http://localhost:7777/portalserver/services/feed/html?url=http://blog.backbase.com/feed)
- [http://localhost:7777/portalserver/services/rest/feed/json?url=http://blog.backbase.com/feed](http://localhost:7777/portalserver/services/rest/feed/json?url=http://blog.backbase.com/feed)

The value of the **url** parameter can be changed to any RSS feed URL.

### Optional: Proxy Configuration

This exercise uses the recipient list to make a request.
 
Since the URL contains the HTTP protocol reference, it will use the [Camel Http](http://camel.apache.org/http.html) component. If you are behind a firewall, you'll need to supplement the address with proxy configuration.
  
See some examples: 
- Without user and password:  <simple>${header.url}?proxyHost=localhost&amp;proxyPort=8888</simple>
- Providing user information: <simple>${header.url}?proxyAuthMethod=Basic&amp;proxyHost=localhost&amp;proxyPort=8888&amp;proxyAuthUsername=johndoe&amp;proxyAuthPassword=johndoe123</simple>
 
Find also the set of proxy-related parameters:
 
- proxyHost: The proxy host name
- proxyPort: The proxy port number
- proxyAuthMethod: Authentication method for proxy, either as Basic, Digest or NTLM.
- proxyAuthUsername: Username for proxy config
- proxyAuthPassword: Password for proxy config
- proxyAuthDomain: Domain for proxy NTML authentication
- proxyAuthHost: Optional host for proxy NTML authentication
