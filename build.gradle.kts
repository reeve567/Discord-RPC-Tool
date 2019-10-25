import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI
import kotlin.collections.mutableListOf

plugins {
	kotlin("jvm") version "1.3.50"
	id("com.github.johnrengelman.shadow") version "5.1.0"
	java
}

group = "dev.reeve"
version = "1.0-SNAPSHOT"


dependencies {
	compile(kotlin("stdlib-jdk8"))
	compile("no.tornado:tornadofx:1.7.17")
	compile("club.minnced:java-discord-rpc:2.0.2")
	compileClasspath("com.github.jengelman.gradle.plugins:shadow:5.1.0")
}

repositories {
	mavenCentral()
	jcenter()
	maven {
		url = URI("https://jitpack.io/")
	}
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
	jvmTarget = "1.8"
}

val compileTestKotlin: KotlinCompile by tasks
compileTestKotlin.kotlinOptions {
	jvmTarget = "1.8"
}

tasks {
	jar {
		classifier = null
		manifest {
			attributes["Main-Class"] = "dev.reeve.discordrpctool.MainAppKt"
		}
	}
	shadowJar {
		minimize()
		configurations = mutableListOf(project.configurations.compile.get())
	}
}