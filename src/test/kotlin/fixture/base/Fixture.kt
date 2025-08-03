package fixture.base

import io.bloco.faker.Faker

/**
 * Base fixture class that provides common instance creation functionality.
 *
 * @param T The type of object this fixture creates
 * @param B The builder type used to construct instances of T
 */
abstract class Fixture<T, B : Any> {
    protected val faker = Faker()

    /**
     * Creates a builder instance with default values
     */
    protected abstract fun createBuilder(): B

    /**
     * Builds the final instance from the builder
     */
    protected abstract fun build(builder: B): T

    /**
     * Creates a single instance with default or overridden values
     */
    fun createOne(block: B.() -> Unit = {}): T {
        val builder = createBuilder()
        block(builder)
        return build(builder)
    }

    /**
     * Creates multiple instances with the same overrides
     */
    fun createMany(count: Int, block: B.() -> Unit = {}): List<T> {
        return List(count) { createOne(block) }
    }
}