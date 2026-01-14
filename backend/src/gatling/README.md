# Pruebas de Rendimiento

Este directorio contiene las pruebas de carga implementadas con **Gatling** para validar el rendimiento del backend bajo diferentes escenarios de uso.

## Requisitos Previos

1. El servidor backend debe estar ejecutándose en `http://localhost:8080`
2. La base de datos debe estar disponible y poblada con datos de prueba

## Ejecución de Pruebas

### Ejecutar todas las simulaciones
```bash
./gradlew gatlingRun
```

### Ejecutar una simulación específica
```bash
# Login masivo
./gradlew gatlingRun-simulations.LoginLoadSimulation

# Lectura de posts
./gradlew gatlingRun-simulations.PostReadLoadSimulation

# Búsqueda (estrés)
./gradlew gatlingRun-simulations.SearchLoadSimulation
```

## Escenarios Implementados

### 1. LoginLoadSimulation
- **Objetivo:** Validar la capacidad del sistema para manejar autenticaciones concurrentes
- **Carga:** 10 usuarios/segundo durante 30 segundos (300 peticiones totales)
- **Criterios de Éxito:**
  - Tiempo de respuesta máximo < 2 segundos
  - 95% de peticiones exitosas

### 2. PostReadLoadSimulation
- **Objetivo:** Medir rendimiento del endpoint más visitado (lectura de posts)
- **Carga:** Escalamiento de 1 a 20 usuarios/segundo durante 1 minuto
- **Criterios de Éxito:**
  - Tiempo promedio de respuesta < 500ms
  - 95% de peticiones < 1 segundo
  - 99% de peticiones exitosas

### 3. SearchLoadSimulation
- **Objetivo:** Prueba de estrés sobre el motor de búsqueda (Hibernate Search)
- **Carga:** 
  - Rampeo: 5 → 50 usuarios/segundo durante 1 minuto
  - Sostenido: 50 usuarios/segundo durante 30 segundos
- **Criterios de Éxito:**
  - 90% de peticiones < 2 segundos
  - Menos del 5% de fallos

## Reportes

Después de ejecutar las pruebas, los reportes HTML se generan automáticamente en:
```
backend/build/reports/gatling/<nombre-simulacion>-<timestamp>/index.html
```

Abre el archivo `index.html` en tu navegador para visualizar:
- Gráficos de tiempo de respuesta
- Distribución de peticiones
- Percentiles y estadísticas detalladas
- Identificación de cuellos de botella

## Interpretación de Resultados

### Métricas Clave
- **Response Time (mean):** Tiempo promedio de respuesta. Meta: < 500ms
- **Response Time (95th percentile):** 95% de peticiones por debajo de este valor. Meta: < 1000ms
- **Requests/sec:** Throughput del sistema. Valor base para comparaciones futuras
- **Success Rate:** Porcentaje de peticiones exitosas. Meta: > 95%

### Optimizaciones Sugeridas
Si las pruebas revelan problemas, considera:
1. **Caché:** Implementar Redis para consultas frecuentes
2. **Índices de BD:** Revisar índices en tablas de posts y usuarios
3. **Connection Pool:** Ajustar tamaño del pool de conexiones Hikari
4. **Query Optimization:** Analizar queries lentas con logs de Hibernate

## Notas Importantes

- **No ejecutar en producción:** Estas pruebas generan carga significativa
- **Ambiente aislado:** Idealmente ejecutar en un ambiente de staging
- **Datos consistentes:** Usar el mismo dataset para comparar resultados entre ejecuciones
