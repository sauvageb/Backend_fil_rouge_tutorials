# définition de l’image de base pour créer l’image de votre application
FROM eclipse-temurin:17-jdk-alpine
# création d'un point de montage
VOLUME /tmp
# copie et renommage du fichier jar exécutable en app.jar
COPY target/*.jar app.jar
# définition de la commande à exécuter lors du démarrage de l'application
ENTRYPOINT ["java","-jar","/app.jar"]
# Configuration de l'application pour écouter sur le port 8080
EXPOSE 8080

