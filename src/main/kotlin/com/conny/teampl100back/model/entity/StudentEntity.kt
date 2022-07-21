package com.conny.teampl100back.model.entity

import com.conny.teampl100back.infrastructure.model.entity.BaseEntity

data class StudentEntity(
    val grade: Int,
    val classroom: Int,
    val name: String,
    val gender: String
) : BaseEntity()