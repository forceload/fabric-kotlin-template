import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("fabric-loom") version Dependency.Loom.VERSION
    kotlin("jvm") version Dependency.Kotlin.VERSION
}

//group = project.extra["group"] as String
group = "io.github.forceload"
version = project.extra["mod_version"] as String

val fabricLanguageKotlin =
    "${project.extra["fabric_language_kotlin_version"] as String}+kotlin.${Dependency.Kotlin.VERSION}"
val yarnBuild = "${project.extra["minecraft_version"] as String}+build.${project.extra["yarn_build"] as String}"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(Dependency.Java.VERSION)) }
}

repositories {
    mavenCentral()
}

val fabricVersion = "${project.extra["fabric_version"] as String}+${project.extra["minecraft_version"] as String}"
dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Kotlin.Coroutines.VERSION}")
}

allprojects {
    // apply { plugin("fabric-loom") }
    tasks.withType<KotlinCompile> { kotlinOptions.jvmTarget = Dependency.Java.VERSION.toString() }

    /*dependencies {
        minecraft("com.mojang", "minecraft", project.extra["minecraft_version"] as String)
        mappings("net.fabricmc", "yarn", yarnBuild, null, "v2")

        modImplementation("net.fabricmc", "fabric-loader", project.extra["loader_version"] as String)
        modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)
        modImplementation("net.fabricmc", "fabric-language-kotlin", fabricLanguageKotlin)
    }*/
}

subprojects {
    // apply("fabric-loom")

    tasks {
        jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }
    }

    dependencies {
        minecraft("com.mojang", "minecraft", project.extra["minecraft_version"] as String)
        mappings("net.fabricmc", "yarn", yarnBuild, null, "v2")

        modImplementation("net.fabricmc", "fabric-loader", project.extra["loader_version"] as String)
        modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)
        modImplementation("net.fabricmc", "fabric-language-kotlin", fabricLanguageKotlin)
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(Dependency.Java.VERSION)
}