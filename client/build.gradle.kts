plugins {
    base
}

group = "io.github.forceload"
version = "1.0-SNAPSHOT"

base {
    archivesName = project.extra["archives_base_name"] as String + "-client"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}