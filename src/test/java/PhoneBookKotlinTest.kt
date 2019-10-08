import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class PhoneBookKotlinTest {

    companion object {
        val NAME = "John Doe"
        val STREET_ADDRESS = "TestStreet 1"
        val EMAIL = "john.doe@email.com"
        val PHONE = "1111 2222 333"
        val AGE = 33
        val BIRTHDAY = Date()
    }


    private lateinit var addressBook: ArrayList<Person>

    private lateinit var phoneBook: PhoneBook

    @BeforeEach
    fun setUp() {
        addressBook = ArrayList()
        phoneBook = PhoneBook(addressBook)
    }

    @Test
    fun testConstructor() {
        val phoneBook = PhoneBook()
        assertNotNull(phoneBook.addressBook)
    }

    @Test
    fun testAntherConstructor() {
        val list = ArrayList<Person>()
        val phoneBook = PhoneBook(list)
        assertFalse(list === phoneBook.addressBook)
        assertTrue(list.containsAll(phoneBook.addressBook))
    }

    @Test
    fun addContact() {
        val contact = Person(
                name = NAME,
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        val result = phoneBook.addContact(contact)

        val addressBook = phoneBook.addressBook

        assertTrue(result)
        assertEquals(1, addressBook.size)
        assertEquals(contact, addressBook[0])
        assertEquals(NAME, addressBook[0].name)
        assertEquals(STREET_ADDRESS, addressBook[0].streetAddress)
        assertEquals(EMAIL, addressBook[0].email)
        assertEquals(PHONE, addressBook[0].phone)
        assertEquals(AGE, addressBook[0].age)
        assertEquals(BIRTHDAY, addressBook[0].birthday)
    }

    @Test
    fun addNotUniqueContact() {
        val contact = Person(
                name = NAME,
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        phoneBook.addContact(contact)

        val result = phoneBook.addContact(contact)
        assertFalse(result)
        assertEquals(1, phoneBook.addressBook.size)
    }

    @Test
    fun findPersonByName() {
        val contactOne = Person(
                name = NAME,
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        val contactTwo = Person(
                name = "test",
                streetAddress = "test",
                email = "test",
                age = 1,
                birthday = Date(),
                phone = "test"
        )

        phoneBook.addContact(contactOne)
        phoneBook.addContact(contactTwo)

        val foundPersons = phoneBook.findPersonByName(NAME)
        assertEquals(1, foundPersons.size)
        assertEquals(contactOne, foundPersons[0])
    }

    @Test
    fun findPersonByNameNoMatch() {
        val contactOne = Person(
                name = NAME,
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactTwo = Person(
                name = "test",
                streetAddress = "test",
                email = "test",
                age = 1,
                birthday = Date(),
                phone = "test"
        )
        addressBook.add(contactOne)
        addressBook.add(contactTwo)

        val foundPersons = phoneBook.findPersonByName("potato")
        assertTrue(foundPersons.isEmpty())
    }

    @Test
    fun searchPersonByName() {
        val contactOne = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        val contactTwo = Person(
                name = "$NAME Second",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        phoneBook.addContact(contactOne)
        phoneBook.addContact(contactTwo)

        val foundPersons = phoneBook.searchPersonByName(NAME)
        assertEquals(2, foundPersons.size)
        assertTrue(foundPersons.containsAll(Arrays.asList(contactOne, contactTwo)))
    }

    @Test
    fun searchPersonByNameNoMatch() {
        val contactOne = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        val contactTwo = Person(
                name = "$NAME Second",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        addressBook.add(contactOne)
        addressBook.add(contactTwo)

        val foundPersons = phoneBook.searchPersonByName("potato")
        assertTrue(foundPersons.isEmpty())
    }

    @Test
    fun getContact() {
        val contact = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val id = contact.id
        phoneBook.addContact(contact)

        val foundContact = phoneBook.getContact(id)

        assertEquals(contact, foundContact)
    }

    @Test
    fun getContactNoMatch() {
        val contact = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val randomID = UUID.randomUUID()
        addressBook.add(contact)

        val foundContact = phoneBook.getContact(randomID)

        assertNull(foundContact)
    }

    @Test
    fun findContactsOnSameAddress() {
        val contactOne = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactTwo = Person(
                name = "$NAME Second",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactThree = Person(
                name = "$NAME Third",
                streetAddress = "potato",
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        phoneBook.addContact(contactOne)
        phoneBook.addContact(contactTwo)
        phoneBook.addContact(contactThree)

        val foundPersons = phoneBook.findContactsOnSameAddress(STREET_ADDRESS)

        assertEquals(2, foundPersons.size)
        assertTrue(foundPersons.containsAll(Arrays.asList(contactOne, contactTwo)))
    }

    @Test
    fun findContactsOnSameAddressNoMatch() {
        val contactOne = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactTwo = Person(
                name = "$NAME Second",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        phoneBook.addContact(contactOne)
        phoneBook.addContact(contactTwo)

        val foundPersons = phoneBook.findContactsOnSameAddress("potato")

        assertTrue(foundPersons.isEmpty())
    }

    @Test
    fun findContactOnSameAddressByPerson() {
        val contactOne = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactTwo = Person(
                name = "$NAME Second",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactThree = Person(
                name = "$NAME Third",
                streetAddress = "potato",
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        phoneBook.addContact(contactOne)
        phoneBook.addContact(contactTwo)
        phoneBook.addContact(contactThree)

        val foundPersons = phoneBook.findContactsOnSameAddress(contactTwo)
        assertEquals(2, foundPersons.size)
        assertTrue(foundPersons.containsAll(Arrays.asList(contactOne, contactTwo)))
    }

    @Test
    fun findContactsOnSameAddressByPersonNoMatch() {
        val contactOne = Person(
                name = "$NAME First",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactTwo = Person(
                name = "$NAME Second",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )
        val contactThree = Person(
                name = "$NAME Third",
                streetAddress = STREET_ADDRESS,
                email = EMAIL,
                age = AGE,
                birthday = BIRTHDAY,
                phone = PHONE
        )

        addressBook.add(contactOne)
        addressBook.add(contactTwo)

        val foundPersons = phoneBook.findContactsOnSameAddress(contactThree)
        assertTrue(foundPersons.isEmpty())
    }
}