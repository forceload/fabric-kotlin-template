import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version Dependency.Kotlin.VERSION
    id("fabric-loom") version Dependency.Loom.VERSION apply false
}

group = providers.gradleProperty("group").get()
version = project.extra["mod_version"] as String

ext {
    extra["fabricLanguageKotlin"] =
        "${project.extra["fabric_language_kotlin_version"] as String}+kotlin.${Dependency.Kotlin.VERSION}"
    extra["yarnBuild"] = "${project.extra["minecraft_version"] as String}+build.${project.extra["yarn_build"] as String}"

    extra["fabricVersion"] = "${project.extra["fabric_version"] as String}+${project.extra["minecraft_version"] as String}"
    extra["KOTLIN_VERSION"] = Dependency.Kotlin.VERSION
}

java {
    toolchain { languageVersion.set(JavaLanguageVersion.of(Dependency.Java.VERSION)) }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    repositories { mavenCentral() }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Dependency.Java.VERSION.toString()
    }

    kotlin { jvmToolchain(Dependency.Java.VERSION) }
}

subprojects {
    apply(plugin = "fabric-loom")

    tasks {
        jar { from("LICENSE") { rename { "${it}_${base.archivesName}" } } }
        test { useJUnitPlatform() }

        processResources {
            filesMatching("fabric.mod.json") {
                expand(
                    "version" to project.extra["mod_version"] as String,
                    "fabricloader" to project.extra["loader_version"] as String,
                    "fabric_api" to project.parent!!.extra["fabricVersion"] as String,
                    "fabric_language_kotlin" to project.parent!!.extra["fabricLanguageKotlin"] as String,
                    "minecraft" to project.extra["minecraft_version"] as String, "java" to Dependency.Java.VERSION,
                    "mod_id" to project.extra["mod_id"] as String, "mod_entrypoint" to project.parent!!.extra["mod_entrypoint"],
                    "group" to project.parent!!.providers.gradleProperty("group")
                )
            }

            filesMatching("*.mixins.json") {
                expand(
                    "java" to Dependency.Java.VERSION, "mod_id" to project.extra["mod_id"] as String,
                    "group" to project.parent!!.providers.gradleProperty("group")
                )
            }

            filesMatching("**/*.json") {
                expand("mod_id" to project.extra["mod_id"] as String,)
            }
        }
    }

    dependencies {
        // testImplementation(kotlin("test"))

        implementation(kotlin("stdlib"))
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependency.Kotlin.Coroutines.VERSION}")
    }
}