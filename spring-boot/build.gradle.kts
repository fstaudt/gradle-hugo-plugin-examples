import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.4"
	id("io.spring.dependency-management") version "1.0.14.RELEASE"
	id("io.github.fstaudt.hugo") version "0.5.0"
	war
	kotlin("jvm") version "1.7.20"
	kotlin("plugin.spring") version "1.7.20"
}

java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

hugo {
	sourceDirectory = "src/main/hugo"
}
tasks.hugoServer {
	baseURL = "http://localhost:1313/documentation"
}
tasks.hugoBuild {
	publicationPath = "static/documentation"
	sourceSets {
		main {
			resources {
				srcDir(outputDirectory)
			}
		}
	}
}
tasks.processResources { dependsOn(tasks.hugoBuild) }
tasks.classes { dependsOn(tasks.hugoBuild) }
