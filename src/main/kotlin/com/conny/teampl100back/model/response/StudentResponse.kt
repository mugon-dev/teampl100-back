package com.conny.teampl100back.model.response

import com.conny.teampl100back.model.entity.StudentEntity

data class StudentResponse(
    var id: Int? = null,
    val grade: Int? = null,
    val classroom: Int? = null,
    val name: String? = null,
    val gender: String? = null
) {
    companion object {
        fun toEntity(entity: StudentEntity?): StudentResponse? {
            entity ?: return null

            return StudentResponse(
                id = entity.id,
                grade = entity.grade,
                classroom = entity.classroom,
                name = entity.name,
                gender = entity.gender,
            )
        }
    }

}