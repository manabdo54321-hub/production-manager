pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ProductionManager"

include(":app")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":feature:production")
include(":feature:tasks")
include(":feature:finance")
include(":feature:analytics")
