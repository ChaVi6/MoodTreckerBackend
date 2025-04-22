
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.plugin.serialization)
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "com.example.ApplicationKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
//    implementation(libs.ktor.server.cio)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
//    implementation(libs.exposed.core)
//    implementation(libs.exposed.jdbc)
    implementation("com.oracle.database.jdbc:ojdbc8:19.8.0.0")
    implementation("org.jetbrains.exposed:exposed-core:0.39.1")
    implementation("org.jetbrains.exposed:exposed-dao:0.39.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.39.1")
    implementation("org.mindrot:jbcrypt:0.4")
    implementation(libs.h2)
    implementation("io.ktor:ktor-server-cio-jvm:1.5.3")
    implementation(libs.ktor.server.netty)
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
