# Proyecto de Base de Datos Distribuida (Java + Oracle)

Este es un sistema de gestión distribuida desarrollado en **JavaFX** que interactúa con una base de datos **Oracle**. El proyecto implementa un modelo de negocio completo (Clientes, Productos, Ordenes, etc.) y un sistema de auditoría.

## 📋 Características

- **Interfaz Gráfica (GUI):** Construida con JavaFX para una experiencia de usuario moderna.
- **Conectividad:** Uso de JDBC (`ojdbc10`) para conexión robusta con Oracle Database.
- **Arquitectura:** Modelo-Vista-Controlador (o similar con Servicios/DAOs) para separar la lógica de negocio, acceso a datos e interfaz.
- **Gestión de Datos:** ABM (Altas, Bajas, Modificaciones) para:
  - Categorías
  - Clientes
  - Empleados
  - Productos
  - Proveedores
  - Órdenes
- **Auditoría:** Servicio automático (`AuditService`) que registra cambios o inicializa la auditoría en segundo plano.

## 🛠 Requisitos Previos

Asegúrate de tener instalado lo siguiente en tu sistema:

1.  **JDK 17** o superior.
2.  **Apache Maven** (para gestión de dependencias y construcción).
3.  **Oracle Database** (Local o Remota).
    - El proyecto espera una conexión en `localhost:1521:orcl` (o XE).

## ⚙️ Configuración de la Base de Datos

Antes de iniciar la aplicación, debes preparar tu base de datos Oracle:

1.  Asegúrate de que tu instancia de Oracle está corriendo.
2.  Ejecuta el script SQL incluido en el proyecto (`script_actualizado.sql`) para crear las tablas y poblar los datos iniciales.
3.  Verifica las credenciales en `src/main/java/com/distribuidas/db/DatabaseConnection.java`. Por defecto están configuradas como:
    - **User:** `master`
    - **Password:** `master`
    - **URL:** `jdbc:oracle:thin:@localhost:1521:orcl`

> **Nota:** Si tu base de datos tiene un SID diferente (ej. `xe`) o usuario diferente, modifica el archivo `DatabaseConnection.java` antes de compilar.

## 🧹 Cómo Limpiar el Proyecto

Para eliminar los archivos compilados anteriores y limpiar el directorio `target/`, ejecuta el siguiente comando en la raíz del proyecto:

```bash
mvn clean
```

Esto es útil para asegurar una compilación fresca y resolver problemas de caché.

## 🚀 Cómo Iniciar el Proyecto

El proyecto está configurado con el plugin de Maven para JavaFX, lo que facilita su ejecución directa.

### Opción 1: Desde la Terminal (Recomendado)

Utiliza el siguiente comando para compilar y ejecutar la aplicación en un solo paso:

```bash
mvn javafx:run
```

### Opción 2: Compilar y Ejecutar el JAR

Si prefieres generar un ejecutable:

1.  Empaqueta el proyecto:
    ```bash
    mvn package
    ```
2.  Ejecuta el jar generado (dependiendo de cómo se haya configurado el shadind/assembly, o simplemente ejecutando la clase main desde el classpath).

## 📂 Estructura del Proyecto

- `src/main/java/com/distribuidas/`
  - **dao/**: Objetos de Acceso a Datos (Consultas SQL directas).
  - **db/**: Configuración de la conexión JDBC (`DatabaseConnection`).
  - **model/**: Clases POJO que representan las tablas de la BD (Entidades).
  - **service/**: Lógica de negocio (ej. `AuditService`).
  - **view/**: Vistas de la interfaz gráfica (`MainView`).
  - **App.java**: Punto de entrada de la aplicación JavaFX.
- `src/main/resources/`
  - **styles.css**: Estilos para la interfaz gráfica.

## 🧩 Tecnologías Usadas

- **Lenguaje:** Java 17
- **Framework UI:** JavaFX 21
- **Gestor de Dependencias:** Maven
- **Base de Datos:** Oracle Database 19c/21c
- **Driver:** OJDBC 10

---
*Desarrollado para el curso de Base de Datos Distribuida - VII Semestre.*
