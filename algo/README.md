# Mise en place des Unit Tests

Structure  
```
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   ├── Main.java
    │   │   └── SomeClass.java
    │   └── resources
    └── test
        └── java
            └── SampleTest.java
```

pom.xml  
<Details>
  <Summary>junit-jupiter-engine + maven-surefire-plugin</Summary>
  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>unamu</groupId>
    <artifactId>algo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <!-- import org.junit.jupiter.api.Test --> 
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.9.1</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <!-- Config pour identifier les tests sur base de leur noms-->
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.5.5</version>
                <configuration>
                    <includes>
                        <include>**/*Test.java</include>
                        <include>**/Test*.java</include>
                    </includes>²²
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```
</Details>

- Sauver les classes de test dans **src/main/test**  
- dans un sous-dossier du même nom que **package des classes à tester**

Commande mvn pour 
- lancer tous les tests :  `mvn test`
- lancer une classe test (test suite) en particulier : `mvn test -Dtest=TestAlgorithmsPublic`
- lancer une méthode de test en particulier : `mvn test -Dtest=TestAlgorithmsPublic#GT_generateInvadersModifiesInvaders`







