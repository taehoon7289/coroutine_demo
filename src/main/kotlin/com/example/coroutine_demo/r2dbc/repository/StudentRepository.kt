package com.example.coroutine_demo.r2dbc.repository

import com.example.coroutine_demo.r2dbc.entity.StudentEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface StudentRepository : ReactiveCrudRepository<StudentEntity, Long> {

}