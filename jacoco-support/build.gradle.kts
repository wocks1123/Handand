dependencies {
    jacocoAggregation(project(":handand-api"))
    jacocoAggregation(project(":handand-domain"))
}


tasks.bootJar {
    enabled = false
}
tasks.jar {
    enabled = true
}
