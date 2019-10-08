import org.junit.jupiter.api.Test

import java.util.Date
import java.util.UUID

import org.junit.jupiter.api.Assertions.*


internal class PersonKotlinTest {

    @Test
    fun testEquals() {
        val id = UUID.randomUUID()

        val personOne = Person(
                name = "John",
                streetAddress = "Street One",
                email = "john@email.com",
                phone = "111",
                age = 12,
                birthday = Date()
        )

        personOne.id = id

        val personTwo = Person(
                name = "Mary",
                streetAddress = "Street Two",
                email = "mary@email.com",
                phone = "222",
                age = 112,
                birthday = Date()
        )

        personTwo.id = id

        assertEquals(personOne, personTwo)
        assertEquals(personTwo, personOne)
    }

    @Test
    fun testNotEquals() {
        val personOne = Person(
                name = "John",
                streetAddress = "Street One",
                email = "john@email.com",
                phone = "111",
                age = 12,
                birthday = Date()
        )

        val personTwo = Person(
                name = "Mary",
                streetAddress = "Street Two",
                email = "mary@email.com",
                phone = "222",
                age = 112,
                birthday = Date()
        )

        assertNotEquals(personOne, personTwo)
        assertNotEquals(personTwo, personOne)
    }
}