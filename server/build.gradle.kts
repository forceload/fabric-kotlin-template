plugins {
    base
}

group = project.parent!!.group
version = "1.0-SNAPSHOT"

val fabricLanguageKotlin =
    "${project.extra["fabric_language_kotlin_version"] as String}+kotlin.${parent!!.extra["KOTLIN_VERSION"]}"
val yarnBuild = "${project.extra["minecraft_version"] as String}+build.${project.extra["yarn_build"] as String}"

base {
    archivesName = project.extra["archives_base_name"] as String + "-server"
}

repositories {
    mavenCentral()
}

dependencies {
    minecraft("com.mojang", "minecraft", project.extra["minecraft_version"] as String)
    mappings("net.fabricmc", "yarn", yarnBuild, null, "v2")

    modImplementation("net.fabricmc", "fabric-loader", project.extra["loader_version"] as String)
    modImplementation("net.fabricmc.fabric-api", "fabric-api", parent!!.extra["fabricVersion"] as String)
    modImplementation("net.fabricmc", "fabric-language-kotlin", fabricLanguageKotlin)
}

tasks.test {
    useJUnitPlatform()
}