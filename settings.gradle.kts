pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net") { name = "FabricMC" }

        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "fabric-kotlin-template"
include("client")
include("server")
