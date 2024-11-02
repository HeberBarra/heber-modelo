plugins {
    java
    id("com.diffplug.spotless") version "6.25.0"
    id("org.springframework.boot") version "3.3.2"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "org.modelador"
version = "0.0.5-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_23
    targetCompatibility = JavaVersion.VERSION_23
}

dependencies {
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
    implementation("org.apache.pdfbox:pdfbox:3.0.3") {
        exclude("commons-logging", "commons-logging")
    }
    implementation("org.fusesource.jansi:jansi:2.4.1")
    implementation("org.hibernate:hibernate-core:6.6.1.Final")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.tomlj:tomlj:1.1.1")
    implementation("mysql:mysql-connector-java:8.0.33")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
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
    this.archiveFileName.set("${archiveBaseName.get()}.${archiveExtension.get()}")
}
