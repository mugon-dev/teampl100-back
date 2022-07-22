package com.conny.teampl100back.service.implementation

import com.conny.teampl100back.exception.AlreadyExistsException
import com.conny.teampl100back.exception.NotFoundException
import com.conny.teampl100back.exception.NullArgumentException
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
        val newStudent = StudentEntity(
            id = request.id ?: throw NullArgumentException("id 입력해 주세요"),
            grade = request.grade ?: throw NullArgumentException("grade 입력해 주세요"),
            classroom = request.classroom ?: throw NullArgumentException("classroom 입력해 주세요"),
            name = request.name ?: throw NullArgumentException("name 입력해 주세요"),
            gender = request.gender ?: throw NullArgumentException("gender 입력해 주세요"),
        )

        if (existsByName(request.name!!)) throw AlreadyExistsException("이미 존재하는 이름입니다.")
        if (existsById(request.id!!)) throw AlreadyExistsException("이미 존재하는 ID입니다.")
        return studentRepository.createStudent(newStudent)
    }

    override fun update(id: Int, request: StudentRequest): StudentEntity? {
        val willUpdate = this.findById(id) ?: throw NotFoundException("해당 학생(id:${id})이 존재하지 않음")
        val updated = willUpdate.copy(
            grade = request.grade ?: willUpdate.grade,
            classroom = request.classroom ?: willUpdate.classroom,
            name = request.name ?: willUpdate.name,
            gender = request.gender ?: willUpdate.gender
        )
        return studentRepository.updateStudent(updated)
    }

    override fun findById(id: Int): StudentEntity? {
        return studentRepository.findById(id) ?: throw NotFoundException("해당 학생(id:${id})이 존재하지 않음")
    }

    override fun delete(id: Int): StudentEntity? {
        val willDelete = findById(id) ?: throw NotFoundException("해당 학생(id:${id})이 존재하지 않음")
        studentRepository.deleteStudent(willDelete.id)
        return willDelete
    }

    override fun findAll(): Collection<StudentEntity> {
        return studentRepository.findAll() ?: listOf()
    }

    override fun existsByName(name: String): Boolean {
        return studentRepository.existsByName(name)
    }

    override fun existsById(id: Int): Boolean {
        return studentRepository.existsById(id)
    }
}