package com.example.demo.user

import jakarta.validation.Valid
import mu.KLogging
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.i18n.LocaleContext
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ServerWebExchange

@RestController
class UserController(private val userService: UserService) {

    companion object: KLogging()

    @Autowired
    private val messageSource: ResourceBundleMessageSource? = null

    @GetMapping("/demo")
    suspend fun demo(exchange: ServerWebExchange): String {
        val localeContext: LocaleContext = exchange.localeContext
        val locale = localeContext.locale
        LocaleContextHolder.setLocale(locale)
        return messageSource!!.getMessage("welcome", null, LocaleContextHolder.getLocale())
    }

    @GetMapping("/users/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<UserDto> {
        val response = userService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/users")
    suspend fun create(@Valid @RequestBody userDto: UserDto): ResponseEntity<UserDto> {
        val response = userService.create(userDto)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }
}