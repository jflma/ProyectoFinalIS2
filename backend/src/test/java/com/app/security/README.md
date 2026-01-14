# Pruebas de Seguridad

Este directorio contiene pruebas automatizadas para validar la seguridad del backend, incluyendo autorización, autenticación JWT y protección contra vulnerabilidades comunes.

## Tipos de Pruebas Implementadas

### 1. Pruebas de Autorización (`AuthorizationSecurityTest`)
Valida que los controles de acceso basados en roles funcionen correctamente y que los endpoints críticos estén protegidos.

**Escenarios cubiertos:**
- ✅ Acceso denegado a endpoints protegidos sin autenticación (401)
- ✅ Endpoints públicos accesibles sin token (signup, get posts)
- ✅ Credenciales inválidas son rechazadas
- ✅ Prevención básica de inyección con inputs maliciosos
- ✅ JSON malformado es rechazado

## Ejecución

### Ejecutar pruebas de seguridad
```bash
./gradlew test --tests "com.app.security.*"
```

### Escaneo de dependencias vulnerables (OWASP)
```bash
./gradlew dependencyCheckAnalyze
```

El reporte se generará en:
```
backend/build/reports/dependency-check-report.html
```

## Interpretación de Resultados

### Pruebas Funcionales
- **PASS**: El sistema rechaza correctamente accesos no autorizados
- **FAIL**: Posible vulnerabilidad de seguridad que requiere corrección inmediata

### OWASP Dependency Check
- **Severidad Alta/Crítica**: Actualizar dependencia de inmediato
- **Severidad Media**: Planificar actualización en próximo sprint
- **Severidad Baja**: Monitorear y evaluar

## Mejores Prácticas

### Tokens JWT
- ✅ Tiempo de expiración: 1 hora (configurado en `JWTService`)
- ✅ Algoritmo: HMAC256 con clave de 256 bits
- ✅ Claims mínimos: subject (username) + roles

### Contraseñas
- ✅ Hasheadas con BCrypt (configurado en `SecurityConfig`)
- ✅ Nunca almacenadas en texto plano

### Headers de Seguridad
El backend debería incluir (verificar en respuestas HTTP):
- `X-Content-Type-Options: nosniff`
- `X-Frame-Options: DENY`
- `X-XSS-Protection: 1; mode=block`

## Vulnerabilidades Comunes Cubiertas

| Vulnerabilidad | Protección | Test |
|---------------|-----------|------|
| Inyección SQL | JPA + Prepared Statements | `testSQLInjectionAttemptIsRejected` |
| XSS | Sanitización de inputs | `testXSSAttemptIsSanitized` |
| CSRF | Token-based auth (stateless) | N/A |
| Broken Authentication | JWT validation | `JWTSecurityTest` |
| Broken Access Control | Role-based authorization | `AuthorizationSecurityTest` |

## Mantenimiento

### Actualización de Dependencias
Ejecutar semanalmente para detectar nuevas vulnerabilidades:
```bash
./gradlew dependencyCheckUpdate
./gradlew dependencyCheckAnalyze
```

### Agregar Nuevas Pruebas
Al agregar endpoints protegidos, crear tests en este directorio validando:
1. Acceso denegado sin autenticación
2. Acceso denegado con token inválido
3. Acceso permitido con credenciales correctas
4. Acceso denegado si el rol no coincide

## Recursos Adicionales

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/)
- [JWT Best Practices](https://tools.ietf.org/html/rfc8725)
