import com.blamejared.Properties
import com.blamejared.Versions
import gradle.kotlin.dsl.accessors._1724fa97fa33dee989bc769d32c5910b.versionTracker
import gradle.kotlin.dsl.accessors._3d41e6ef7ce6759a44bc369089ef4003.compileOnly
import org.gradle.jvm.tasks.Jar

plugins {
    id("blamejared-java-conventions")
    id("com.blamejared.gradle-mod-utils")
    id("com.modrinth.minotaur")
    id("net.darkhax.curseforgegradle")
}

configurations {
    register("commonJava") {
        isCanBeResolved = true
    }
    register("commonResources") {
        isCanBeResolved = true
    }
}

dependencies {
    compileOnly(project(":common")) {
        capabilities {
            requireCapability("$group:${Properties.MODID}")
        }
    }
    "commonJava"(project(path = ":common", configuration = "commonJava"))
    "commonResources"(project(path = ":common", configuration = "commonResources"))
}

tasks {
    named<JavaCompile>("compileJava").configure {
        dependsOn(configurations.getByName("commonJava"))
        source(configurations.getByName("commonJava"))
    }

    named<ProcessResources>("processResources").configure {
        dependsOn(configurations.getByName("commonResources"))
        from(configurations.getByName("commonResources"))
    }

    named<Javadoc>("javadoc").configure {
        dependsOn(configurations.getByName("commonJava"))
        source(configurations.getByName("commonJava"))
    }

    named<Jar>("sourcesJar") {
        dependsOn(configurations.getByName("commonJava"))
        dependsOn(configurations.getByName("commonResources"))
        from(configurations.getByName("commonJava"))
        from(configurations.getByName("commonResources"))
    }

}

versionTracker {
    mcVersion.set(Versions.MINECRAFT)
    homepage.set(Properties.CURSE_HOMEPAGE)
    author.set(Properties.AUTHOR)
    projectName.set(Properties.NAME)
}