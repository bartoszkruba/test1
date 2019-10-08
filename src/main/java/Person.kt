import java.util.*

data class Person constructor(
        val name: String,
        val streetAddress: String,
        val email: String,
        val phone: String,
        val age: Int,
        val birthday: Date
) {
    var id: UUID = UUID.randomUUID()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}