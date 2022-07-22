package com.conny.teampl100back.service

import com.conny.teampl100back.model.entity.StudentEntity
import com.conny.teampl100back.model.request.StudentRequest


interface StudentService {
    fun create(request: StudentRequest): StudentEntity?

    fun update(id: Int, request: StudentRequest): StudentEntity?

    fun findById(id: Int): StudentEntity?

    fun delete(id: Int): StudentEntity?

    fun findAll(): Collection<StudentEntity>

    fun existsByName(name: String): Boolean
    fun existsById(id: Int): Boolean
}