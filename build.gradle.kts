import org.jetbrains.kotlin.gradle.tasks.KotlinCompile



buildscript {
	val kotlinVersion = "1.6.20"
	val springBootVersion = "2.6.6"

	repositories {
		mavenCentral()
	}
	dependencies {
		classpath("org.springframework.boot:spring-boot-gradle-plugin:${springBootVersion}")
		classpath(kotlin("gradle-plugin", version = kotlinVersion))
		classpath(kotlin("allopen", version = kotlinVersion))
	}
}

plugins {
	id("org.springframework.boot") version "2.6.6"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	war
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}


group = "com.ksc91u"
version = "0.0.1-SNAPSHOT"

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

repositories {
	mavenCentral()
}


dependencies {
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation(kotlin("stdlib", org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION))
	implementation(kotlin("reflect"))
	implementation("org.springframework.boot:spring-boot-starter-freemarker")
	implementation("com.google.code.gson:gson:2.8.6")

	implementation("com.aventrix.jnanoid:jnanoid:2.0.0")

	//derby
	//implementation("org.apache.derby:derby")
	//hsql
	implementation("org.hsqldb:hsqldb:2.6.1")
	runtimeOnly("org.hsqldb:hsqldb:2.6.1")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation(group = "org.springframework", name = "spring-jdbc", version = "5.3.19")


	implementation("com.squareup.retrofit2:retrofit:2.6.2")
	implementation("com.squareup.retrofit2:adapter-rxjava2:2.6.2")
	implementation("com.squareup.retrofit2:converter-gson:2.6.2")
	implementation("com.squareup.okhttp3:okhttp:3.14.2")

	runtimeOnly("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

