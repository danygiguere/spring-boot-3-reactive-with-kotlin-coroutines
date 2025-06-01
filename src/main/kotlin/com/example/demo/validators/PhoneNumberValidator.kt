package com.example.demo.validators

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import kotlin.reflect.KClass

@MustBeDocumented
@Constraint(validatedBy = [PhoneNumberValidator::class])
@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsValidPhoneNumber(
    val message: String = "{enter_valid_phone_number}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Any>> = []
)

class PhoneNumberValidator : ConstraintValidator<IsValidPhoneNumber?, String?> {
    override fun initialize(contactNumber: IsValidPhoneNumber?) {}
    override fun isValid(
        contactField: String?,
        cxt: ConstraintValidatorContext
    ): Boolean {
        // Accepts 10-13 digits, with optional dashes (e.g., 555-555-1234 or 5555551234)
        val regex = """^\d{3}-?\d{3}-?\d{4,7}$""".toRegex()
        return contactField != null && regex.matches(contactField)
    }
}