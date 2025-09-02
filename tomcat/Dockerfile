FROM tomcat:10.1-jdk21-temurin

# Au démarrage du conteneur, copier le WAR monté via /opt/dist vers webapps
# (le volume est monté par docker-compose)
EXPOSE 8080

# Healthcheck: vérifie l'endpoint actuator (exposé via /api/actuator/health sous le contexte /app)
HEALTHCHECK --interval=10s --timeout=3s --retries=10 CMD wget -qO- http://localhost:8080/app/api/actuator/health | grep -q 'UP' || exit 1

CMD ["/bin/sh", "-c", "cp -f /opt/dist/app.war /usr/local/tomcat/webapps/app.war && catalina.sh run"] 