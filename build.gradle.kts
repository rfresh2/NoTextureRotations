plugins {
    id("net.fabricmc.fabric-loom-remap") version "1.15-SNAPSHOT"
}

val modVersion = property("mod_version") as String
val mavenGroup = property("maven_group") as String
val archivesBaseName = property("archives_base_name") as String
val minecraftVersion = property("minecraft_version") as String
val parchmentVersion = property("parchment_version") as String
val loaderVersion = property("loader_version") as String
val fabricVersion = property("fabric_version") as String
val yaclVersion = property("yacl_version") as String
val modmenuVersion = property("modmenu_version") as String
val sodiumVersion = property("sodium_version") as String

version = "$modVersion+1.20.x"
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
    options.release.set(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName.get()}" }
    }
}
