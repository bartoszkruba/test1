import java.util.*
import kotlin.collections.ArrayList

class PhoneBook {

    val addressBook: ArrayList<Person>

    constructor() : this(ArrayList())

    constructor(addressBook: ArrayList<Person>) {
        this.addressBook = ArrayList(addressBook)
    }

    /**
     * Add a unique person as a contact in the phone book;
     *
     * @param contact The person object to be added.
     * @return True if person was added, false otherwise.
     */
    fun addContact(contact: Person): Boolean = when (addressBook.contains(contact)) {
        true -> false
        false -> {
            addressBook.add(contact)
            true
        }
    }

    /**
     * Search the phone book and return every contact with that name.
     *
     * @param exactName The name that is searched for
     * @return List<Person> a List with matching contacts
     */
    fun findPersonByName(exactName: String): List<Person> = addressBook.filter { p -> p.name.equals(exactName) }

    /**
     * Find persons with a name that matches, the search should be a little fuzzy.
     *
     * @param name : The partial name to look for.
     * @return a List of all the contacts partially matching the name.
     */
    fun searchPersonByName(name: String): List<Person> = addressBook.filter { person ->
        person.name.matches(Regex("(.*)$name(.*)", option = RegexOption.IGNORE_CASE))
    }

    /**
     * Returns the specific contact from the phone book.
     *
     * @param id
     * @return
     */
    fun getContact(id: UUID): Person? = addressBook.find { person -> person.id.equals(id) }

    /**
     * @param address : The address to compare to
     * @return a List of every contact living on the address.
     */
    fun findContactsOnSameAddress(address: String): List<Person> = addressBook.filter { p -> p.streetAddress == address }

    /**
     * @param contact : The contact with an address
     * @return a List of every contact  living on the address.
     */
    fun findContactsOnSameAddress(contact: Person): List<Person> = addressBook.filter { person ->
        person.streetAddress == contact.streetAddress
    }
}