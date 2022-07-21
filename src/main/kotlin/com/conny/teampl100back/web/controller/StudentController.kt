package com.conny.teampl100back.web.controller

import com.conny.teampl100back.constant.RestUriConstant
import com.conny.teampl100back.infrastructure.model.response.BodyResponse
import com.conny.teampl100back.service.StudentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(RestUriConstant.STUDENT.INDEX)
class StudentController @Autowired constructor(
    private val studentService: StudentService
) {
    @GetMapping
    fun findAll(): ResponseEntity<Any> {
        val data = studentService.findAll()
        return BodyResponse.success(data, "Find all Student")
    }
}