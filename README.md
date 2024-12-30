# mcs-customer-core
Api microservice with java 17, spring security and spring boot 3

Microservicio de Gestión de Clientes

Este microservicio permite gestionar clientes a través de una API REST, desplegada en AWS EC2, construida con Java 17, Spring Boot 3, y Spring Security para la autenticación básica con OAuth con un Username y Password, dicho password está configurado en la base de datos con la clave Encriptada. El servicio está diseñado para gestionar información sobre los clientes, calcular estadísticas como el promedio y la desviación estándar de las edades, y también para obtener el signo zodiacal basado en la fecha de nacimiento de cada cliente. La base de datos utilizada es AWS RDS MySQL.

Funcionalidades

	Este microservicio permite realizar las siguientes acciones:

	Registrar nuevo cliente: Permite crear un nuevo cliente proporcionando su nombre, apellido, edad y fecha de nacimiento.
	Consultar métricas de los clientes: Obtiene el promedio de edad y la desviación estándar de las edades de los clientes registrados.
	Listar todos los clientes: Muestra la lista de todos los clientes con sus datos completos (nombre, apellido, edad, fecha de nacimiento) y el cálculo de su signo zodiacal basado en la fecha de nacimiento.

Requisitos
Antes de ejecutar el microservicio, asegúrate de tener instalados los siguientes requisitos:

EC2 AWS - Servidor de Despliegue
Java 17 o superior.
Maven 3.6+ (para compilar el proyecto).
AWS RDS MySQL configurado y con una base de datos creada.
Spring Boot 3.
Base de datos
SWAGGER

Este microservicio se conecta a una base de datos MySQL alojada en AWS RDS. Asegúrate de tener una instancia de RDS configurada y accesible desde el servidor donde ejecutarás el microservicio.

Instalación y Configuración
1. Clonar el repositorio
	Primero, clona el repositorio en tu máquina local:

	git clone https://github.com/jesusazga/mcs-customer-core.git

2. Configurar la base de datos
	Configurar la conexión a la base de datos MySQL en AWS RDS en el archivo application.properties o application.yml de Spring Boot.

	Configuración para application.properties:

	spring.application.name=mcs-customer-core

	# MySQL Database URL
	spring.datasource.url=jdbc:mysql://databaseseek.cpcw4g6wml9z.us-east-1.rds.amazonaws.com:3306/seekdb

	# MySQL Database Username
	spring.datasource.username=admin

	# MySQL Database Password
	spring.datasource.password=root2025AWS

	# MySQL Driver Class Name
	spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

	# JPA/Hibernate Properties
	spring.jpa.hibernate.ddl-auto=update
	spring.jpa.show-sql=true
	spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
	spring.jpa.properties.hibernate.format_sql=true

	# swagger-ui custom path
	#springdoc.swagger-ui.path=/swagger-ui.html

	management.endpoints.web.exposure.include=*
	management.endpoints.web.exposure.include=health,info,metrics
	management.endpoint.health.show-details=always

3. Compilar el proyecto
	
	Si tienes Maven instalado, compila el proyecto utilizando el siguiente comando:
	
	mvn clean install

4. Ejecutar el microservicio
	
	Para ejecutar el microservicio, utiliza el siguiente comando:

	mvn spring-boot:run
	
	El microservicio estará disponible en http://localhost:8080.


Autenticación

Este microservicio utiliza Spring Security con autenticación básica Basic Oauth, solo funcionará con estas credenciales, en caso se conecte a la Base de Datos Mysql, en la tabla "user" se encuentra dicho usuario registrado con el password encriptado.

	Credenciales de Autenticación

	Username: testuser

	Password: 1234

	Seguridad en los Endpoints
	
	Los siguientes endpoints están protegidos y requieren la autenticación básica:

	POST /api/customer: Para registrar un nuevo cliente.
	GET /api/customer: Para listar todos los clientes registrados.
	GET /api/customer/metrics: Para obtener las métricas de los clientes (promedio de edad, desviación estándar, etc.).