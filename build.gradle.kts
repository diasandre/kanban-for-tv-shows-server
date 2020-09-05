import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val ktorVersion: String by project
val kotlinVersion: String by project
val koinVersion: String by project
val logbackVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.4.0"
    kotlin("plugin.serialization") version "1.4.0"

    id("org.jlleitschuh.gradle.ktlint") version "9.3.0"
}

group = "br.com.dias.andre"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_1_8

application {
    mainClassName = "io.ktor.server.tomcat.EngineMain"
}

repositories {
    mavenLocal()
    jcenter()
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("io.ktor:ktor-server-tomcat:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-core:$ktorVersion")
    implementation("io.ktor:ktor-server-host-common:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-gson:$ktorVersion")

    implementation("org.koin:koin-ktor:$koinVersion")

    implementation("org.litote.kmongo:kmongo-coroutine:4.1.2")

    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")
