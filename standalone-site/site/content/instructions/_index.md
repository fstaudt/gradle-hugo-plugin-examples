This simple Hugo site has been created with [Hugo Gradle plugin](https://plugins.gradle.org/plugin/io.github.fstaudt.hugo)

- Configure Hugo Gradle plugin in `build.gradle.kts`
  ```kotlin
  plugins {
    id("io.github.fstaudt.hugo") version "0.10.0"
  }
  ```

- configure hugo extension in `build.gradle.kts` to use Hugo version compatible with Hugo theme
    ```kotlin
    hugo {
      // required for compatibility with Hugo whisper theme
      // cfr https://github.com/zerostaticthemes/hugo-whisper-theme/issues/43
      version = "0.131.0"
    }
    ```

- Create hugo site in site directory:
  ```shell
  ./gradlew hugo
  ```

- Add submodule for Hugo theme
  ```shell
  git clone https://github.com/zerostaticthemes/hugo-whisper-theme.git site/themes/hugo-whisper-theme
  tee site/hugo.toml << EOF
  baseURL = "/"
  title = 'Hugo Gradle plugin'
  languageCode = 'en-us'
  theme = "hugo-whisper-theme"
  EOF
  ```

- Render Hugo site on [http://localhost:1313](http://localhost:1313)
  ```shell
  ./gradlew hugoServer
  ```
