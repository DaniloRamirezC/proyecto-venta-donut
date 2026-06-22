## Arquitectura de Microservicios: Sabores Felices Donutin

Proyecto desarrollado con **Spring Boot** para la gestión integral de un servicio de venta de donuts, utilizando una arquitectura de microservicios desacoplados.

### Integrantes del proyecto
- Benjamín González
- Danilo Ramírez

### 1. Microservicios y puertos
| Microservicio | Responsabilidad | Puerto  | Base de Datos | Tablas |
| :---          | :---            | :---    | :---          | :---   |
| `service-catalogo`| Gestión del inventario y tipos de donuts | 8081 | `bd_catalogo` | donut, categoria |
| `servicio-clientes`| Gestión de los datos del cliente | 8082 | `bd_clientes` | clientes |
| `servicio-pedido`| Gestión de órdenes de compra y detalles | 8083 | `bd_pedidos` | pedido, detalle_pedido |
| `service-pagos`| Procesamiento de pagos y generación de comprobantes con IVA incluido | 8084 | `bd_pagos` | pago, comprobante_pago |
| `service-logistica`| Gestión de despacho y repartidores | 8085 | `bd_logistica` | despacho |
| `service-autenticacion`| Gestión de usuarios, contraseñas y roles | 8086 | `bd_seguridad` | roles, usuarios, usuario_roles |
| `service-empresa`| Gestión de los datos de la empresa | 8087 | `bd_empresa` | empresa |
| `service-reportes`| Gestión de despacho y repartidores | 8088 | `bd_reportes` | reporte_ventas |
| `service-inventario`| Gestión de insumos, proveedores y recetas | 8089 | `bd_inventario` | insumo, proveedor, receta |
| `service-promociones`| Gestión de las promociones | 8090 | `bd_promociones` | cupon |

### 2. Tecnologías y aplicaciones requeridas
Para crear este proyecto, necesitas:
-	Lenguaje: Java 21
-	Framework: Spring Boot 3.x
-	Herramientas de construcción y gestión de proyectos: Maven
-	Dependencias: Spring Data JPA, MySQL Driver, Lombok, Spring Web, Validation, SpringDoc, Eureka, Spring Security.
-	Base de Datos: MySQL / phpMyAdmin. Uso de XAMPP para encender la base de datos
-	Pruebas de API: Postman
-	IDE: Visual Studio Code
-	Comunicación entre los servicios: WebClient (Spring Reactive Web)
-	Swagger
-	OpenAPI Specification (OAS): Descripción del funcionamiento de la API

### 3.	Configuración de base de datos: 
1. Iniciar Apache y MySQL en XAMPP. 
2. Crea las bases de datos mencionadas en la tabla anterior.
3. El esquema se genera automáticamente mediante spring.jpa.hibernate.ddl-auto=update.
4. Para el microservicio de autenticación, debes crear una base de datos bd_seguridad y sus tablas de roles, usuarios y usuario_roles, además de insertar valores en las tablas: Cliente, Gerente, Pastelero, Contador y Repartidor

### 4.	Encendido de terminales en Visual Studio Code: 
1.	Terminal 1: Ejecuta service-catalogo (puerto 8081)
2.	Terminal 2: Ejecuta servicio-clientes (puerto 8082)
3.	Terminal 3: Ejecuta servicio-pedido (puerto 8083)
4.	Terminal 4: Ejecuta service-pagos (puerto 8084)
5.	Terminal 5: Ejecuta service-logistica (puerto 8085)
6.	Terminal 6: Ejecuta service-autenticacion (puerto 8086)
7.	Terminal 7: Ejecuta service-empresa (puerto 8087)
8.	Terminal 8: Ejecuta service-reportes (puerto 8088)
9.	Terminal 9: Ejecuta service-inventario (puerto 8089)
10.	Terminal 10: Ejecuta service-promociones (puerto 8090)
11.	Terminal 11: Ejecuta api-gateway (puerto 8080)
**NOTA: Se recomienda ejecutar los microservicios por separado en el Dashboard, ejecutarlos todos de una puede ralentizar su encendido**

