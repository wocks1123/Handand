dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("jakarta.validation:jakarta.validation-api:3.1.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("io.jsonwebtoken:jjwt:0.12.5")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("com.h2database:h2")
}

tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
}
