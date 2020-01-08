plugins {
    java
    `java-library`
    kotlin("jvm") version "1.3.60"
}

group = "me.gwsl.vrouter"
version = "0.1"

val vertxVersion by project.extra { "4.0.0-milestone4" }
val junitJupiterEngineVersion by project.extra { "5.4.0" }
val jdkVersion by project.extra { JavaVersion.VERSION_1_8 }

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(kotlin("stdlib-jdk8"))
    compileOnly("io.vertx:vertx-web:$vertxVersion")
    compileOnly("io.vertx:vertx-lang-kotlin:$vertxVersion")
    compileOnly("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")
    compileOnly("org.slf4j:slf4j-api:1.8.0-alpha2")

    testImplementation("com.fasterxml.jackson.core:jackson-databind:2.9.10")
    testImplementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.10")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterEngineVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterEngineVersion")

}

java {
    sourceCompatibility = jdkVersion
    targetCompatibility = jdkVersion
    withSourcesJar()
    withJavadocJar()
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = jdkVersion.toString()
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = jdkVersion.toString()
    }
    compileJava {
        options.encoding = "utf-8"
    }
}
