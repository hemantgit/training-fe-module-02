# Install and configure PTC's

In order to complete exercise 1b, you will need to setup some custom PTC's by following the procedure below. All the necessary files can be found in the [materials](materials/) folder.

## Installation guide

 1. In **hellobundle/src/main**, overwrite the **'java'** folder with the one provided
 2. In **hellobundle/src/main/resources**, copy/paste the **'exercises-ptc-config.xml'** file and the **'wsdl'** folder
 3. In **hellobundle/src/main/webapp/WEB-INF**, copy/paste the **'xslt'** folder
 4. In your terminal, run **'mvn package'** at the root of the _hellobundle_ folder
 5. Add **'exercises-ptc-config.xml'** to the list of PTC configuration files in **portalserver/src/main/webapp/WEB-INF/web.xml**, e.g.:

```xml
<!-- PTC configuration files list. -->
<context-param>
    <param-name>com.backbase.portal.ptc.web.CONFIG_FILE</param-name>
    <param-value>classpath:/ptc-config.xml,classpath:/ptc-launchpad.xml,classpath:/exercises-ptc-config.xml</param-value>
</context-param>
```

 6. run `mvn package` at the root of _portalserver_
 7. restart _portalserver_ (`mvn jetty:run`)