### 5. Datos de prueba (Endpoints en Postman)
**URL:** `http://localhost:8080/api/v1/auth/registrar`
### Crear un Usuario (POST)
```json
{
    "nombreUsuario": "Juan Castaño",
	"contrasena": "1234",
	"correo": "jcastano@gmail.com",
	"roles": ["GERENTE"]
}
```
**Respuesta de la consola:** Usuario registrado con éxito

**URL:** `http://localhost:8080/api/v1/auth/login`
### Realizar inicio de sesión (login) (POST)
```json
{
    "nombreUsuario": "Juan Castaño",
	"contrasena": "1234"
}
```
**Respuesta de la consola:** Se genera un token alfanumérico. Copia el String gigante (Token) que te devuelve.
## Acceso autorizado:
1. Selecciona Bearer Token.
2. Pega el token
3. Haz el `Get` en http://localhost:8080/api/v1/catalogo/donuts como prueba y te dejará entrar.

**URL:** `http://localhost:8080/avi/v1/catalogo/tipos`
```json
{
    "nombre": "Vegano"
}
```
### Crear una Donut (POST)
**URL:** `http://localhost:8080/api/v1/catalogo/donuts`
```json
{
    "nombreDonut": "Brocheta Donutin",
    "descripcion": "Brocheta Donutin con 4 deliciosas mini donuts",
    "precioUnitario": 1000,
    "stock": 40,
    "categoria": {
        "idCategoria":1
          }
}
```
### Crear un Cliente (POST)
**URL:** `http://localhost:8080/api/v1/clientes`
```json
{
    "rutCliente": "12345678-9",
    "nombreCliente": "Juan",
    "apellidoCliente": "Perez",
    "email": "jperez@gmail.com",
    "direccion": "Avda Pajaritos 6350" 
}
```
### Crear un Pedido (POST)
**URL:** `http://localhost:8080/api/v1/pedidos/pedido`
```json
{
    "fechaPedido": "2026-05-04",
    "clienteId": 1,
    "detallePedido": [
    {
        "cantidad": 5,
        "donutId": 1
    }
    ]
}
```
### Crear un Pago (POST)
**URL:** `http://localhost:8080/api/v1/pagos`
```json
{
    "metodoPago":"Tarjeta débito",
    "monto":1000,
    "estadoTransaccion": true,
    "pedidoId": 5,
    "comprobantePago": {
        		"detalle":"Compra Brocheta Donutin"
    	}
}
```
### Crear un Despacho (POST)
**URL:** `http://localhost:8080/api/v1/despachos`
```json
{
    "repartidor": "Carlos Moreno",
    "estado": "En preparación",
    "pedidoId": 9,
	"clienteId": 5
}
```
### Actualizar el estado del Despacho (PUT)
**URL:** `http://localhost:8080/api/v1/despachos/1/estado`
```diff
{
	"idDespacho": 1,
	"repartidor": "Carlos Moreno",
-   "estado": "En preparación",
+   "estado": "Entregado", 
    "pedidoId": 8,
	"clienteId": 4
}
```
### Obtener el conteo por Categoría (GET)
**URL:** `http://localhost:8080/api/v1/catalogo/donuts/conteo-categoria`
```json
[
    [
        "Vegano",
        2
    ]
]
```
### 6. Datos de prueba (Para ejecución en Swagger)
Debes ingresar a http://localhost:8080/swagger-ui.html

Ingresa a Servicio de Catalogo

## Crea una Categoría
En `POST` /api/v1/catalogo/tipos te diriges a **Parameters** presionando **Try It Out**, luego en **Request body**, anotando:
```json
{
    "nombre": "Vegano"
}
```
Presiona **Execute** y luego te diriges a **Responses**, el Response Body tiene que indicar esto: 
Code 
200 

Response Body
```json
{
	"idCategoria": 9
    "nombre": "Vegano"
}
```

### 7. Modelos relacionales
Los archivos VentaDonutModeloRelacional/ y VentaDonutModeloRelacional.dmd se encuentran en la carpeta Modelo Relacional Proyecto, el archivo .dmd se abre en Oracle DataModeler. Estos archivos **DEBEN** estar juntos para su funcionamiento en Data Modeler.
