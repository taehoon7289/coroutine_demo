package com.example.coroutine_demo.repository

import com.example.coroutine_demo.model.Person

interface PersonRepository {

    fun findAll(): List<Person>

    fun findById(id: String): Person?

    fun save(id: String, name: String, age: Int): Person

    fun deleteById(id: String): Person?

}