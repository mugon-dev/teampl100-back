package com.conny.teampl100back.service.implementation

import com.conny.teampl100back.exception.NullArgumentException
import com.conny.teampl100back.model.request.StudentRequest
import com.conny.teampl100back.repository.StudentRepository
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

internal class StudentServiceImplTest {
    private val studentRepository: StudentRepository = mockk(relaxed = true)
    private val studentService = StudentServiceImpl(studentRepository)

    @Test
    fun `should add student`() {
        // given
        val request = StudentRequest(
            id = 1,
            grade = 3,
            classroom = 2,
            name = "강태풍",
            gender = "남"
        )
        // when
        val addedStudent = studentService.create(request)
        //then
        assertThat(addedStudent).isNotNull
    }

    @Test
    fun `should add student return null Exception classroom`() {
        // given
        val request = StudentRequest(
            id = 1,
            grade = 3,
            classroom = null,
            name = "강태풍",
            gender = "남"
        )
        // when
        val exception = assertThrows<NullArgumentException> {
            studentService.create(request)
        }
        //then
        assertEquals("classroom 입력해 주세요", exception.message)
    }
}