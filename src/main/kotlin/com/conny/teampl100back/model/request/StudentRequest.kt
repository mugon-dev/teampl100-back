package com.conny.teampl100back.model.request

data class StudentRequest(
    var id: Int?,
    var grade: Int?,
    var classroom: Int?,
    var name: String?,
    var gender: String?
)