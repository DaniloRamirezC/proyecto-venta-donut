## Arquitectura de Microservicios: Sabores Felices Donutin

Proyecto desarrollado con **Spring Boot** para la gestión integral de un servicio de venta de donuts, utilizando una arquitectura de microservicios desacoplados.

### 1. Microservicios y puertos
| Microservicio | Responsabilidad | Puerto  | Base de Datos | Tablas |
| :---          | :---            | :---    | :---          | :---   |
| `service-catalogo`| Gestión del inventario y tipos de donuts | 8081 | `bd_catalogo` | donut, categoria |
| `servicio-clientes`| Gestión de los datos del cliente | 8082 | `bd_clientes` | clientes |
| `servicio-pedido`| Gestión de órdenes de compra y detalles | 8083 | `bd_pedidos` | pedido, detalle_pedido |
| `service-pagos`| Procesamiento de pagos y generación de comprobantes con IVA incluido | 8084 | `bd_pagos` | pago, comprobante_pago |
| `service-logistica`| Gestión de despacho y repartidores | 8085 | `bd_logistica` | despacho |

### 2. Tecnologías y aplicaciones requeridas
Para crear este proyecto, necesitas:
-	Lenguaje: Java 21
-	Framework: Spring Boot 3.x
-	Herramientas de construcción y gestión de proyectos: Maven
-	Dependencias: Spring Data JPA, MySQL Driver, Lombok, Spring Web, Validation.
-	Base de Datos: MySQL / phpMyAdmin. Uso de XAMPP para encender la base de datos
-	Pruebas de API: Postman
-	IDE: Visual Studio Code
-	Comunicación entre los servicios: WebClient (Spring Reactive Web)

### 3.	Configuración de base de datos: 
1. Iniciar Apache y MySQL en XAMPP. 
2. Crea las bases de datos mencionadas en la tabla anterior.
3. El esquema se genera automáticamente mediante spring.jpa.hibernate.ddl-auto=update.

### 4.	Encendido de terminales en Visual Studio Code: 
1.	Terminal 1: Ejecuta service-catalogo (puerto 8081)
2.	Terminal 2: Ejecuta servicio-clientes (puerto 8082)
3.	Terminal 3: Ejecuta servicio-pedido (puerto 8083)
4.	Terminal 4: Ejecuta service-pagos (puerto 8084)
5.	Terminal 5: Ejecuta service-logistica (puerto 8085)
6.	Terminal 6: Ejecuta api-gateway (puerto 8080)

### 5. Datos de prueba (Endpoints en Postman)

### Crear una Categoría (POST)
**URL:** `http://localhost:8080/donuts/tipos`
```json
{
    "nombre": "Vegano"
}
```
### Crear una Donut (POST)
**URL:** `http://localhost:8080/donuts`
```json
{
    "nombreDonut": "Brocheta Donutin"
    "descripción": "Brocheta Donutin con 4 deliciosas mini donuts"
    "precioUnitario": 1000
    "stock": 40
    "categoría”: {
        "idCategoria":1,
        "nombre": "Vegano"
          }
}
```
### Crear un Cliente (POST)
**URL:** `http://localhost:8080/clientes`
```json
{
    "rutCliente": "12345678-9",
    "nombreCliente": "Juan",
    "apellidoCliente": "Perez",
    "email": "jperez@gmail.com"
    "dirección": "Avda Pajaritos 6350" 
}
```
### Crear un Detalle de pedido (POST)
**URL:** `http://localhost:8080/api/v1/pedidos/detalles`
```json
{
    "cantidad": 5,
    "donutId": 1
}
```

### Crear un Pedido (POST)
**URL:** `http://localhost:8080/api/v1/pedidos`
```json
{
    "fechaPedido": "2026-05-04"
    "clienteId": 1
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
    "repartidor": "Carlos Moreno"
	  "estado": "En preparación"
	  "pedidoId": 5
}
```

### 6. Modelos relacionales
Se encuentran en la carpeta VentaDonutModeloRelacional/ y archivo VentaDonutModeloRelacional.dmd, estos dos documentos se deben abrir en DataModeler de Oracle
