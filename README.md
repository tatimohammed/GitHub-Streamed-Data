# GitHub Streamed Data
![Screenshot from 2023-06-27 01-32-18](https://github.com/tati2002med/GitHub-Streamed-Data/assets/95311883/8e41fb96-a4dc-402d-aa78-3b2eae23adbb)
- The `Dashboard` will automatically fetch new data each 30s.
## Data Pipeline
![Spring Boot API](https://github.com/tati2002med/GitHub-Streamed-Data/assets/95311883/9d3c29ab-5aed-4a3a-b3a2-488d99e3acca)
## Project Description
This project aims to provide valuable insights into GitHub repositories by analyzing the usage of different programming languages and identifying the most active months in terms of repository creation. It serves as an opportunity to gain familiarity with various technologies such as `Kafka`, `Spark`, `Spring Boot`, `MongoDB`, and `Angular`.
#### App Design
- `Front end` using Angular.
- `Back end` using Spring boot to creat a RESTful API.
#### Repository Architecture
```bash
.
├── Front-End using Angular
│   ├── angular.json
│   ├── package.json
│   ├── package-lock.json
│   ├── README.md
│   ├── src
│   │   ├── app
│   │   │   ├── app.component.css
│   │   │   ├── app.component.html
│   │   │   ├── app.component.spec.ts
│   │   │   ├── app.component.ts
│   │   │   ├── app.module.ts
│   │   │   ├── app-routing.module.ts
│   │   │   └── dashboard
│   │   │       ├── dashboard.component.css
│   │   │       ├── dashboard.component.html
│   │   │       ├── dashboard.component.ts
│   │   │       ├── dashboard.service.ts
│   │   │       └── dashboard.ts
│   │   ├── favicon.ico
│   │   ├── index.html
│   │   ├── main.ts
│   │   └── styles.css
│   ├── tsconfig.app.json
│   ├── tsconfig.json
│   └── tsconfig.spec.json
└── Java Spring API
    ├── HELP.md
    ├── mvnw
    ├── mvnw.cmd
    ├── pom.xml
    ├── src
    │   ├── main
    │   │   ├── java
    │   │   │   └── com
    │   │   │       └── example
    │   │   │           └── githubproject
    │   │   │               ├── auth
    │   │   │               │   └── GitHubEventsAPIClient.java
    │   │   │               ├── config
    │   │   │               │   ├── KafkaProducerConfig.java
    │   │   │               │   ├── KafkaTopicConfig.java
    │   │   │               │   └── SparkConfig.java
    │   │   │               ├── controller
    │   │   │               │   └── Streamer.java
    │   │   │               ├── dao
    │   │   │               │   └── ReposRepository.java
    │   │   │               ├── database
    │   │   │               │   └── GithubData.java
    │   │   │               ├── GithubProjectApplication.java
    │   │   │               └── service
    │   │   │                   ├── GitHubDataServiceImpl.java
    │   │   │                   └── GitHubDataService.java
    │   │   └── resources
    │   │       └── application.properties
    │   └── test
    │       └── java
    │           └── com
    │               └── example
    │                   └── githubproject
    │                       └── GithubProjectApplicationTests.java
    └── target
        └── classes
            ├── application.properties
            └── com
                └── example
                    └── githubproject
                        ├── auth
                        │   └── GitHubEventsAPIClient.class
                        ├── config
                        │   ├── KafkaProducerConfig.class
                        │   ├── KafkaTopicConfig.class
                        │   └── SparkConfig.class
                        ├── controller
                        │   └── Streamer.class
                        ├── dao
                        │   └── ReposRepository.class
                        ├── database
                        │   └── GithubData.class
                        ├── GithubProjectApplication.class
                        └── service
                            ├── GitHubDataService.class
                            └── GitHubDataServiceImpl.class

34 directories, 49 files
```
## Suggestions
- In addition to the existing goals, we can enhance the project by incorporating `Natural Language Processing (NLP)` tasks to classify repositories based on their names and programming languages. This addition will enable us to gain insights into the dominant themes within the GitHub ecosystem, such as `NLP`, `Image Recognition`, or `General Software Development` ....  

## Contact Me if you need anything :)
- Email: `mohammed.tati21@gmail.com`
- Phone: `+212682633363`
- LinkedIn: <a href="https://www.linkedin.com/in/mohammed-tati-2b3665222/">
<img src="https://camo.githubusercontent.com/fecb06c5b51c0c605a7db2b5e549d180fa3fb38e87a0d6011c3c9c830a2c68b7/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f4c696e6b6564496e2d626c75653f7374796c653d666c6174266c6f676f3d4c696e6b6564696e266c6f676f436f6c6f723d7768697465" alt="Linkedin Badge" data-canonical-src="https://img.shields.io/badge/LinkedIn-blue?style=flat&logo=Linkedin&logoColor=white" style="max-width: 100%; mergin-top:15px"></a>
