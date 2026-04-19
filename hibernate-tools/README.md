# Reverse engineering from a database
[hibernate-tools](https://hibernate.org/tools/)


## Setup 

1. pom.xml   

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.bar</groupId>
    <artifactId>foo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <hibernate-core.version>7.3.0.Final</hibernate-core.version>
        <h2.version>2.3.232</h2.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.hibernate.orm</groupId>
            <artifactId>hibernate-core</artifactId>
            <version>${hibernate-core.version}</version>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>${h2.version}</version>
        </dependency>
        <!-- DB2 -->
        <dependency>
            <groupId>com.ibm.db2.jcc</groupId>
            <artifactId>db2jcc</artifactId>
            <version>db2jcc4</version>
        </dependency>
         <!-- MySQL -->
        <dependency>
            <groupId>com.mysql</groupId>
            <artifactId>mysql-connector-j</artifactId>
        </dependency>

    </dependencies>

</project>
```
2. hibernate.properties

src/main/resources/hibernate.properties :  
```
# MySQL Configuration
hibernate.connection.driver_class=hibernate.connection.url=
hibernate.dialect=org.hibernate.dialect.DB2Dialect
hibernate.connection.username=ROOT
hibernate.connection.password=PWD
hibernate.default_schema=
hibernate.default_catalog=
hibernate.connection.autocommit=true
```

3. Generate Java classes from DB

`$ mvn org.hibernate.tool:hibernate-tools-maven:7.3.0.Final:hbm2java` 

see target/generated-sources/  

---

How to control the process ?

