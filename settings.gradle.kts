pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)

    repositories {
        google()
        mavenCentral()
        // --- Repositorio PÚBLICO y CORRECTO para cardinalmobilesdk ---
        maven { url = uri("https://cardinalcommerce.jfrog.io/artifactory/android") }
    }
}

rootProject.name = "CarRent v1"
include(":app")
