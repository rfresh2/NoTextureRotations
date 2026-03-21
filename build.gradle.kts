plugins {
    id("net.fabricmc.fabric-loom") version "1.15-SNAPSHOT"
}

val modVersion = property("mod_version") as String
val mavenGroup = property("maven_group") as String
val archivesBaseName = property("archives_base_name") as String
val minecraftVersion = property("minecraft_version") as String
val loaderVersion = property("loader_version") as String
val fabricVersion = property("fabric_version") as String
val yaclVersion = property("yacl_version") as String
val modmenuVersion = property("modmenu_version") as String
val sodiumVersion = property("sodium_version") as String

version = "$modVersion+26.1"
group = mavenGroup

base {
    archivesName.set(archivesBaseName)
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
    maven {
        name = "CaffeineMC"
        url = uri("https://maven.caffeinemc.net/releases") // or /snapshots
    }
}

dependencies {
    minecraft("com.mojang:minecraft:26.1-rc-2")
    implementation("net.fabricmc:fabric-loader:$loaderVersion")
    implementation("net.fabricmc.fabric-api:fabric-api:$fabricVersion")
    implementation("dev.isxander:yet-another-config-lib:${yaclVersion}+${minecraftVersion}-fabric")
    implementation("maven.modrinth:modmenu:$modmenuVersion")
    implementation(files("lib/sodium-fabric-0.8.6-SNAPSHOT+mc26.1-pre-1-local.jar"))
}

tasks.processResources {
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

tasks.withType<JavaCompile>().configureEach {
    options.release.set(25)
}

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }
}
