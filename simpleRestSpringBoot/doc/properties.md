Pour utiliser les variables définies dans le fichier `application.properties` dans votre Spring Boot application, vous pouvez utiliser la classe `@Value` pour injecter ces valeurs en tant que propriétés à partir de cette configuration. Voici comment vous pouvez faire cela dans votre code Java.

Supposons que dans `src/main/resources/application.properties`, vous ayez les variables définies comme suit :

```properties
upload.dir=/path/to/uploads
```

Vous pouvez alors utiliser ces valeurs en tant qu'attributs injectables dans votre classe. Voici comment vous pouvez modifier votre code pour y accéder :

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@SpringBootApplication
public class DemoApplication {

    @Value("${upload.dir}")
    private String uploadDir; // Variable définie dans application.properties

    @PostMapping(path="/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
        String filename = file.getOriginalFilename();
        
        try {
            File dir = new File(uploadDir + "/" + filename);
            if (!dir.getParentFile().exists()) {
                dir.getParentFile().mkdirs(); // Crée le répertoire s'il n'existe pas
            }
            file.transferTo(dir);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(filename + " uploaded successfully");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

Dans cet exemple, `@Value("${upload.dir}")` injecte la valeur de la propriété définie dans `application.properties`.

### Exemple complet

```java
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@SpringBootApplication
public class DemoApplication {

    @Value("${upload.dir}")
    private String uploadDir; // Variable définie dans application.properties

    @PostMapping(path="/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file){
        String filename = file.getOriginalFilename();
        
        try {
            File dir = new File(uploadDir + "/" + filename);
            if (!dir.getParentFile().exists()) {
                dir.getParentFile().mkdirs(); // Crée le répertoire s'il n'existe pas
            }
            file.transferTo(dir);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return ResponseEntity.ok(filename + " uploaded successfully");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
```

Dans cet exemple complet, nous avons injecté la valeur de `upload.dir` dans la classe et utilisé cette variable pour le chemin du fichier. Vous pouvez modifier `uploadDir` selon votre configuration spécifique.