# lib-auto-diagnosis

Le but de cette librairie est d'interpréter un index de santé pour en déduire les unités médicales par lesquelles le patient diagnostiqué doit passer.

## Utilisation

### Installation

Pour ajouter lib-auto-diagnosis à un projet Maven:
```
<dependency>
    <groupId>io.github.7evy.lib-auto-diagnosis</groupId>
    <artifactId>diagnosis-public</artifactId>
    <version>1.0.0</version>
</dependency>

<dependency>
    <groupId>io.github.7evy.lib-auto-diagnosis</groupId>
    <artifactId>diagnosis-impl</artifactId>
    <version>1.0.0</version>
    <scope>runtime</scope>
</dependency>
```

Ou récupérez l'archive JAR du projet, sur [Github](https://www.github.com/7evy/lib-auto-diagnosis/releases) ou avec Maven:
```
mvn clean install
```

### Intégration

Le capteur générant l'index de santé doit appeler [AutoDiagnosisService](diagnosis-public/src/main/java/io/github/sevenevy/diagnosis/service/AutoDiagnosisService.java)#diagnoseFromHealthIndex .
Le diagnostic peut être affiché sur un écran en implémentant [ScreenAdapter](diagnosis-public/src/main/java/io/github/sevenevy/diagnosis/infrastructure/ScreenAdapter.java).

## Configuration

Un index de santé indique une unité médicale (eg "Cardiologie") s'il est divisible par l'indicateur correspondant.
Par défaut, lib-auto-diagnosis assigne l'indicateur 3 à la Cardiologie et 5 à la Traumatologie.
D'autres indicateurs peuvent être utilisés en exportant la variable d'environnement `health.indicators.mapping` au format JSON:
```
health.indicators.mapping="{3:\"Cardiologie\",5:\"Traumatologie\"}"
```
