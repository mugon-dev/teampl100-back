import com.google.cloud.tools.jib.api.buildplan.ImageFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.1"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.cloud.tools.jib") version "3.2.1"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.conny"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // https://mvnrepository.com/artifact/org.springdoc/springdoc-openapi-ui
    implementation("org.springdoc:springdoc-openapi-ui:1.6.9")
    implementation("org.junit.jupiter:junit-jupiter:5.8.1")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // https://mvnrepository.com/artifact/io.mockk/mockk
    testImplementation("io.mockk:mockk:1.12.4")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jib {
    from {
        image = "adoptopenjdk/openjdk11:alpine-jre"
    }
//    to {
//        image = "mugon/${project.name}-${project.version.toString().toLowerCase()}"
//        tags = setOf("1.0")
//    }
    container {
//        entrypoint =
//            listOf("java", "-jar", "teampl100-back-0.0.1-SNAPSHOT.jar")
        mainClass = "com.conny.teampl100back.Teampl100BackApplication"
        jvmFlags = listOf(
            "-Xms512m",
            "-Xmx512m",
            "-Xdebug",
            "-XshowSettings:vm",
            "-XX:+UnlockExperimentalVMOptions",
            "-XX:+UseContainerSupport"
        )
        ports = listOf("8080")

        environment = mapOf("SPRING_OUTPUT_ANSI_ENABLED" to "ALWAYS")

        labels.putAll(
            mapOf(
                "version" to "${project.version}",
                "name" to project.name,
                "group" to "${project.group}"
            )
        )

        creationTime = "USE_CURRENT_TIMESTAMP"
        format = ImageFormat.Docker
    }
    extraDirectories {
        paths {
            path {
                setFrom("build/libs")
            }
        }
    }
}