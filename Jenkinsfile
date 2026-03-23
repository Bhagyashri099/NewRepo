pipeline {
    agent any
    tools {
        maven 'maven3' 
    }

    environment {
        PASS_THRESHOLD = 90.0
        GIT_CREDS = 'Admin'
        REPO_URL = 'github.com/Bhagyashri099/NewRepo.git'
    }

    
    stages {
        stage('Build') {
            steps {
                bat 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test -Dmaven.test.failure.ignore=true'
            }
            post {
                always {
                    script {
                        // 1. Record the results
                        def testResults = junit 'target/surefire-reports/*.xml'
                        
                        // 2. Calculate the percentage
                        double total = testResults.totalCount
                        double passed = testResults.passCount
                        double percent = (total > 0) ? (passed / total) * 100 : 0
                        
                        env.ACTUAL_PASS_PERCENT = percent
                        echo "Captured Pass Percentage: ${env.ACTUAL_PASS_PERCENT}%"

                        // 3. FORCE SUCCESS (This is the missing piece)
                        // This prevents 'skipStagesAfterUnstable' from triggering yet.
                        currentBuild.result = 'SUCCESS'
                    }
                }
            }
        }

        stage('Quality Gate & Revert') {
            steps {
                script {
                    double actual = env.ACTUAL_PASS_PERCENT.toDouble()
                    double limit = env.PASS_THRESHOLD.toDouble()

                    if (actual < limit) {
                        echo "REVERTING: Pass rate ${actual}% is below threshold ${limit}%."

                        withCredentials([usernamePassword(credentialsId: "${GIT_CREDS}", 
                                                         passwordVariable: 'GIT_PASSWORD', 
                                                         usernameVariable: 'GIT_USERNAME')]) {
                            
                            // URL व्यवस्थित तयार करा (https:// काढले तरी चालेल)
                            def rawRepo = env.REPO_URL.replaceFirst('^https?://', '')
                            def fullUrl = "https://${GIT_USERNAME}:${GIT_PASSWORD}@${rawRepo}"

                            // महत्त्वाचे: 'bat' साठी ट्रिपल डबल-कोट्स (""") वापरा
                            bat """
                                @echo off
                                
                                git reset --hard
                                git clean -fd
                                
                               
                                git fetch "${fullUrl}" master
                                
                                
                                git checkout -B master
                                git reset --hard FETCH_HEAD

                                
                                git config user.email "budchane24@gmail.com"
                                git config user.name "Bhagyashri099"

                                
                                git revert --no-edit ${env.GIT_COMMIT}
                                git push "${fullUrl}" master
                            """
                        }
                        error("Build Reverted: Pass rate ${actual}% was too low (Threshold: ${limit}%).")
                    } else {
                        echo "PASSED: Pass rate ${actual}% meets threshold."
                    }
                }
            }
        
        //test

                error("Build Reverted: Pass rate ${actual}% was too low (Threshold: ${limit}%).")
            } else {
                echo "PASSED: Pass rate ${actual}% meets threshold."
            }
        }
    }
}

        stage('Deliver') {
            steps {
                bat "mvn help:evaluate -Dexpression=project.version -DtestRate=${env.ACTUAL_PASS_PERCENT}"
            
            }
        }
    }
}
