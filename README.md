# Backbase Training Exercise

## Portal Frontend - Module 2: Widget Extras

In this module, we dive into more advanced widget concepts. We will first look at pipes and how they can help us transforming and rendering external content. We will then talk about developing your own ICE templates to render custom content. Finally, we will learn everything about widget chromes and how to develop your own.

### Contents

 - **pf2e1a**: How to use g:include ([solution](cxp-fe-training-02/src/main/webapp/static/cxp-fe-training-02/widgets/pf2e1a-feed-reader))
 - **pf2e1b**: How to use proxy pipes ([solution](cxp-fe-training-02/src/main/webapp/static/cxp-fe-training-02/widgets/pf2e1b-feed-reader))
 - **pf2e2**: How to create ICE templates ([solution](cxp-fe-training-02/src/main/webapp/static/cxp-fe-training-02/widgets/pf2e2-content))
 - **pf2e3**: How to create widget chromes ([solution](cxp-fe-training-02/src/main/webapp/static/cxp-fe-training-02/html/chromes))

### Installation & Configuration

 -  Copy and paste the **"cxp-fe-training-02"** folder in the **"bundles"** folder of your Launchpad 0.11.x project
 - If it is not already there, add the `bundles.dir` property as a new property in portalserver/pom.xml:

```xml
<bundles.dir>${project.parent.basedir}/bundles</bundles.dir>
```

 - Add the bundle resource base in portalserver/pom.xml, e.g.:

```xml
<resourceBases>
 <resourceBase>${bundles.dir}/cxp-fe-training-02/src/main/webapp</resourceBase>
 <resourceBase>${project.basedir}/src/main/webapp</resourceBase>
 <resourceBase>${work.dir}</resourceBase>
</resourceBases>
<extraClasspath>${bundles.dir}/cxp-fe-training-02/target/classes;${basedir}/target/classes/;${basedir}/target/portalserver/WEB-INF/classes</extraClasspath>
```

 - Add "exercises-ptc-config.xml" to the list of PTC configuration files in portalserver\src\main\webapp\WEB-INF\web.xml, e.g.:

```xml
<!-- PTC configuration files list. -->
<context-param>
    <param-name>com.backbase.portal.ptc.web.CONFIG_FILE</param-name>
    <param-value>classpath:/ptc-config.xml,classpath:/ptc-launchpad.xml,classpath:/exercises-ptc-config.xml</param-value>
</context-param>
```

Then run `mvn package` at the root of portalserver!
