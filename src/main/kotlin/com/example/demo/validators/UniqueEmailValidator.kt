package com.example.demo.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

import com.example.demo.app.user.UserService
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@MustBeDocumented
@Constraint(validatedBy = [UniqueEmailValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class UniqueEmail(
    val message: String = "Email already exists.",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

@Component
class UniqueEmailValidator @Autowired constructor(
    private val userService: UserService
) : ConstraintValidator<UniqueEmail, String> {

    override fun isValid(email: String?, context: ConstraintValidatorContext): Boolean {
//        if (email.isNullOrBlank()) return true // Let @NotEmpty/@NotNull handle this
//        return userService.findByEmail(email) == null
        return true
    }
}