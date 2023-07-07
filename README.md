----------------------------------------------------------------------------------------------------------------------
                                           TSpringBootProjectDemo4                                                   
                                                                                                                     
                                          Autor: Daniel Pérez Pérez                                                  
                                             Fecha: 24/06/2023                                                       
----------------------------------------------------------------------------------------------------------------------
----------------------------------------------------------------------------------------------------------------------
DESCRIPCIÓN
----------------------------------------------------------------------------------------------------------------------
Arquitectura en 4 capas y conexión a base de datos MySql.

IMPORTANTE: Antes de ejecutar el proyecto debe realizar lo siguiente:

	1º Ejecutar en la base de datos MySql o MariaDb el script data-stockdb.sql. (Cargar el esquema y los datos para probar)
	2º Debe revisar, modificar y recompilar si procede, los datos de conexión disponibles en la clase es.dsw.connections.MySqlConnection relativos a el host, puerto, nameDB, usuario y password.

----------------------------------------------------------------------------------------------------------------------
ESPECIFICACIÓN TÉCNICA DE DESARROLLO UTILIZADO
----------------------------------------------------------------------------------------------------------------------
Entorno de Desarrollo: Spring Boot Suite, versión: 4
Servidor de referencia: Apache Tomcat, versión: 10
Jdk: OpenJdk, versión: 17.1
Base de datos: MySql o MariaDb 8.0
Gestor de proyecto: Maven, versión: 4.0

----------------------------------------------------------------------------------------------------------------------
DEPENDENCIAS
----------------------------------------------------------------------------------------------------------------------
Spring Boot Framework: versión 3.1.*
       - spring-boot-starter-web
       - spring-boot-starter-tomcat
       - spring-boot-starter-test
       - tomcat-embed-jasper
       - spring-boot-starter-thymeleaf
       - mysql-connector-java
              
----------------------------------------------------------------------------------------------------------------------
RECOMENDACIÓN PARA ABRIR EL PROYECTO EN EL IDE: Spring Boot Suite
----------------------------------------------------------------------------------------------------------------------
1º. Copie el directorio TSpringBootProjectDemo4 en el directorio de su espacio de trabajo.
2º. Desde el IDE (Spring Boot Suite), importe el proyecto haciendo click en File -> Open Projects from File System 
3º. En Import source, haciendo click en el botón "Directory..." seleccione la carpeta que contiene el proyecto.
4º. Haga click en Finish
5º. Se recomienda realizar un Maven Update (Click derecho sobre el proyecto Maven -> Update Project...)
6º. Recompilar (salvo que tenga activado compilación automática).
7º. Ejecutar, por ejemplo haciendo click derecho sobre el proyecto -> Run As -> Java Application

----------------------------------------------------------------------------------------------------------------------
RECOMENDACIÓN PARA LA LECTURA Y ANÁLISIS DEL PROYECTO
----------------------------------------------------------------------------------------------------------------------

1º Pom.xml (nueva dependencia MySqlConnection)
2º MySqlConnection.java (Configuración del conector)


