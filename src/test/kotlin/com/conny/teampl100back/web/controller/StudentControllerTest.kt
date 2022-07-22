package com.conny.teampl100back.web.controller

import com.conny.teampl100back.infrastructure.model.response.BaseBodyResponse
import com.conny.teampl100back.infrastructure.model.response.BodyResponse
import com.conny.teampl100back.model.entity.StudentEntity
import com.conny.teampl100back.model.request.StudentRequest
import com.conny.teampl100back.model.response.StudentResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.*

@SpringBootTest
@AutoConfigureMockMvc
internal class StudentControllerTest @Autowired constructor(
    val mockMvc: MockMvc,
    val objectMapper: ObjectMapper
) {
    val baseUrl = "/api/v1/student"

    @Nested
    @DisplayName("POST /api/v1/student")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class CreateStudent {

        @Test
        fun `should add new student`() {
            // given
            val newStudent = StudentRequest(1, 1, 1, "홍길동", "남")
            // when
            val executePost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newStudent)
            }
            //then
            val expectEntity = StudentEntity(1, 1, 1, "홍길동", "남")
            val expectResponse =
                BodyResponse.success(StudentResponse.toEntity(expectEntity), "Create student successful")
            executePost
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse.body
                            )
                        )
                    }
                }
        }

        @Test
        fun `should return AlreadyExistsException by name`() {
            // given
            val existStudent = StudentRequest(1, 1, 1, "홍길동", "남")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existStudent)
            }
            val newStudent = StudentRequest(2, 2, 2, "홍길동", "남")
            // when
            val executePost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newStudent)
            }
            //then
            val expectResponse = BaseBodyResponse(
                status = HttpStatus.CONFLICT.value(),
                message = "이미 존재하는 이름입니다."
            )
            executePost
                .andDo { print() }
                .andExpect {
                    status { isConflict() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse
                            )
                        )
                    }
                }

        }

        @Test
        fun `should return AlreadyExistsException by id`() {
            // given
            val existStudent = StudentRequest(1, 1, 1, "홍길동", "남")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existStudent)
            }
            val newStudent = StudentRequest(1, 2, 2, "홍일", "여")
            // when
            val executePost = mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(newStudent)
            }
            //then
            val expectResponse = BaseBodyResponse(
                status = HttpStatus.CONFLICT.value(),
                message = "이미 존재하는 ID입니다."
            )
            executePost
                .andDo { print() }
                .andExpect {
                    status { isConflict() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse
                            )
                        )
                    }
                }

        }
    }

    @Nested
    @DisplayName("GET /api/v1/student")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetStudent {

        @BeforeEach
        fun `setting data`() {
            val existStudent = StudentRequest(1, 1, 1, "홍길동", "남")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existStudent)
            }
            val existStudent2 = StudentRequest(2, 2, 2, "홍일", "여")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existStudent2)
            }
        }

        @Test
        fun `should get student list `() {
            // given

            // when
            val executeGet = mockMvc.get(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
            }
            //then
            executeGet
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.data", Matchers.hasSize<Array<Any>>(2))
                }
        }

        @Test
        fun `should find student by id`() {
            // given

            // when
            val executeGet = mockMvc.get("$baseUrl/1") {
                contentType = MediaType.APPLICATION_JSON
            }
            //then
            executeGet
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.data.name") { value("홍길동") }
                }
        }

        @Test
        fun `should find student by id return NOT FOUND Exception`() {
            // given

            // when
            val executeGet = mockMvc.get("$baseUrl/3") {
                contentType = MediaType.APPLICATION_JSON
            }
            //then
            val expectResponse = BaseBodyResponse(
                status = HttpStatus.NOT_FOUND.value(),
                message = "해당 학생(id:3)이 존재하지 않음"
            )
            executeGet
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse
                            )
                        )
                    }
                }
        }
    }

    @Nested
    @DisplayName("PUT /api/v1/student")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class UpdateStudent {

        @BeforeEach
        fun `setting data`() {
            val existStudent = StudentRequest(1, 1, 1, "홍길동", "남")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existStudent)
            }
        }

        @Test
        fun `should update student`() {
            // given
            val updateStudent = StudentRequest(grade = 2, classroom = 2, name = "홍일", gender = "여")
            // when
            val executePut = mockMvc.put("$baseUrl/1") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateStudent)
            }
            //then
            val expectEntity = StudentEntity(1, 2, 2, "홍일", "여")
            val expectResponse =
                BodyResponse.success(StudentResponse.toEntity(expectEntity), "Update student successful")
            executePut
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse.body
                            )
                        )
                    }
                }
        }

        @Test
        fun `should update student return not found`() {
            // given
            val updateStudent = StudentRequest(grade = 2, classroom = 2, name = "홍일", gender = "여")
            // when
            val executePut = mockMvc.put("$baseUrl/3") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(updateStudent)
            }
            //then
            val expectResponse = BaseBodyResponse(
                status = HttpStatus.NOT_FOUND.value(),
                message = "해당 학생(id:3)이 존재하지 않음"
            )
            executePut
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse
                            )
                        )
                    }
                }
        }
    }

    @Nested
    @DisplayName("DELETE /api/v1/student")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteStudent {
        @BeforeEach
        fun `setting data`() {
            val existStudent = StudentRequest(1, 1, 1, "홍길동", "남")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existStudent)
            }
            val existStudent2 = StudentRequest(2, 2, 2, "홍일", "여")
            mockMvc.post(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(existStudent2)
            }
        }

        @Test
        fun `should delete student`() {
            // given

            // when
            val executeDelete = mockMvc.delete("$baseUrl/1") {
                contentType = MediaType.APPLICATION_JSON
            }
            //then
            val expectEntity = StudentEntity(1, 1, 1, "홍길동", "남")
            val expectResponse =
                BodyResponse.success(StudentResponse.toEntity(expectEntity), "Delete student successful")
            executeDelete
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse.body
                            )
                        )
                    }
                }
            mockMvc.get(baseUrl) {
                contentType = MediaType.APPLICATION_JSON
            }
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.data", Matchers.hasSize<Array<Any>>(1))
                }
        }

        @Test
        fun `should delete student return Not Found`() {
            // given

            // when
            val executeDelete = mockMvc.delete("$baseUrl/3") {
                contentType = MediaType.APPLICATION_JSON
            }
            //then
            val expectResponse = BaseBodyResponse(
                status = HttpStatus.NOT_FOUND.value(),
                message = "해당 학생(id:3)이 존재하지 않음"
            )
            executeDelete
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(
                            objectMapper.writeValueAsString(
                                expectResponse
                            )
                        )
                    }
                }
            //then

        }
    }
}