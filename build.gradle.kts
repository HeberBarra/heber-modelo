plugins {
    java
    id("com.diffplug.spotless") version "6.25.0"
}

group = "org.modelador"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    implementation("org.fusesource.jansi:jansi:2.4.1")
    implementation("org.jetbrains:annotations:24.1.0")
    implementation("com.miglayout:miglayout:3.7.4")
    implementation("org.tomlj:tomlj:1.1.1")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.modelador.Principal"
        attributes["Implementation-Version"] = version
    }

    from (
        configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }
    )

    duplicatesStrategy = DuplicatesStrategy.INCLUDE
    project.version = ""
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