package com.example.coroutine_demo.route

import com.example.coroutine_demo.handler.RouterHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouteConfig(
    private val routerHandler: RouterHandler
) {

    @Bean
    fun routes() = coRouter {
        accept(MediaType.APPLICATION_JSON).nest {
            GET("/", routerHandler::getPersons)
            GET("/{id}", routerHandler::getPerson)
            POST("/", routerHandler::savePerson)
            DELETE("/{id}", routerHandler::removePerson)
        }

    }

}

