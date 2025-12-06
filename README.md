# FlexTemps Job Monitor - Guide d'Intégration

Ce projet est une démonstration d'un système de surveillance de jobs en temps réel pour une application Spring Boot.

## 🚀 Fonctionnement du Système

Le système repose sur **Spring AOP (Aspect Monitor)** pour être le moins intrusif possible.
1.  **Annotation** : Vous placez `@JobStep` sur vos méthodes existantes.
2.  **Aspect** : Un aspect intercepte automatiquement ces méthodes, mesure le temps d'exécution et met à jour l'état global.
3.  **WebSocket** : L'état est poussé en temps réel vers une interface web moderne.

---

## 🛠 Guide d'Intégration dans votre Projet

Suivez ces étapes pour intégrer ce monitoring dans votre projet existant.

### 1. Dépendances (`pom.xml`)

Ajoutez ces dépendances si vous ne les avez pas déjà (Web et WebSocket sont essentiels, AOP pour l'interception).

```xml
<dependencies>
    <!-- Web & WebSocket -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-websocket</artifactId>
    </dependency>

    <!-- AOP (Crucial pour le monitoring) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-aop</artifactId>
    </dependency>

    <!-- Lombok (Optionnel, mais utilisé dans le code d'exemple) -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
</dependencies>
```

### 2. Copier les Classes Core

Copiez le package `com.flextemps.dashboard.monitor` (ou adaptez le package) dans votre projet.
Ce package doit contenir :
*   `JobStep.java` (L'annotation)
*   `JobStepState.java` (Le DTO)
*   `JobStateService.java` (Le service qui stocke l'état)
*   `JobMonitorAspect.java` (L'aspect AOP)

> **Important** : Assurez-vous que `JobMonitorAspect` est bien scanné par Spring (soit dans le même package racine, soit importé).

### 3. Setup WebSocket et Web

Copiez le package `com.flextemps.dashboard.web` dans votre projet.
*   `WebSocketConfig.java`
*   `StatusWebSocketHandler.java`
*   (Optionnel) `DashboardController.java` (Uniquement si vous voulez servir la page via un Controller, sinon Spring Boot sert `index.html` statique automatiquement).

### 4. Intégration Frontend

Copiez le dossier `src/main/resources/static` complet (contenant `index.html`, `css/`, `js/`) dans votre dossier `src/main/resources/static`.
*   Vous pouvez renommer `index.html` en `monitor.html` si vous avez déjà une page d'accueil.

### 5. Annoter votre Code Existant

C'est la seule modification à faire dans votre logique métier.
Dans vos services lancés séquentiellement, ajoutez simplement l'annotation :

```java
@Service
public class MonServiceExistant {

    @JobStep(description = "1. Import des Fichiers") // <--- AJOUTER CECI
    public void importerFichiers() {
        // Votre code existant...
    }

    @JobStep(description = "2. Calcul des Salaires")
    public void calculer() {
        // Votre code existant...
    }
}
```

### 6. Gérer le cyle de vie (Optionnel mais recommandé)

Au début de votre méthode `@Scheduled`, nettoyez l'état précédent :

```java
@Autowired
private JobStateService jobStateService;

@Scheduled(...)
public void monJobPlanifie() {
    jobStateService.clear(); // Réinitialise le dashboard
    
    // ... lancement de vos services
}
```

## 🎨 Personnalisation

*   **Design** : Modifiez `src/main/resources/static/css/style.css`.
*   **Logique JS** : Modifiez `src/main/resources/static/js/app.js` si vous voulez changer la façon dont les données sont affichées.

## ✅ Vérification

Lancez votre application et allez sur `http://localhost:8080` (où `http://localhost:8080/monitor.html`) quand votre Job se lance.
