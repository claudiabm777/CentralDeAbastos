El proyecto se hizo con GWT 2.6 (http://www.gwtproject.org)

Necesita:
* Java 7
* JBoss
* Eclipse

A continuación presentamos la ubicación de las partes más importantes del proyecto:
* src/co/edu/uniandes/cabAndes/client tiene la interfaz gráfica web hecha en GWT
* src/co/edu/uniandes/cabAndes/server/dao tiene los DAOs usando JDBC (AdminDAO, AdminDAOCatalogo)
* src/co/edu/uniandes/cabAndes/server/fachada tiene las fachadas (Fachada, FachadaAdmin)
* src/co/edu/uniandes/cabAndes/server/servlets tiene los servlets GWT
* src/co/edu/uniandes/cabAndes/shared/vos tiene los Value Objects (clases XXXValue)

Para ejecutar el proyecto:
1. Instalar Java 7 y JBoss si no están (en las salas si estan)
2. Copiar CabAndes.war en JBoss (standalone/deployments/ o server/default/deploy)
3. Prender JBoss
4. Visitar http://localhost:8080/CabAndes/CabAndes.html
5. Estudiar los ejemplos en documentacion.pdf

Solo si la carpeta lib está vacía, para que todo compile en Eclipse:
1. Entrar a http://www.gwtproject.org/download.html
2. Clic en 'Download GWT SDK'
3. Descomprimir el zip descargado
4. Copiar a la carpeta lib los archivos:
   gwt-codeserver.jar
   gwt-dev.jar
   gwt-user.jar
   validation-api-1.0.0.GA-sources.jar
   validation-api-1.0.0.GA.jar

Para volver a generar el archivo CabAndes.war:
1. Ejecutar ant war
2. El archivo CabAndes.war queda al lado de build.xml
