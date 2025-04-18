plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.github.fstaudt.hugo") version "0.10.0"
    war
    kotlin("jvm") version embeddedKotlinVersion
    kotlin("plugin.spring") version embeddedKotlinVersion
}

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

tasks.test { useJUnitPlatform() }

hugo {
    sourceDirectory = "src/main/hugo"
    // required for compatibility with Hugo whisper theme
    // cfr https://github.com/zerostaticthemes/hugo-whisper-theme/issues/43
    version = "0.131.0"
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
