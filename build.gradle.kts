plugins {
    java
    id("org.springframework.boot") version "3.4.3"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "8.4"
    id("jacoco")
    id("jacoco-report-aggregation")
}

allprojects {
    group = "com.example"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

}

subprojects {
    apply {
        plugin("java")
        plugin("io.freefair.lombok")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("jacoco")
    }
    dependencies {
        implementation("org.jetbrains:annotations:26.0.0")
    }
    java {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<JacocoReport> {
        dependsOn(tasks.test)

        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }
}

project(":jacoco-support") {
    apply {
        plugin("jacoco-report-aggregation")
    }

    tasks.testCodeCoverageReport {
        dependsOn(subprojects.map { it.tasks.test })

        reports {
            xml.required.set(true)
            html.required.set(true)
        }


//        classDirectories.map {
//            fileTree(it).matching {
//                exclude(
//                    "**/com/example/jacoco/support/**",
//                    "**/com/example/jacoco/support/**",
//                    "**/com/example/jacoco/support/**",
//                    "**/com/example/jacoco/support/**"
//                )
//            }
//        }

//        getClassDirectories().setFrom(
//            subprojects.map {
//                fileTree(it.sourceSets.main.get().output).matching {
//                    exclude(
//                        "org/example/handanddomain/domain/post/infra"
//                    )
//                }
//            }
//        )
        classDirectories.setFrom(
            files(
                listOf(
                    project(":handand-api"),
                    project(":handand-domain")
                ).map { subProject ->
                    fileTree(subProject.layout.buildDirectory.dir("classes/java/main")) {
                        exclude(
                            "**/dto/**",
                            "**/config/**",
                            "**/output/**",
                            "**/infra/**",
                            "**/*Application.*"
                        )
                    }
                }
            )
        )

    }
}

tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = false
}
