plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
//	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
//	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("io.github.microutils:kotlin-logging-jvm:2.0.11")
	implementation("io.asyncer:r2dbc-mysql:1.1.0")
	implementation("org.flywaydb:flyway-core:10.8.1")
	implementation("org.flywaydb:flyway-mysql:10.8.1")
	implementation("io.bloco:faker:2.0.4")
	implementation("com.auth0:java-jwt:4.4.0")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	runtimeOnly("com.h2database:h2")
	runtimeOnly("com.mysql:mysql-connector-j")
	runtimeOnly("io.r2dbc:r2dbc-h2")
	testImplementation("com.ninja-squad:springmockk:4.0.2")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
	testImplementation("org.springframework.security:spring-security-test")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.test {
	useJUnitPlatform()
	systemProperty("spring.profiles.active", "test")
}