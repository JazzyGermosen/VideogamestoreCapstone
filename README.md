# 🎮 Videogame Store Capstone

## 📖 Description
This repository contains the **VideogamestoreCapstone** project — a backend application built in **Java** using **Spring Boot** to demonstrate understanding of REST APIs, Spring Boot fundamentals, database integration, and debugging/debugging practices. The goal of this capstone is to practice building scalable backend services and managing full-stack application workflows using modern Java tools and conventions. :contentReference[oaicite:0]{index=0}



## 💡 Interesting Code

Here is a snippet showing how a REST endpoint is implemented to retrieve all videogame products:

```java
@RestController
@RequestMapping("/api/videogames")
public class VideogameController {

    @Autowired
    private VideogameService videogameService;

    @GetMapping
    public ResponseEntity<List<Videogame>> getAllVideogames() {
        List<Videogame> games = videogameService.findAll();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}

Project Tree
VideogamestoreCapstone/
├── database/
│   ├── schema.sql
│   └── data.sql
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── videogame/
│   │   │           ├── controller/
│   │   │           ├── model/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
