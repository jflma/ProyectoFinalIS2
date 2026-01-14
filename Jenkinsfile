pipeline {
    agent any

    environment {
        // La integración con SonarQube requiere configurar el servidor 'SonarQube' en Jenkins
        // Asegúrate de tener los plugins necesarios instalados:
        // - Pipeline Maven Integration / Gradle
        // - SonarQube Scanner
        // - Gatling
        // - OWASP Dependency-Check
        // - JUnit
    }

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
                    post {
                        always {
                            // Publica el reporte HTML de vulnerabilidades
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
            post {
                always {
                    // Archiva el sitio estático generado por Gatling
                    archiveArtifacts artifacts: 'backend/build/reports/gatling/**/*', allowEmptyArchive: true
                }
            }
        }

        stage('Análisis Estático (SonarQube)') {
            steps {
                dir('backend') {
                    // Requiere que el servidor SonarQube esté configurado en Jenkins con el nombre 'SonarQube'
                    withSonarQubeEnv('SonarQube') {
                        script {
                            echo 'Ejecutando análisis de calidad de código...'
                            if (isUnix()) {
                                sh './gradlew sonar'
                            } else {
                                bat 'gradlew sonar'
                            }
                        }
                    }
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
