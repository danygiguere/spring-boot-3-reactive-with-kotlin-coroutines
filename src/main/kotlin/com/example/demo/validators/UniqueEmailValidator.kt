package com.example.demo.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

import com.example.demo.feature.user.UserService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@MustBeDocumented
@Constraint(validatedBy = [UniqueEmailValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class UniqueEmail(
    val message: String = "{enter_unique_email}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

@Component
class UniqueEmailValidator @Autowired constructor(
    private val userService: UserService
) : ConstraintValidator<UniqueEmail, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value == null) return true
        // Run suspend function in a blocking way for validation
        return runBlocking { userService.findByEmail(value) == null }
    }
}