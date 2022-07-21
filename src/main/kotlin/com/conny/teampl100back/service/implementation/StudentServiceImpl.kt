package com.conny.teampl100back.service.implementation

import com.conny.teampl100back.model.entity.StudentEntity
import com.conny.teampl100back.model.request.StudentRequest
import com.conny.teampl100back.repository.StudentRepository
import com.conny.teampl100back.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class StudentServiceImpl @Autowired constructor(
    private val studentRepository: StudentRepository
) : StudentService {
    override fun create(request: StudentRequest): StudentEntity? {

        return studentRepository.createStudent(request.toEntity())
    }

    override fun update(id: Int, request: StudentRequest): StudentEntity? {
        val willUpdate = this.findById(id) ?: throw Exception("해당 학생(id:${id})이 존재하지 않음")
        val updated = willUpdate.copy(
            grade = request.grade,
            classroom = request.classroom,
            name = request.name,
            gender = request.gender
        )
        return studentRepository.updateStudent(updated)
    }

    override fun findById(id: Int): StudentEntity? {
        return studentRepository.findById(id) ?: throw Exception("해당 학생(id:${id})이 존재하지 않음")
    }

    override fun delete(id: Int): StudentEntity? {
        val willDelete = findById(id) ?: throw Exception("해당 학생(id:${id})이 존재하지 않음")
        studentRepository.deleteStudent(willDelete.id!!)
        return willDelete
    }

    override fun findAll(): Collection<StudentEntity> {
        return studentRepository.findAll() ?: listOf()
    }
}