# TCM App

## Ejecutar pruebas
Para correr los tests se requiere tener un servidor Payara local corriendo con los puertos predeterminados.
Se requiere crear los datasources en Payara por línea de comando como sigue: 
```
asadmin create-jdbc-connection-pool --datasourceclassname com.microsoft.sqlserver.jdbc.SQLServerDataSource --restype javax.sql.DataSource --property user=user_tcm:password=12345:url="jdbc:sqlserver://localhost\sqlexpress;DatabaseName=tcm;encrypt=true;trustServerCertificate=true;" TcmPool
asadmin create-jdbc-connection-pool --connectionpoolid TcmPool jdbc/tcm

asadmin create-jdbc-connection-pool --datasourceclassname org.h2.jdbcx.JdbcDataSource --restype javax.sql.DataSource --property user=sa:password=12345:url="jdbc:h2:mem:tcmtest;MODE=MSSQLServer" TcmTestPool
asadmin create-jdbc-connection-pool --connectionpoolid TcmTestPool jdbc/tcmTest
```
Verificar si la conexión se creó correctamente con el siguiente comando:

```
asadmin ping-connection-pool TcmPool
```

Ahora puede ejecutar las pruebas con el comando:
```
mvn test
```

## Inicializar aplicación en contenedores
Para inicializar la aplicación en contenedores se requiere tener Docker y Docker Compose instalados en su máquina. Además se requiere tener Java 8 y Maven instalados.
Una vez que tenga todo listo, ejecute los siguientes comandos en la raíz del proyecto:

```
mvn clean install -DskipTests
```
```
docker-compose up sqlserver_tcm
``` 
```
mvn liquibase:update
```
```
docker-compose up payara
```
Una vez inicializada la aplicación puede ingresar con los siguientes datos:
```
http://localhost:8080/tcm
usuario: admin
contraseña: 12345
```
Para detener la aplicación ejecute el siguiente comando:
```
docker-compose down
```
