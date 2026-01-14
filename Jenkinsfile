pipeline {
    agent any

    triggers {
        // Dispara el pipeline cuando GitHub envía un evento de PUSH (requiere Webhook en GitHub)
        githubPush()
        // Alternativa: Revisa el repositorio cada 5 minutos por si no hay webhooks configurados
        pollSCM('H/5 * * * *')
    }

    stages {
        stage('Checkout SCM') {
            steps {
                // Descarga el código del repositorio configurado en el job
                checkout scm
            }
        }

        stage('Backend: Compilación') {
            steps {
                dir('backend') {
                    script {
                        // Limpia y compila sin ejecutar tests para verificar integridad sintáctica
                        echo 'Iniciando compilación...'
                        if (isUnix()) {
                            sh './gradlew clean assemble -x test'
                        } else {
                            bat 'gradlew clean assemble -x test'
                        }
                    }
                }
            }
        }

        stage('Análisis Estático (SonarQube)') {
            steps {
                dir('backend') {
                    // Requiere que el servidor SonarQube esté configurado en Jenkins con el nombre 'SonarQube'
                    // Si falla la conexión, el bloque withSonarQubeEnv suele manejarlo o fallar el stage.
                    // Se usa catchError por si el servidor no está disponible.
                    script {
                        try {
                            // Ejecutamos sonar directamente. El token ya está en backend/sonar-project.properties
                            // Esto es más robusto y evita errores de configuración en Jenkins.
                            echo 'Ejecutando análisis de calidad de código...'
                            if (isUnix()) {
                                sh './gradlew sonar'
                            } else {
                                bat 'gradlew sonar'
                            }
                        } catch (Exception e) {
                            echo "ERROR EN SONARQUBE: ${e.getMessage()}"
                            // Marcamos como UNSTABLE pero permitimos continuar
                            currentBuild.result = 'UNSTABLE'
                        }
                    }
                }
            }
        }

        stage('Backend: Pruebas Unitarias') {
            steps {
                dir('backend') {
                    script {
                        echo 'Ejecutando pruebas unitarias y mocking...'
                        if (isUnix()) {
                            sh './gradlew unitTest'
                        } else {
                            bat 'gradlew unitTest'
                        }
                    }
                }
            }
            post {
                always {
                    // Recolecta reportes XML compatible con xUnit
                    junit 'backend/build/test-results/unitTest/*.xml'
                }
            }
        }

        stage('Backend: Pruebas Funcionales') {
            steps {
                dir('backend') {
                    script {
                        echo 'Ejecutando pruebas funcionales de integración...'
                        if (isUnix()) {
                            sh './gradlew functionalTest'
                        } else {
                            bat 'gradlew functionalTest'
                        }
                    }
                }
            }
            post {
                always {
                    junit 'backend/build/test-results/functionalTest/*.xml'
                }
            }
        }

        stage('Backend: Seguridad (OWASP & Tests)') {
            parallel {
                stage('Análisis de Dependencias (OWASP)') {
                    steps {
                        // Tolerancia a fallos de conexión con NVD
                        catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                            dir('backend') {
                                script {
                                    echo 'Buscando vulnerabilidades conocidas en dependencias...'
                                    if (isUnix()) {
                                        sh './gradlew dependencyCheckAnalyze'
                                    } else {
                                        bat 'gradlew dependencyCheckAnalyze'
                                    }
                                }
                            }
                        }
                    }
                    post {
                        always {
                             // Publica el reporte HTML de vulnerabilidades si se generó
                             archiveArtifacts artifacts: 'backend/build/reports/dependency-check-report.html', allowEmptyArchive: true
                        }
                    }
                }
                stage('Tests de Autorización y Seguridad') {
                    steps {
                        dir('backend') {
                            script {
                                echo 'Verificando controles de acceso y seguridad...'
                                if (isUnix()) {
                                    sh './gradlew securityTest'
                                } else {
                                    bat 'gradlew securityTest'
                                }
                            }
                        }
                    }
                    post {
                        always {
                            junit 'backend/build/test-results/securityTest/*.xml'
                        }
                    }
                }
            }
        }

        stage('Backend: Pruebas de Rendimiento (Gatling)') {
            steps {
                // Tolerancia a fallos: Gatling requiere que la app esté corriendo.
                // Si no se inicia antes, esto fallará con Connection Refused.
                // Lo marcamos UNSTABLE para no detener el despliegue en este entorno básico de CI.
                catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                    dir('backend') {
                        script {
                            echo 'Ejecutando pruebas de carga y rendimiento...'
                            if (isUnix()) {
                                sh './gradlew gatlingRun'
                            } else {
                                bat 'gradlew gatlingRun'
                            }
                        }
                    }
                }
            }
            post {
                always {
                    // Archiva el sitio estático generado por Gatling
                    archiveArtifacts artifacts: 'backend/build/reports/gatling/**/*', allowEmptyArchive: true
                }
            }
        }

        stage('Empaquetado (Build Artifact)') {
            steps {
                dir('backend') {
                    script {
                        echo 'Generando artefacto desplegable (JAR)...'
                        if (isUnix()) {
                            sh './gradlew bootJar'
                        } else {
                            bat 'gradlew bootJar'
                        }
                    }
                }
            }
            post {
                success {
                    // Archiva el JAR generado para despliegue posterior
                    archiveArtifacts artifacts: 'backend/build/libs/*.jar', fingerprint: true
                }
            }
        }
    }
}
