import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    `java-library`
    kotlin("jvm") version "2.1.20"
    id("com.gradleup.shadow") version "9.2.2"
}

group = "com.github.grassproject.folra"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")

    implementation(project(":API"))
    // implementation(project("NMS:1_21_8"))
}

kotlin {
    jvmToolchain(21)
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks.register<ShadowJar>("shadowJarPlugin") {
    archiveFileName.set("Folra-${project.version}.jar")

    from(sourceSets.main.get().output)
    configurations = listOf(project.configurations.runtimeClasspath.get())

    exclude("com/zaxxer/**")
    exclude("kotlin/**", "kotlinx/**")
    exclude("org/intellij/**")
    exclude("org/jetbrains/**")
    exclude("org/slf4j/**")
}

tasks {
    build {
        dependsOn("shadowJarPlugin")
    }

    compileJava {
        options.encoding = "UTF-8"
        options.release.set(21)
    }

    processResources {
        val props = mapOf("version" to version)
        inputs.properties(props)
        filteringCharset = "UTF-8"
        filesMatching("paper-plugin.yml") {
            expand(props)
        }
    }
}