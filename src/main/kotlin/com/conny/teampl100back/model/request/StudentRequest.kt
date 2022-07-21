package com.conny.teampl100back.model.request

import com.conny.teampl100back.model.entity.StudentEntity

data class StudentRequest(
    var grade: Int,
    var classroom: Int,
    var name: String,
    var gender: String
) {
    fun toEntity(): StudentEntity {
        return StudentEntity(
            grade, classroom, name, gender
        )
    }
}