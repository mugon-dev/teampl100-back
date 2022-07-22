package com.conny.teampl100back.model.request

data class StudentRequest(
    var id: Int? = null,
    var grade: Int? = null,
    var classroom: Int? = null,
    var name: String? = null,
    var gender: String? = null
)