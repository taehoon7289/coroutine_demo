package com.example.coroutine_demo.r2dbc.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table(name = "student")
class StudentEntity(
    @Id private var id: Long? = null,
    @Column private var name: String?,
    )