dependencyResolutionManagement {
    repositories {
        mavenCentral()

        maven("https://maven.pkg.github.com/compactmods/feather") {
            name = "Github PKG - Feather"
            credentials {
                username = providers.gradleProperty("gpr.user").orNull
                    ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("gpr.token").orNull
                    ?: System.getenv("GITHUB_TOKEN")
            }
        }

        maven("https://maven.pkg.github.com/compactmods/gander") {
            name = "Github PKG - Gander"
            credentials {
                username = providers.gradleProperty("gpr.user").orNull
                    ?: System.getenv("GITHUB_ACTOR")
                password = providers.gradleProperty("gpr.token").orNull
                    ?: System.getenv("GITHUB_TOKEN")
            }
        }

        maven("https://maven.neoforged.net/releases") {
            name = "NeoForged"
        }

        maven("https://maven.parchmentmc.org") {
            name = "ParchmentMC"
        }
    }

    addVersionCatalog(this, "neoforged")
    addVersionCatalog(this, "mojang")
    addVersionCatalog(this, "compactmods")
    addVersionCatalog(this, "mods")
}

pluginManagement {
    plugins {
        id("idea")
        id("eclipse")
        id("maven-publish")
    }

    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()

        // maven("https://maven.architectury.dev/")

        maven("https://maven.parchmentmc.org") {
            name = "ParchmentMC"
        }

        maven("https://maven.neoforged.net/releases") {
            name = "NeoForged"
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version ("0.8.0")
}

include(":core-api")
include(":neoforge-main")
include(":neoforge-datagen")

fun addVersionCatalog(dependencyResolutionManagement: DependencyResolutionManagement, name: String) {
    dependencyResolutionManagement.versionCatalogs.create(name) {
        from(files("./gradle/$name.versions.toml"))
    }
}
