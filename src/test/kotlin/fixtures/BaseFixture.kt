package fixtures

import io.bloco.faker.Faker
import java.time.LocalDateTime
import java.util.*

/**
 * Base fixture class that provides common functionality for all fixtures
 */
abstract class BaseFixture<T> {
    protected val faker = Faker(locale = "en-CA")
    protected val random = Random()

    protected val defaultId: Long = 1L
    protected val defaultDate: LocalDateTime = LocalDateTime.now()
    protected val defaultString: String = "test"
    protected val defaultEmail: String = "test@example.com"

    /**
     * Creates a builder for the fixture
     */
    abstract fun builder(): T

    /**
     * Creates a default instance of the fixture
     */
    abstract fun createDefault(): T

    /**
     * Creates multiple instances of the fixture
     */
    fun createMany(count: Int): List<T> {
        return (1..count).map { createDefault() }
    }
}