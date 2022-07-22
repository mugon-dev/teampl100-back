package com.conny.teampl100back.web

import com.conny.teampl100back.exception.AlreadyExistsException
import com.conny.teampl100back.exception.NotFoundException
import com.conny.teampl100back.exception.NullArgumentException
import com.conny.teampl100back.infrastructure.model.response.BaseBodyResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ApiResponses(
        ApiResponse(
            responseCode = "500",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = Exception::class)
            )]
        )
    )
    fun handleExceptionOutside(
        ex: Exception,
    ): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(
                BaseBodyResponse(
                    status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    message = ex.message
                )
            )
    }

    @ExceptionHandler(NullArgumentException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ApiResponses(
        ApiResponse(
            responseCode = "400",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BaseBodyResponse::class)
            )]
        )
    )
    fun handleNullArgumentException(
        ex: NullArgumentException,
    ): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(
                BaseBodyResponse(
                    status = HttpStatus.BAD_REQUEST.value(),
                    message = ex.message
                )
            )
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ApiResponses(
        ApiResponse(
            responseCode = "404",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BaseBodyResponse::class)
            )]
        )
    )
    fun handleNotFoundException(
        ex: NotFoundException,
    ): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(
                BaseBodyResponse(
                    status = HttpStatus.NOT_FOUND.value(),
                    message = ex.message
                )
            )
    }

    @ExceptionHandler(AlreadyExistsException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ApiResponses(
        ApiResponse(
            responseCode = "409",
            content = [Content(
                mediaType = "application/json",
                schema = Schema(implementation = BaseBodyResponse::class)
            )]
        )
    )
    fun handleAlreadyExistsException(
        ex: AlreadyExistsException,
    ): ResponseEntity<Any> {
        return ResponseEntity
            .status(HttpStatus.CONFLICT)
            .body(
                BaseBodyResponse(
                    status = HttpStatus.CONFLICT.value(),
                    message = ex.message
                )
            )
    }
}