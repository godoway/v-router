import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    java
    kotlin("jvm")
    kotlin("kapt") 
}

group = "gwsl.demo"
version = "1.0-SNAPSHOT"
val vertxVersion by project.extra { "4.0.0-milestone4" }
val junitJupiterEngineVersion by project.extra { "5.4.0" }
val jdkVersion by project.extra { JavaVersion.VERSION_1_8 }


application {
    mainClassName = "gwsl.demo.VertxLauncher"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(rootProject)
    implementation(kotlin("stdlib-jdk8"))

    implementation("io.vertx:vertx-web:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin:$vertxVersion")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertxVersion")

    implementation("org.apache.logging.log4j:log4j-slf4j18-impl:2.13.0")
    implementation("org.apache.logging.log4j:log4j-core:2.11.2")

    implementation("com.fasterxml.jackson.core:jackson-databind:2.9.10")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.9.10")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.10")

    testImplementation(kotlin("test"))
    testImplementation(kotlin("test-junit5"))
    testImplementation("io.vertx:vertx-junit5:$vertxVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterEngineVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterEngineVersion")

    kapt(rootProject)
}

java {
    sourceCompatibility = jdkVersion
    targetCompatibility = jdkVersion
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
    test {
        useJUnitPlatform()
        testLogging {
            events(TestLogEvent.PASSED, TestLogEvent.FAILED, TestLogEvent.SKIPPED)
        }
    }
}

tasks.register("printPlugins") {
    doLast {
        project.plugins.forEach {
            println(it.toString())
        }
    }
}