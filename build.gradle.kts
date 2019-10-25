import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
	kotlin("jvm") version "1.3.50"
}

group = "dev.reeve"
version = "1.0-SNAPSHOT"

dependencies {
	implementation(kotlin("stdlib-jdk8"))
	implementation("no.tornado:tornadofx:1.7.17")
	compile("club.minnced:java-discord-rpc:2.0.2")
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