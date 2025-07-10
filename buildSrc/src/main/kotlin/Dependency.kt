object Dependency {
    object Kotlin {
        const val VERSION = "2.2.0"
        object Coroutines { const val VERSION = "1.9.0-RC" }
    }

    object Java {
        const val VERSION = 21
    }

    object Loom {
        const val VERSION = "1.11-SNAPSHOT"
    }

    object Serialization {
        const val VERSION = Dependency.Kotlin.VERSION
        object Json { const val VERSION = "1.7.1" }
    }
}