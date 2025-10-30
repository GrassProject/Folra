plugins {
    kotlin("jvm")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

group = "com.github.grassproject.folra.nms"
version = parent!!.version

repositories {
    mavenCentral()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    // gradlePluginPortal()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // paperweight.paperDevBundle("1.21.8-R0.1-SNAPSHOT")
    compileOnly(project(":API"))
}
kotlin {
    jvmToolchain(21)
}