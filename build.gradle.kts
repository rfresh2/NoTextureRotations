import me.modmuss50.mpp.ReleaseType
import me.modmuss50.mpp.platforms.modrinth.ModrinthEnvironment

plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.17-SNAPSHOT"
    id("me.modmuss50.mod-publish-plugin") version "2.2.0"
}

val modVersion = property("mod_version") as String
val modReleaseVersion = property("mod_release_version") as String
val modMcVersionStart = property("mod_mc_version_start") as String
val modMcVersionEnd = property("mod_mc_version_end") as String
val mavenGroup = property("maven_group") as String
val archivesBaseName = property("archives_base_name") as String
val minecraftVersion = property("minecraft_version") as String
val parchmentVersion = property("parchment_version") as String
val loaderVersion = property("loader_version") as String
val fabricVersion = property("fabric_version") as String
val yaclVersion = property("yacl_version") as String
val modmenuVersion = property("modmenu_version") as String
val sodiumVersion = property("sodium_version") as String
val javaVersion = property("java_version") as String

version = "$modVersion+$modReleaseVersion"
group = mavenGroup

base {
    archivesName.set(archivesBaseName)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

repositories {
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name = "Parchment"
        url = uri("https://maven.parchmentmc.org")
    }
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
    }
    maven {
        name = "Xander Maven"
        url = uri("https://maven.isxander.dev/releases")
    }
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraftVersion")
    mappings(
        loom.layered {
            officialMojangMappings()
            parchment("org.parchmentmc.data:parchment-$minecraftVersion:$parchmentVersion@zip")
        }
    )
    modImplementation("net.fabricmc:fabric-loader:$loaderVersion")
    modImplementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    modImplementation("dev.isxander:yet-another-config-lib:$yaclVersion+$minecraftVersion-fabric")
    modImplementation("maven.modrinth:modmenu:$modmenuVersion")
    modImplementation("maven.modrinth:sodium:mc$minecraftVersion-$sodiumVersion")
}

tasks {
    processResources {
        inputs.properties(
            mapOf(
                "version" to modVersion,
                "minecraft_version" to minecraftVersion,
            )
        )
        filesMatching("fabric.mod.json") {
            expand(
                mapOf(
                    "version" to modVersion,
                    "minecraft_version" to minecraftVersion,
                )
            )
        }
    }
    jar {
        from("LICENSE") {
            rename { "${it}_${project.base.archivesName.get()}" }
        }
    }
}

publishMods {
    file = tasks.remapJar.get().archiveFile
    changelog = """
        # $modVersion
    """.trimIndent()

    displayName = "$modVersion+$modReleaseVersion"
    version = displayName
    modLoaders = listOf("fabric")
    type = ReleaseType.STABLE

    github {
        accessToken = providers.environmentVariable("GITHUB_TOKEN")
        repository = "rfresh2/NoTextureRotations"
        commitish = "1.20.x"
        tagName = modVersion
        displayName = modVersion
        version = modVersion
    }
    curseforge {
        projectId = "1013466"
        accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
        client = true
        server = false
        minecraftVersionRange {
            start = modMcVersionStart
            end = modMcVersionEnd
        }
        requires("306612") // fabric api
    }
    modrinth {
        projectId = "h4ktIYQ8"
        accessToken = providers.environmentVariable("MODRINTH_TOKEN")
        environment = ModrinthEnvironment.CLIENT_ONLY
        minecraftVersionRange {
            start = modMcVersionStart
            end = modMcVersionEnd
        }
        optional("sodium", "modmenu", "yacl")
        requires("fabric-api")
    }
    dryRun = !providers.environmentVariable("MODRINTH_TOKEN").isPresent
}
