plugins {
	java
	id("org.springframework.boot") version "4.1.0"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework:spring-orm:7.0.8")
	implementation("org.springframework:spring-context:7.0.8")
	implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
	implementation("org.postgresql:postgresql:42.7.13")
	implementation("org.hibernate.orm:hibernate-core:7.4.5.Final")
	implementation("org.springframework.data:spring-data-jpa:4.1.0")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
