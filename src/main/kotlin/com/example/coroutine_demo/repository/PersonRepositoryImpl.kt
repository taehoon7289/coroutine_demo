package com.example.coroutine_demo.repository

import com.example.coroutine_demo.model.Person
import org.springframework.stereotype.Repository

@Repository
class PersonRepositoryImpl : PersonRepository {

    val personDb: MutableMap<String, Person> = HashMap()
    override fun findAll(): List<Person> {
        return personDb.entries.map { (_, value) -> value }
    }

    override fun findById(id: String): Person? {
        return personDb[id]
    }

    override fun save(id: String, name: String, age: Int): Person {
        personDb[id] = Person(
            id = id, name = name, age = age,
        )
        return personDb[id]!!
    }

    override fun deleteById(id: String): Person? {
        return personDb.remove(id)
    }

}