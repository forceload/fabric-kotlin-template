plugins {
    id("java")
}

group = "io.github.forceload"
version = "1.0-SNAPSHOT"

base { archivesName.set(project.extra["archives_base_name"] as String + "-server") }

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}