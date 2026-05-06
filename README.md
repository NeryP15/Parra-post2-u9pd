# Microservicio de Productos - parra-post2-u9

Microservicio RESTful de gestión de productos desarrollado con Spring Boot 3.x, Java 21, pruebas automatizadas y CI/CD con GitHub Actions.

## Características

- ✅ Java 21 + Spring Boot 3.2.4
- ✅ Pruebas de integración con @DataJpaTest
- ✅ Pruebas unitarias de controlador con @WebMvcTest
- ✅ Cobertura de código con JaCoCo (70% mínimo)
- ✅ Base de datos H2 para testing
- ✅ Manejo global de excepciones con @ControllerAdvice
- ✅ CI/CD con GitHub Actions

## Requisitos Previos

- Java 21+
- Maven 3.9+
- Git

## Instalación y Ejecución

### Clonar repositorio
\`\`\`bash
git clone https://github.com/usuario/parra-post2-u9.git
cd parra-post2-u9
\`\`\`

### Ejecutar pruebas
\`\`\`bash
mvn clean test
\`\`\`

### Ejecutar verificación con JaCoCo
\`\`\`bash
mvn clean verify
\`\`\`

### Ejecutar aplicación
\`\`\`bash
mvn spring-boot:run
\`\`\`

## Cobertura de Código

![CI/CD Workflow](https://github.com/usuario/parra-post2-u9/actions/workflows/ci.yml/badge.svg)

### Reporte JaCoCo
La cobertura de código se genera automáticamente al ejecutar `mvn verify`.
El reporte está disponible en: `target/site/jacoco/index.html`

Consulta la captura del reporte en: [docs/jacoco-report.png](docs/jacoco-report.png)

**Cobertura mínima alcanzada: 70%** en ProductoServiceImpl

## Estructura del Proyecto

\`\`\`
parra-post2-u9/
├── src/
│   ├── main/
│   │   ├── java/com/ejemplo/
│   │   │   ├── ProductoApplication.java
│   │   │   ├── entity/Producto.java
│   │   │   ├── repository/ProductoRepository.java
│   │   │   ├── service/
│   │   │   │   ├── ProductoService.java
│   │   │   │   └── ProductoServiceImpl.java
│   │   │   ├── controller/ProductoController.java
│   │   │   └── exception/
│   │   │       ├── GlobalExceptionHandler.java
│   │   │       └── ErrorResponse.java
│   │   └── resources/application.properties
│   └── test/
│       ├── java/com/ejemplo/
│       │   ├── repository/ProductoRepositoryTest.java
│       │   └── controller/ProductoControllerTest.java
│       └── resources/application-test.properties
├── pom.xml
├── .gitignore
└── README.md
\`\`\`

## Pruebas Automatizadas

### Pruebas de Repositorio (ProductoRepositoryTest) - 4 pruebas
1. **save_asignaIdAutomaticamente** - Verifica que al guardar un producto, se asigna ID automáticamente
2. **findById_existente_retornaProducto** - Busca un producto existente por ID
3. **findAll_retornaListaCompleta** - Lista todos los productos
4. **deleteById_eliminaProducto** - Elimina un producto por ID

### Pruebas del Controlador (ProductoControllerTest) - 3 pruebas
1. **listarProductos_retorna200ConLista** - GET /api/productos retorna 200 con lista
2. **crearProducto_datosValidos_retorna201** - POST /api/productos retorna 201
3. **buscarProducto_noExistente_retorna404** - GET /api/productos/{id} no existente retorna 404

## Endpoints API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | /api/productos | Listar todos los productos |
| GET | /api/productos/{id} | Obtener producto por ID |
| POST | /api/productos | Crear nuevo producto |
| PUT | /api/productos/{id} | Actualizar producto |
| DELETE | /api/productos/{id} | Eliminar producto |

## Tecnologías Utilizadas

- **Spring Boot 3.2.4**
- **Java 21**
- **Maven 3.9+**
- **JPA/Hibernate**
- **H2 Database**
- **JUnit 5**
- **Mockito**
- **JaCoCo**
- **GitHub Actions**

## Autor

Alumno: Nery Parra  
Curso: Post 2 - Unidad 9  
Fecha: 2026

## Licencia

Este proyecto es parte de un ejercicio educativo.
