package io.github.fstaudt.hugo.springboot

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

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