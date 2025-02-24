plugins {
    java
    alias(libs.plugins.spotless)
    alias(libs.plugins.springboot)
    alias(libs.plugins.springdependency)
}

group = "io.github.heberbarra"
version = "0.0.16-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

dependencies {
    implementation(libs.apachePDFBox) {
        exclude("commons-logging", "commons-logging")
    }
    implementation(libs.cdimascioDotenv)
    implementation(libs.fusesourceJansi)
    implementation(libs.hibernateCore)
    implementation(libs.heberModeloAPI)
    implementation(libs.mysqlConnector)
    implementation(libs.tomlj)
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

spotless {

    java {
        importOrder()
        removeUnusedImports()
        cleanthat()
        palantirJavaFormat()
        formatAnnotations()
    }

}

tasks.test {
    useJUnitPlatform()
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    exclude("static/scss/")
    exclude("static/typescript")
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}
