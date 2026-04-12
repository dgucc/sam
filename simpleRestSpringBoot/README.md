# Compiler le projet
`mvn clean compile`  

# Lancer le serveur spring-boot
`mvn spring-boot:run`  

ou préciser la class main  
`mvn exec:java -Dexec.mainClass=com.example.demo.DemoApplication`  

ou en une seule ligne  
`mvn clean compile spring-boot:run`  


# Dans le browser 

Pour obtenir la liste des universités, URL : http://localhost:8080/universities  
```json
[
  {
    "name": "Faculté universitaire de Namur"
  },
  {
    "name": "Université catholique de Louvain"
  },
  {
    "name": "Université libre de Bruxelles"
  }
]
```

ou à partir d'une commande curl :   
`curl --silent http://localhost:8080/universities`  


ou à partir d'une page web (javascript) :  
src/main/resources/static/index.html


# Add University
curl --silent -H "Content-Type: application/json" -X POST -d'{"name":"Unif de Waremme", "logo":"logo/no-logo"}' http://localhost:8080/universities

# MySQL

pom.xml:  
```xml
<!-- MySQL -->
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
</dependency>
``` 

application.properties:  
``` 
# MySQL Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=root
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
``` 


---

# TODO

- login

---
# Claude Code Ollama

$ ollama launch claude --model qwen2.5:3b




---
# Claude Code Ollama

$ ollama launch claude --model qwen2.5:3b

---

# TODO

MySQL config

---

# Liens utiles
https://commons.wikimedia.org/wiki/Category:Logos_of_universities_and_colleges_in_Belgium