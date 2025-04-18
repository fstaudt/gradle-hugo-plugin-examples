This simple Hugo site has been created with [Hugo Gradle plugin](https://plugins.gradle.org/plugin/io.github.fstaudt.hugo) and embedded in a [Spring Boot](https://spring.io/projects/spring-boot) application. 

- Create Spring Boot project on [start.spring.io](https://start.spring.io/#!type=gradle-project&language=kotlin&platformVersion=2.7.0&packaging=war&jvmVersion=17&groupId=io.github.fstaudt.hugo&artifactId=spring-boot&name=spring-boot&description=Demo%20project%20for%20Spring%20Boot%20integration%20with%20Hugo%20Gradle%20plugin&packageName=io.github.fstaudt.hugo.springboot&dependencies=web)

- Configure Hugo Gradle plugin in `build.gradle.kts`
  ```kotlin
  plugins {
    id("io.github.fstaudt.hugo") version "0.10.0"
  }
  ```

- Configure Hugo extension in `build.gradle.kts` to integrate with Spring Boot
  ```kotlin
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
  ```

- Create hugo site in site directory:
  ```shell
  ./gradlew hugo
  ```

- Add submodule for Hugo theme
  ```shell
  git clone https://github.com/zerostaticthemes/hugo-whisper-theme.git src/main/hugo/themes/hugo-whisper-theme
  tee src/main/hugo/hugo.toml << EOF
  baseURL = "/documentation"
  title = 'Hugo Gradle plugin'
  languageCode = 'en-us'
  theme = "hugo-whisper-theme"
  EOF
  ```

- Configure uglyUrls for Hugo theme to ensure correct rendering as Spring Boot resources
  ```shell
  tee -a src/main/hugo/hugo.toml << EOF
  uglyUrls = true
  EOF
  ```

- Configure Spring Web MVC to redirect base URL to index.html
  ```kotlin
  @Configuration
  class WebMvcConfiguration {
  @Bean
  fun webMvcConfigurer(): WebMvcConfigurer = DocumentationWebMvcConfigurer()

    private class DocumentationWebMvcConfigurer: WebMvcConfigurer {
      override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addRedirectViewController("/documentation", "/documentation/index.html")
        registry.addRedirectViewController("/documentation/", "/documentation/index.html")
      }
    }
  }
  ```

- Render Hugo site on [http://localhost:1313/documentation](http://localhost:1313/documentation)
  ```shell
  ./gradlew hugoServer
  ```

- Start Spring Boot application with embedded Hugo site on [http://localhost:8080/documentation](http://localhost:8080/documentation)
  ```shell
  ./gradlew bootRun
  ```
