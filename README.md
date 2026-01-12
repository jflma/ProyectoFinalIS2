# 🎓 Proyecto Final IS2 - Sistema de Foro EPCC

Sistema integral de foro académico desarrollado con tecnología moderna.

## 🚀 Inicio Rápido

```bash
# Opción 1: Script automático (Windows)
run-project.bat both

# Opción 2: Manual - Terminal 1 (Backend)
cd backend && ./gradlew bootRun

# Opción 2: Manual - Terminal 2 (Frontend)
cd frontend && npm run dev
```

**Acceso:**
- Backend: http://localhost:8080
- Frontend: http://localhost:3000

---

## 📚 Documentación

| Documento | Descripción |
|-----------|-------------|
| **[📄 RESUMEN_EJECUTIVO.md](./RESUMEN_EJECUTIVO.md)** | ⭐ **COMIENZA AQUÍ** - Resumen de issues y cómo ejecutar |
| **[📊 ANALISIS_ISSUES.md](./ANALISIS_ISSUES.md)** | Análisis detallado de ambos issues (ResponseEntity y MockMvc) |
| **[🚀 QUICK_START.md](./QUICK_START.md)** | Guía rápida de comandos y endpoints |
| **[📈 DASHBOARD.md](./DASHBOARD.md)** | Estado general del proyecto y métricas |

---

## 🏗️ Arquitectura

### Backend (Java/Spring Boot)
- **Framework:** Spring Boot 3.3.1
- **Seguridad:** Spring Security + JWT (Auth0)
- **Base de Datos:** PostgreSQL + H2 (testing)
- **Búsqueda:** Hibernate Search + Lucene
- **Estructura:** Organizado en módulos (auth, user, post)

### Frontend (Next.js)
- **Framework:** Next.js 14.2.5
- **UI:** React 18 + TailwindCSS
- **Estado:** Zustand
- **HTTP:** Axios
- **Formularios:** React Hook Form

---

## ✨ Características

- ✅ Autenticación y autorización con roles
- ✅ CRUD de posts y preguntas
- ✅ Sistema de comentarios y respuestas
- ✅ Búsqueda full-text
- ✅ Editor de Markdown
- ✅ Interfaz responsiva
- ✅ WebSockets preparado
- ✅ Validación de entrada

---

## 📊 Estado de Issues

### 1. Estandarizar ResponseEntity
```
Estado: ⚠️ 40-50% COMPLETADO
✅ AuthController, PostController completados
❌ UserController, CommentController, AnswerController, SearchController
Prioridad: 🔴 CRÍTICA
```

### 2. Pruebas de Integración MockMvc
```
Estado: ⚠️ 60-70% COMPLETADO
✅ AuthControllerTest, PostControllerTest implementados
❌ Tests para User, Comment, Answer, Search controllers
Prioridad: 🟡 ALTA
```

👉 **[Ver análisis completo →](./ANALISIS_ISSUES.md)**

---

## 🔧 Requisitos

- Java 21+
- Node.js 18+
- PostgreSQL 12+ (o usar H2 para testing)
- npm o yarn

---

## 📁 Estructura del Proyecto

```
ProyectoFinalIS2/
├── backend/                    ← Servidor Spring Boot
│   ├── src/main/java/com/app/
│   │   ├── modules/           ← Módulos por dominio
│   │   │   ├── auth/
│   │   │   ├── user/
│   │   │   └── post/
│   │   ├── config/
│   │   ├── exceptions/
│   │   └── dto/
│   ├── src/test/java/         ← Tests de integración
│   └── build.gradle
│
├── frontend/                   ← Cliente Next.js
│   ├── src/
│   │   ├── app/               ← Páginas
│   │   ├── components/        ← Componentes
│   │   ├── services/          ← Servicios HTTP
│   │   └── storages/          ← Estado (Zustand)
│   └── package.json
│
├── README.md                  ← Este archivo
├── RESUMEN_EJECUTIVO.md       ← Resumen de issues
├── ANALISIS_ISSUES.md         ← Análisis detallado
├── QUICK_START.md             ← Guía rápida
├── DASHBOARD.md               ← Métricas del proyecto
├── run-project.bat            ← Script Windows
└── run-project.ps1            ← Script PowerShell
```

---

## 🔑 Endpoints Principales

### Autenticación (`/auth`)
```bash
POST   /auth/signup      # Registrar usuario
POST   /auth/signin      # Iniciar sesión
GET    /auth/hello       # Test endpoint
```

### Posts (`/post`)
```bash
POST   /post/create          # Crear post (requiere USER)
GET    /post/ultimatePost    # Obtener últimos posts
GET    /post/details/{id}    # Detalles de un post
```

### Usuario (`/user`)
```bash
GET    /user/check-status    # Estado del usuario (requiere USER)
GET    /user/role           # Solo admins (requiere ADMIN)
GET    /user/helloworld     # Test endpoint
```

### Comentarios (`/comment`)
```bash
POST   /comment/post                # Crear comentario (requiere USER)
GET    /comment/getComments/{id}    # Obtener comentarios
```

### Respuestas (`/answer`)
```bash
POST   /answer/create    # Crear respuesta (requiere USER)
```

### Búsqueda (`/search`)
```bash
GET    /search/posts?keyword=...    # Buscar posts
GET    /search/posts/index         # Indexar posts
```

---

## 🧪 Pruebas

```bash
# Ejecutar todos los tests
cd backend
./gradlew test

# Ejecutar test específico
./gradlew test --tests "*AuthControllerTest"

# Con cobertura
./gradlew test jacocoTestReport

# Análisis SonarQube
./gradlew sonarqube
```

---

## 🐛 Solución de Problemas

### Puerto 8080 en uso
```powershell
netstat -ano | findstr :8080
taskkill /PID <PID> /F
```

### Puerto 3000 en uso
```powershell
netstat -ano | findstr :3000
taskkill /PID <PID> /F
```

### Limpiar proyecto
```bash
cd backend && ./gradlew clean
cd frontend && rm -r node_modules && npm install
```

---

## 📋 Checklist de Desarrollo

- [x] Autenticación
- [x] Autorización con roles
- [x] CRUD de posts
- [x] Comentarios y respuestas
- [x] Búsqueda full-text
- [x] Interfaz frontend
- [ ] Estandarizar ResponseEntity (⚠️ 50%)
- [ ] Tests completos (⚠️ 70%)
- [ ] Documentación API

---

## 🎯 Próximas Acciones

1. **Lee:** [RESUMEN_EJECUTIVO.md](./RESUMEN_EJECUTIVO.md) (5 min)
2. **Entiende:** [ANALISIS_ISSUES.md](./ANALISIS_ISSUES.md) (15 min)
3. **Ejecuta:** `run-project.bat both` (1 min)
4. **Desarrolla:** Completa los items pendientes (3-5 horas)

---

## 📞 Contacto / Soporte

Para dudas sobre:
- **Cómo ejecutar:** Ver [QUICK_START.md](./QUICK_START.md)
- **Issues:** Ver [ANALISIS_ISSUES.md](./ANALISIS_ISSUES.md)
- **Arquitectura:** Ver [DASHBOARD.md](./DASHBOARD.md)

---

## 📜 Licencia

Este proyecto es parte de la materia Software Engineering II de la EPCC.

---

**Última actualización:** 12 de enero de 2026  
**Estado:** ⚠️ En Desarrollo (85% completado)