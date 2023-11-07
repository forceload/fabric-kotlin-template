import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependency.Kotlin.VERSION
    id("fabric-loom") version Dependency.Loom.VERSION
}

group = providers.gradleProperty("group").get()
version = project.extra["mod_version"] as String

val fabricLanguageKotlin =
    "${project.extra["fabric_language_kotlin_version"] as String}+kotlin.${Dependency.Kotlin.VERSION}"
val yarnBuild = "${project.extra["minecraft_version"] as String}+build.${project.extra["yarn_build"] as String}"

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(Dependency.Java.VERSION)) }
}

val fabricVersion = "${project.extra["fabric_version"] as String}+${project.extra["minecraft_version"] as String}"
allprojects {
    apply(plugin = "fabric-loom")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    repositories { mavenCentral() }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Dependency.Java.VERSION.toString()
    }

    dependencies {
        minecraft("com.mojang", "minecraft", project.extra["minecraft_version"] as String)
        mappings("net.fabricmc", "yarn", yarnBuild, null, "v2")

        modImplementation("net.fabricmc", "fabric-loader", project.extra["loader_version"] as String)
        modImplementation("net.fabricmc.fabric-api", "fabric-api", fabricVersion)
        modImplementation("net.fabricmc", "fabric-language-kotlin", fabricLanguageKotlin)
    }

    kotlin { jvmToolchain(Dependency.Java.VERSION) }
}

subprojects {
    // apply(plugin = "fabric-loom")

    tasks {
        jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }
        test { useJUnitPlatform() }

        processResources {
            filesMatching("fabric.mod.json") {
                expand(
                    "version" to project.extra["mod_version"] as String,
                    "fabricloader" to project.extra["loader_version"] as String,
                    "fabric_api" to fabricVersion, "fabric_language_kotlin" to fabricLanguageKotlin,
                    "minecraft" to project.extra["minecraft_version"] as String, "java" to Dependency.Java.VERSION,
                    "mod_id" to project.extra["mod_id"] as String, "group" to project.extra["group"] as String
                )
            }

            filesMatching("*.mixins.json") {
                expand("java" to Dependency.Java.VERSION, "mod_id" to project.extra["mod_id"] as String)
            }

            filesMatching("**/*.json") {
                expand("mod_id" to project.extra["mod_id"] as String)
            }
        }
    }

    dependencies {
        // testImplementation(kotlin("test"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Kotlin.Coroutines.VERSION}")
    }
}