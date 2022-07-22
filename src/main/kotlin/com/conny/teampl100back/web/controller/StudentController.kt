package com.conny.teampl100back.web.controller

import com.conny.teampl100back.constant.RestUriConstant
import com.conny.teampl100back.infrastructure.model.response.BodyResponse
import com.conny.teampl100back.model.request.StudentRequest
import com.conny.teampl100back.model.response.StudentResponse
import com.conny.teampl100back.service.StudentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(
    name = "Student Api",
    description = "Welcome to Student api"
)
@RestController
@RequestMapping(RestUriConstant.STUDENT.INDEX)
class StudentController @Autowired constructor(
    private val studentService: StudentService
) {
    @Operation(summary = "get all students", description = "모든 학생 정보 가져오기")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "OK",
            content = [
                Content(
                    mediaType = "application/json",
                    array = (ArraySchema(schema = Schema(implementation = StudentResponse::class)))
                )
            ]
        )
    )
    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        val data = studentService.findAll().map { StudentResponse.toEntity(it) }
        return BodyResponse.success(data, "Find all Student successful")
    }

    @Operation(summary = "find one students", description = "학생ID로 정보 찾기")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = StudentResponse::class)
            )]
        )
    )
    @GetMapping("{id}")
    fun findOneStudent(@PathVariable id: Int): ResponseEntity<Any> {
        val data = studentService.findById(id)
        return BodyResponse.success(StudentResponse.toEntity(data), "Find student successful")
    }

    @Operation(summary = "post students", description = "학생 정보 등록하기")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = StudentResponse::class)
            )]
        )
    )
    @PostMapping
    fun createStudent(@RequestBody request: StudentRequest): ResponseEntity<Any> {
        val data = studentService.create(request)
        return BodyResponse.success(StudentResponse.toEntity(data), "Create student successful")
    }

    @Operation(summary = "update students", description = "학생 정보 수정")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = StudentResponse::class)
            )]
        )
    )
    @PutMapping("{id}")
    fun updateStudent(@PathVariable id: Int, @RequestBody request: StudentRequest): ResponseEntity<Any> {
        val data = studentService.update(id, request)
        return BodyResponse.success(StudentResponse.toEntity(data), "Update student successful")
    }

    @Operation(summary = "delete students", description = "학생 정보 삭제하기")
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = StudentResponse::class)
            )]
        )
    )
    @DeleteMapping("{id}")
    fun deleteStudent(@PathVariable id: Int): ResponseEntity<Any> {
        val data = studentService.delete(id)
        return BodyResponse.success(StudentResponse.toEntity(data), "Delete student successful")
    }
}