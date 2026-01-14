# 🎓 FOROEPCC - Foro Académico para Estudiantes de Ciencia de la Computación

<div align="center">

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3.1-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![Next.js](https://img.shields.io/badge/Next.js-14-000000?style=for-the-badge&logo=next.js&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Jenkins](https://img.shields.io/badge/Jenkins-CI/CD-D24939?style=for-the-badge&logo=jenkins&logoColor=white)
![SonarQube](https://img.shields.io/badge/SonarQube-Analysis-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)

**Proyecto Final - Ingeniería de Software II**  
Universidad Nacional de San Agustín de Arequipa

[Repositorio GitHub](https://github.com/jflma/ProyectoFinalIS2.git)

</div>

---

## 📋 Tabla de Contenidos

1. [Equipo de Trabajo](#1-equipo-de-trabajo)
2. [Propósito del Proyecto](#2-propósito-del-proyecto)
3. [Funcionalidades de Alto Nivel](#3-funcionalidades-de-alto-nivel)
4. [Modelo de Dominio](#4-modelo-de-dominio)
5. [Visión General de Arquitectura](#5-visión-general-de-arquitectura)
6. [Servicios REST Disponibles](#6-servicios-rest-disponibles)
7. [Pipeline CI/CD](#7-pipeline-cicd)
8. [Instalación y Ejecución](#8-instalación-y-ejecución)
9. [Capturas de Pantalla](#9-capturas-de-pantalla)

---

## 1. Equipo de Trabajo

### 👥 **FOROEPCC**

### 👥 Integrantes del equipo

- **Huayhua Carlos, Lenin** 
- **Lizarve Mamani, Johan** 
- **Mamani Yucra, Edilson** 


## 2. Propósito del Proyecto

### 🎯 Objetivo General

**FOROEPCC** es una plataforma web tipo foro académico diseñada específicamente para estudiantes de la Escuela Profesional de Ciencia de la Computación (EPCC) de la UNSA. El sistema permite a los estudiantes:

- **Compartir conocimiento** mediante publicaciones y respuestas.
- **Resolver dudas académicas** de forma colaborativa.
- **Interactuar** con otros estudiantes a través de comentarios y votos.
- **Buscar información** utilizando un motor de búsqueda integrado.

### 🔑 Objetivos Específicos

1. Implementar un sistema de autenticación seguro con JWT.
2. Desarrollar un CRUD completo para posts, respuestas y comentarios.
3. Aplicar prácticas de **Domain-Driven Design (DDD)** y **Clean Architecture**.
4. Establecer un pipeline CI/CD completo con Jenkins.
5. Garantizar la calidad del código mediante análisis estático y pruebas automatizadas.

---

## 3. Funcionalidades de Alto Nivel

### 📊 Diagrama de Casos de Uso

```
┌─────────────────────────────────────────────────────────────────┐
│                        SISTEMA FOROEPCC                          │
├─────────────────────────────────────────────────────────────────┤
│                                                                  │
│   ┌─────────┐                                                   │
│   │ Usuario │                                                   │
│   │ Anónimo │──────┬──► Registrarse                             │
│   └─────────┘      │                                            │
│                    └──► Ver Posts Públicos                      │
│                                                                  │
│   ┌─────────┐                                                   │
│   │ Usuario │──────┬──► Iniciar Sesión                          │
│   │Registrado│     ├──► Crear Post                              │
│   └─────────┘      ├──► Responder Post                          │
│                    ├──► Comentar Respuesta                      │
│                    ├──► Buscar Posts                            │
│                    ├──► Ver Detalles de Post                    │
│                    ├──► Editar Perfil                           │
│                    └──► Cerrar Sesión                           │
│                                                                  │
│   ┌─────────┐                                                   │
│   │  Admin  │──────┬──► Gestionar Usuarios                      │
│   └─────────┘      ├──► Moderar Contenido                       │
│                    └──► Ver Estadísticas                        │
│                                                                  │
└─────────────────────────────────────────────────────────────────┘
```

### ✨ Funcionalidades Principales

| Módulo | Funcionalidad | Descripción |
|--------|---------------|-------------|
| **Auth** | Registro | Creación de cuenta con validación de datos |
| **Auth** | Login/Logout | Autenticación mediante JWT |
| **Post** | CRUD Posts | Crear, leer, actualizar y eliminar publicaciones |
| **Post** | Respuestas | Sistema de respuestas a posts |
| **Post** | Comentarios | Comentarios anidados en respuestas |
| **Search** | Búsqueda | Motor de búsqueda full-text con Hibernate Search |
| **User** | Perfil | Gestión de información del usuario |

---

## 4. Modelo de Dominio

### 📐 Diagrama de Clases Principal

<img width="861" height="795" alt="Untitled" src="https://github.com/user-attachments/assets/fda1813f-8fcf-4f2d-beef-122488a8fd8b" />


### 📦 Módulos Principales

| Módulo | Responsabilidad | Entidades |
|--------|-----------------|-----------|
| **auth** | Autenticación y autorización (JWT) | ForoUser, Role, Person |
| **post** | Gestión de publicaciones, respuestas y comentarios | Post, Entry, Answer, Comment |
| **user** | Gestión de perfiles de usuario | ForoUser, Person |

### 📁 Carpetas de Soporte

| Carpeta | Responsabilidad |
|---------|-----------------|
| **config** | Configuraciones de Spring Security, WebSocket, CORS |
| **dto** | Data Transfer Objects compartidos |
| **exceptions** | Manejadores globales de excepciones |
| **shared** | Utilidades y componentes transversales |

---

## 5. Visión General de Arquitectura

### 🏗️ Enfoque: DDD + Clean Architecture

El proyecto sigue los principios de **Domain-Driven Design (DDD)** combinados con **Clean Architecture**, organizando el código en capas bien definidas:

```
┌─────────────────────────────────────────────────────────────────┐
│                      CAPA DE PRESENTACIÓN                        │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │ Controllers │  │    DTOs     │  │  Responses  │              │
│  └──────┬──────┘  └─────────────┘  └─────────────┘              │
├─────────┼───────────────────────────────────────────────────────┤
│         ▼           CAPA DE APLICACIÓN                          │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │  Services   │  │ Interfaces  │  │   Mappers   │              │
│  └──────┬──────┘  └─────────────┘  └─────────────┘              │
├─────────┼───────────────────────────────────────────────────────┤
│         ▼             CAPA DE DOMINIO                           │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │  Entities   │  │   Value     │  │  Domain     │              │
│  │  (Modelos)  │  │   Objects   │  │  Events     │              │
│  └──────┬──────┘  └─────────────┘  └─────────────┘              │
├─────────┼───────────────────────────────────────────────────────┤
│         ▼         CAPA DE INFRAESTRUCTURA                       │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐              │
│  │Repositories │  │  Security   │  │   Config    │              │
│  │    (JPA)    │  │   (JWT)     │  │  (Spring)   │              │
│  └─────────────┘  └─────────────┘  └─────────────┘              │
└─────────────────────────────────────────────────────────────────┘
```

### 📁 Diagrama de Paquetes

```
backend/
├── src/main/java/com/app/
│   ├── modules/
│   │   ├── auth/
│   │   │   ├── controller/     # AuthController
│   │   │   ├── dto/            # LoginDTO, SignupDTO, TokenDTO
│   │   │   ├── model/          # ForoUser, Role, Person
│   │   │   ├── repository/     # UserRepository, RoleRepository
│   │   │   └── service/        # UserService, JwtService
│   │   │
│   │   ├── post/
│   │   │   ├── controller/     # PostController, AnswerController, CommentController
│   │   │   ├── dto/            # PostDTO, AnswerDTO, CommentDTO
│   │   │   ├── model/          # Post, Entry, Answer, Comment
│   │   │   ├── repository/     # PostRepository, AnswerRepository
│   │   │   └── service/        # PostService, AnswerService, CommentService
│   │   │
│   │   └── search/
│   │       ├── controller/     # SearchController
│   │       └── service/        # SearchService (Hibernate Search)
│   │
│   ├── shared/
│   │   ├── config/             # SecurityConfig, WebConfig
│   │   ├── exception/          # GlobalExceptionHandler
│   │   └── utils/              # JwtUtils, ValidationUtils
│   │
│   └── BackendApplication.java
│
├── src/test/java/com/app/
│   ├── modules/post/
│   │   ├── controller/         # Tests unitarios de controllers
│   │   └── functional/         # Tests funcionales de integración
│   │
│   └── security/               # Tests de seguridad y autorización
│
└── src/gatling/java/simulations/  # Tests de rendimiento
    ├── LoginLoadSimulation.java
    ├── PostReadLoadSimulation.java
    └── SearchLoadSimulation.java
```

---

## 6. Servicios REST Disponibles

### 📖 Documentación OpenAPI / Swagger

### 🔐 Módulo: Authentication (`/auth`)

**Propósito:** Gestión de registro, inicio de sesión y tokens JWT.

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| `POST` | `/auth/signup` | Registrar nuevo usuario | `SignupFieldsDTO` (body) |
| `POST` | `/auth/signin` | Iniciar sesión | `LoginRequestDTO` (body) |
| `GET` | `/auth/hello` | Verificar estado del servicio | - |

**Modelos Involucrados:**
```json
// SignupFieldsDTO
{
  "username": "string",
  "password": "string",
  "email": "string",
  "firstName": "string",
  "lastName": "string"
}

// LoginRequestDTO
{
  "username": "string",
  "password": "string"
}

// TokenResponseDTO
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6..."
}
```

---

### 📝 Módulo: Posts (`/post`)

**Propósito:** CRUD completo de publicaciones del foro.

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| `POST` | `/post/create` | Crear nueva publicación | ✅ JWT |
| `GET` | `/post/ultimatePost` | Obtener posts recientes | ❌ Público |
| `GET` | `/post/details/{id}` | Ver detalles de un post | ❌ Público |

**Modelos Involucrados:**
```json
// CreatePostDTO
{
  "title": "string",
  "content": "string",
  "image": "string (opcional)"
}

// PostResponseDTO
{
  "id": 1,
  "title": "string",
  "content": "string",
  "authorUsername": "string",
  "views": 0,
  "answers": 0,
  "createdAt": "2026-01-14T00:00:00Z"
}
```

---

### 💬 Módulo: Answers (`/answer`)

**Propósito:** Gestión de respuestas a publicaciones.

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| `POST` | `/answer/create` | Crear respuesta a post | ✅ JWT |
| `GET` | `/answer/post/{postId}` | Obtener respuestas de un post | ❌ Público |

---

### 🗨️ Módulo: Comments (`/comment`)

**Propósito:** Gestión de comentarios en respuestas.

| Método | Endpoint | Descripción | Auth |
|--------|----------|-------------|------|
| `POST` | `/comment/create` | Crear comentario | ✅ JWT |
| `GET` | `/comment/answer/{answerId}` | Obtener comentarios | ❌ Público |

---

### 🔍 Módulo: Search (`/search`)

**Propósito:** Motor de búsqueda full-text sobre posts.

| Método | Endpoint | Descripción | Parámetros |
|--------|----------|-------------|------------|
| `GET` | `/search` | Buscar posts | `query` (string) |

---

## 7. Pipeline CI/CD

### 🔄 Visión General del Pipeline

El proyecto implementa un pipeline de **Integración y Entrega Continua** utilizando **Jenkins**, activado automáticamente mediante webhooks de GitHub.

```
┌─────────────────────────────────────────────────────────────────────────────┐
│                           PIPELINE CI/CD                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                              │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐               │
│  │ Checkout │───►│  Build   │───►│ SonarQube│───►│  Unit    │               │
│  │   SCM    │    │ (Gradle) │    │ Analysis │    │  Tests   │               │
│  └──────────┘    └──────────┘    └──────────┘    └──────────┘               │
│                                                        │                     │
│       ┌────────────────────────────────────────────────┘                    │
│       ▼                                                                      │
│  ┌──────────┐    ┌──────────┐    ┌──────────┐    ┌──────────┐               │
│  │Functional│───►│ Security │───►│   Perf   │───►│ Package  │               │
│  │  Tests   │    │  Tests   │    │  Tests   │    │  (JAR)   │               │
│  └──────────┘    └──────────┘    └──────────┘    └──────────┘               │
│                                                                              │
└─────────────────────────────────────────────────────────────────────────────┘
```

<img width="1787" height="274" alt="image" src="https://github.com/user-attachments/assets/84ed5bb5-3a5a-4488-8f2b-96508cb08c7d" />


### 📋 Etapas del Pipeline

#### 1️⃣ Construcción Automática
- **Herramienta:** Gradle 8.11
- **Comando:** `./gradlew clean assemble -x test`
- **Descripción:** Compila el código fuente y genera los artefactos sin ejecutar tests.

#### 2️⃣ Análisis Estático de Código
- **Herramienta:** SonarQube + sonar-scanner
- **Comando:** `./gradlew sonar`
- **Métricas Analizadas:**
  - Code Smells
  - Vulnerabilidades de seguridad
  - Cobertura de código
  - Duplicación de código
  - Complejidad ciclomática

#### 3️⃣ Pruebas Unitarias
- **Framework:** JUnit 5 + Mockito
- **Comando:** `./gradlew unitTest`
- **Cobertura:** Controllers, Services, Repositories
- **Ejemplos:**
  - `AnswerControllerTest.java`
  - `CommentControllerTest.java`
  - `PostControllerTest.java`

#### 4️⃣ Pruebas Funcionales
- **Framework:** Spring Boot Test + MockMvc
- **Comando:** `./gradlew functionalTest`
- **Descripción:** Pruebas de integración end-to-end.
- **Ejemplos:**
  - `AuthFunctionalTest.java` - Flujo de registro de usuarios
  - `PostFunctionalTest.java` - Flujo de creación de posts

#### 5️⃣ Pruebas de Seguridad
- **Framework:** Spring Security Test + OWASP Dependency Check
- **Comandos:**
  - `./gradlew securityTest` - Tests de autorización
  - `./gradlew dependencyCheckAnalyze` - Análisis de vulnerabilidades CVE
- **Ejemplos:**
  - `AuthorizationSecurityTest.java` - Verificación de endpoints protegidos

#### 6️⃣ Pruebas de Rendimiento
- **Framework:** Gatling (Java DSL)
- **Comando:** `./gradlew gatlingRun`
- **Simulaciones:**
  | Simulación | Descripción | Usuarios Virtuales |
  |------------|-------------|-------------------|
  | `LoginLoadSimulation` | Stress test de login | 50 usuarios/seg |
  | `PostReadLoadSimulation` | Carga de lectura de posts | 100 usuarios/seg |
  | `SearchLoadSimulation` | Estrés del motor de búsqueda | 30 usuarios/seg |

#### 7️⃣ Empaquetado
- **Herramienta:** Gradle + Spring Boot
- **Comando:** `./gradlew bootJar`
- **Artefacto:** `backend-0.0.1-SNAPSHOT.jar`

---

### 🐛 Gestión de Issues

El proyecto utiliza **GitHub Issues** y **GitHub Projects** para el seguimiento de:

- 🐞 **Bugs:** Errores identificados durante desarrollo y testing.
- ✨ **Features:** Nuevas funcionalidades solicitadas.
- 📝 **Tasks:** Tareas técnicas y de documentación.
- 🔧 **Improvements:** Mejoras de código existente.

**Tablero Kanban:** 
<img width="1919" height="900" alt="image" src="https://github.com/user-attachments/assets/ee74dcc2-f229-414c-aa03-ea676af789c5" />


---

## 8. Instalación y Ejecución

### 📋 Requisitos Previos

- **Java 21** (JDK)
- **Node.js 18+** (para el frontend)
- **PostgreSQL 15**
- **Docker Desktop** (opcional, para contenedores)
- **Jenkins** (para CI/CD)

### 🚀 Ejecución Local

#### Backend
```bash
cd backend
./gradlew bootRun
```
El servidor estará disponible en: `http://localhost:8080`

#### Frontend
```bash
cd frontend
npm install
npm run dev
```
La aplicación estará disponible en: `http://localhost:3000`



## 9. Capturas de Pantalla

### 🔐 Registro de Usuario
![Register](https://github.com/user-attachments/assets/ad64e07d-08ba-48ed-9c63-a2373f1a7cac)

### 🔑 Inicio de Sesión
![Login](https://github.com/user-attachments/assets/e4a25060-4fc3-41b6-9fb5-caf1274693be)

### 🏠 Página Principal
![Home](https://github.com/user-attachments/assets/10ac0246-79b9-4a3d-866b-b2f225b7ddc5)

### 📝 Vista de Posts
![Posts](https://github.com/user-attachments/assets/edf04f70-329c-4725-bf74-dde9beba7134)

---

<div align="center">

**FOROEPCC** © 2026 - Todos los derechos reservados

Desarrollado con ❤️ por el equipo FOROEPCC

</div>
