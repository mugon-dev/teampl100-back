package com.conny.teampl100back.infrastructure.model.response

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class BaseBodyResponse(
    var status: Int? = 200,
    var message: String? = null,
)

data class ResponseStatusObject(
    var message: Any? = null,
    var code: Int? = 200
)

data class BodyResponse(
    var data: Any? = null,
    var status: ResponseStatusObject? = ResponseStatusObject()
) {
    companion object {
        fun success(data: List<*>, message: String? = null): ResponseEntity<Any> {
            val status = ResponseStatusObject(
                message, 200
            )
            val responseObject = BodyResponse(
                data, status
            )
            return ResponseEntity.status(HttpStatus.OK).body(responseObject)
        }

        fun success(data: Any?, message: String? = null): ResponseEntity<Any> {
            val status = ResponseStatusObject(
                message, 200
            )
            val responseObject = BodyResponse(
                data, status
            )
            return ResponseEntity.status(HttpStatus.OK).body(responseObject)
        }
    }
}