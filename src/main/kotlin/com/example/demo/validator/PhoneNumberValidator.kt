package com.example.demo.validator

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PhoneNumberValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsValidPhoneNumber(
    val message: String = "{isValidPhoneNumber}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)

class PhoneNumberValidator : ConstraintValidator<IsValidPhoneNumber?, String?> {
    override fun initialize(contactNumber: IsValidPhoneNumber?) {}
    override fun isValid(
        contactField: String?,
        cxt: ConstraintValidatorContext
    ): Boolean {
        val regex = """[0-9]+""".toRegex()
        return contactField != null && regex.containsMatchIn(contactField) && contactField.length >= 10 && contactField.length < 14
    }
}