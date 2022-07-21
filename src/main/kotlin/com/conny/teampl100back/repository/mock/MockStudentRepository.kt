package com.conny.teampl100back.repository.mock

import com.conny.teampl100back.model.entity.StudentEntity
import com.conny.teampl100back.repository.StudentRepository
import org.springframework.stereotype.Repository

@Repository
class MockStudentRepository : StudentRepository {
    val students = mutableListOf<StudentEntity>()
    override fun createStudent(student: StudentEntity): StudentEntity? {
        student.id = students.size
        try {
            students.add(student)
            return this.findById(student.id!!)
        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }

    override fun updateStudent(student: StudentEntity): StudentEntity? {
        val willUpdateStudent = this.findById(student.id!!)
        try {
            students.remove(willUpdateStudent)
            students.add(student)
            return this.findById(student.id!!)
        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }

    override fun deleteStudent(studentId: Int) {
        val willDeleteStudent = this.findById(studentId)
        try {
            students.remove(willDeleteStudent)
        } catch (ex: Exception) {
            throw Exception(ex.message)
        }
    }

    override fun findAll(): Collection<StudentEntity> = this.students

    override fun findById(studentId: Int): StudentEntity? = students.firstOrNull { it.id == studentId }
}