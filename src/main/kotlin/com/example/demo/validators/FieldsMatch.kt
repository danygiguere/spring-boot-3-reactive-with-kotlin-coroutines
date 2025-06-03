package com.example.demo.validators

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [FieldsMatchValidator::class])
annotation class FieldsMatch(
    val first: String,
    val second: String,
    val message: String = "Fields do not match",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class FieldsMatchValidator : ConstraintValidator<FieldsMatch, Any> {
    private lateinit var firstFieldName: String
    private lateinit var secondFieldName: String
    private lateinit var message: String

    override fun initialize(constraintAnnotation: FieldsMatch) {
        firstFieldName = constraintAnnotation.first
        secondFieldName = constraintAnnotation.second
        message = constraintAnnotation.message
    }

    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val kClass = value::class
        val first = kClass.memberProperties.find { it.name == firstFieldName }?.getter?.call(value)
        val second = kClass.memberProperties.find { it.name == secondFieldName }?.getter?.call(value)
        if (first == second) return true

        // Attach the error to the second field (e.g., password_confirmation)
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate(message)
            .addPropertyNode(secondFieldName)
            .addConstraintViolation()
        return false
    }
}