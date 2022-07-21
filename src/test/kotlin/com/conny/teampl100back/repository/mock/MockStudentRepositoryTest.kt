package com.conny.teampl100back.repository.mock

import com.conny.teampl100back.model.entity.StudentEntity
import com.conny.teampl100back.model.request.StudentRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockStudentRepositoryTest {
    private val mockRepository = MockStudentRepository()

    @Test
    fun `add student`() {
        // given
        val student = StudentEntity(1, 6, 4, "강태수", "남")
        // when
        mockRepository.createStudent(student)
        val addedStudent = mockRepository.findById(student.id)
        //then
        assertThat(addedStudent).matches { it?.id == 1 }
    }

    @Test
    fun `should find one student`() {
        // given
        val student = StudentEntity(1, 6, 4, "강태수", "남")
        mockRepository.createStudent(student)
        // when
        val addedStudent = mockRepository.findById(student.id)
        //then
        assertThat(addedStudent).matches { it?.id == 1 }
    }

    @Test
    fun `update student`() {
        // given
        val student = StudentEntity(1, 6, 4, "강태수", "남")
        mockRepository.createStudent(student)
        val request = StudentRequest(
            id = 1,
            grade = 3,
            classroom = 2,
            name = "강태풍",
            gender = "남"
        )
        // when
        val willUpdate = mockRepository.findById(request.id!!)
        val updated = willUpdate?.copy(
            grade = request.grade ?: willUpdate.grade,
            classroom = request.classroom ?: willUpdate.classroom,
            name = request.name ?: willUpdate.name,
            gender = request.gender ?: willUpdate.gender
        )
        mockRepository.updateStudent(updated!!)
        val updatedStudent = mockRepository.findById(student.id)
        //then
        assertThat(updatedStudent).matches { it?.id == 1 }
        assertThat(updatedStudent).matches { it?.grade == 3 }
        assertThat(updatedStudent).matches { it?.classroom == 2 }
        assertThat(updatedStudent).matches { it?.name == "강태풍" }
        assertThat(updatedStudent).matches { it?.gender == "남" }
    }

    @Test
    fun `should delete student`() {
        // given
        val student = StudentEntity(1, 6, 4, "강태수", "남")
        mockRepository.createStudent(student)
        // when
        mockRepository.deleteStudent(1)
        val allStudents = mockRepository.findAll()
        //then
        assertThat(allStudents.size).isEqualTo(0)


    }
}