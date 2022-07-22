package com.conny.teampl100back.repository

import com.conny.teampl100back.model.entity.StudentEntity

interface StudentRepository {
    fun createStudent(student: StudentEntity): StudentEntity?
    fun updateStudent(student: StudentEntity): StudentEntity?
    fun deleteStudent(studentId: Int)
    fun findAll(): Collection<StudentEntity>?
    fun findById(studentId: Int): StudentEntity?
    fun existsByName(name: String): Boolean
    fun existsById(id: Int): Boolean
}