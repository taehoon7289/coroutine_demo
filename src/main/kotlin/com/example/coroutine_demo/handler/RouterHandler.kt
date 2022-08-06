package com.example.coroutine_demo.handler

import com.example.coroutine_demo.model.Person
import com.example.coroutine_demo.repository.PersonRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.util.logging.Logger

@Component
class RouterHandler(
    private val personRepositoryImpl: PersonRepository,
) {

    private val log = LoggerFactory.getLogger(RouterHandler::class.java)

    suspend fun getPersons(request: ServerRequest): ServerResponse {
        log.info("getPersons :: {}", request)
        return ServerResponse.ok()
            .body(Mono.just(personRepositoryImpl.findAll()), List::class.java)
            .awaitSingle()
    }

    suspend fun getPerson(request: ServerRequest): ServerResponse {
        log.info("getPerson :: {}", request)
        val id = request.pathVariable("id")
        return ServerResponse.ok()
            .body(
                Mono.justOrEmpty(personRepositoryImpl.findById(id)),
                Person::class.java
            ).awaitSingle()
    }

    suspend fun savePerson(request: ServerRequest): ServerResponse {
        log.info("savePerson :: {}", request)
        val person = request.bodyToMono(Person::class.java).awaitSingle()
        return ServerResponse.ok()
            .body(
                Mono.just(
                    personRepositoryImpl.save(
                        id = person.id,
                        name = person.name,
                        age = person.age,
                    )
                ), Person::class.java
            ).awaitSingle()
    }

    suspend fun removePerson(request: ServerRequest): ServerResponse {
        log.info("removePerson :: {}", request)
        val id = request.pathVariable("id")
        return ServerResponse.ok()
            .body(
                Mono.justOrEmpty(personRepositoryImpl.deleteById(id)),
                Person::class.java,
            ).awaitSingle()
    }
}