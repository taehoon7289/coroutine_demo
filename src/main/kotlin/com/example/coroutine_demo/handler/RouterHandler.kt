package com.example.coroutine_demo.handler

import com.example.coroutine_demo.model.Person
import com.example.coroutine_demo.model.Student
import com.example.coroutine_demo.r2dbc.entity.StudentEntity
import com.example.coroutine_demo.r2dbc.repository.StudentRepository
import com.example.coroutine_demo.repository.PersonRepository
import kotlinx.coroutines.reactor.awaitSingle
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.reactive.function.server.EntityResponse.fromObject
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.util.stream.Collectors.toList

@Component
class RouterHandler(
    private val personRepositoryImpl: PersonRepository,
    private val studentRepository: StudentRepository,
) {

    private val log = LoggerFactory.getLogger(RouterHandler::class.java)

    suspend fun getPersons(request: ServerRequest): ServerResponse {
        log.info("getPersons :: {}", request)
        return ServerResponse.ok().body(Mono.just(personRepositoryImpl.findAll()), List::class.java)
            .awaitSingle()
    }

    suspend fun getPerson(request: ServerRequest): ServerResponse {
        log.info("getPerson :: {}", request)
        val id = request.pathVariable("id")
        return ServerResponse.ok().body(
            Mono.justOrEmpty(personRepositoryImpl.findById(id)), Person::class.java
        ).awaitSingle()
    }

    suspend fun savePerson(request: ServerRequest): ServerResponse {
        log.info("savePerson :: {}", request)
        val person = request.bodyToMono(Person::class.java).awaitSingle()
        return ServerResponse.ok().body(
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
        return ServerResponse.ok().body(
            Mono.justOrEmpty(personRepositoryImpl.deleteById(id)),
            Person::class.java,
        ).awaitSingle()
    }


    @Transactional
    suspend fun getStudents(request: ServerRequest): ServerResponse {
        log.info("getStudents :: {}", request)
        return studentRepository.findAll().collect(toList())
            .flatMap { ok().body(fromObject(it).build()) }.awaitSingle()
    }

    @Transactional
    suspend fun getStudent(request: ServerRequest): ServerResponse {
        log.info("getStudent :: {}", request)
        val id = request.pathVariable("id").toLong()
        return studentRepository.findById(id).flatMap { ok().body(fromObject(it).build()) }
            .awaitSingle()
    }

    @Transactional
    suspend fun saveStudent(request: ServerRequest): ServerResponse {
        log.info("saveStudent :: {}", request)
        val student = request.bodyToMono(Student::class.java).awaitSingle()
        return studentRepository.save(
            StudentEntity(
                name = student.name,
            )
        ).flatMap { ok().body(fromObject(it).build()) }.awaitSingle()
    }

    @Transactional
    suspend fun removeStudent(request: ServerRequest): ServerResponse {
        log.info("removeStudent :: {}", request)
        val id = request.pathVariable("id").toLong()
        return studentRepository.deleteById(id).flatMap { ok().body(fromObject(it).build()) }
            .awaitSingle()
    }

}