package com.example.demo.exception;

import jakarta.validation.ConstraintViolationException
import mu.KLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class ApplicationExceptionHandler {

    companion object: KLogging()

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleWebExchangeBindException(e: WebExchangeBindException): ResponseEntity<Any> {
        logger.info("*** WebExchangeBindException: $e")
        val errorMap: MutableMap<String, ArrayList<String>> = HashMap()
        e.bindingResult.fieldErrors
            .forEach { error ->
                val message = error.defaultMessage
                if (message != null) {
                    errorMap.getOrPut(error.field, defaultValue = { ArrayList() }).add(message)
                }
            }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errorMap)
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//            .body(mapOf("errors" to errorMap))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(e: IllegalStateException): String {
        logger.info("*** IllegalStateException: $e")
        return e.toString()
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): String {
        logger.info("*** ConstraintViolationException: $e")
        return e.toString()
    }

    @ExceptionHandler(ServerWebInputException::class)
    fun handleServerWebInputException(e: ServerWebInputException): String {
        logger.info("*** ServerWebInputException: $e")
        return e.toString()
    }

}