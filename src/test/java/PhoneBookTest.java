import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;



class PhoneBookTest {



    private final String NAME = "John Doe";
    private final String STREET_ADDRESS = "TestStreet 1";
    private final String EMAIL = "john.doe@email.com";
    private final String PHONE = "1111 2222 333";
    private final Integer AGE = 33;
    private final Date BIRTHDAY = new Date();

    private ArrayList<Person> addressBook;

    private PhoneBook phoneBook;

    @BeforeEach
    void setUp() {
        addressBook = new ArrayList<>();
        phoneBook = new PhoneBook(addressBook);
    }

    @Test
    void testConstructor() {
        var phoneBook = new PhoneBook();
        assertNotNull(phoneBook.getAddressBook());
    }

    @Test
    void testAntherConstructor() {
        var list = new ArrayList<Person>();
        var phoneBook = new PhoneBook(list);
        assertFalse(list == phoneBook.getAddressBook());
        assertTrue(list.containsAll(phoneBook.getAddressBook()));
    }

    @Test
    void testPassNullToConstructor() {
        assertThrows(RuntimeException.class, () -> new PhoneBook(null));
    }

    @Test
    void addContact() {

        Person contact = new Person(NAME, STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        boolean result = phoneBook.addContact(contact);

        var addressBook = phoneBook.getAddressBook();

        assertTrue(result);
        assertEquals(1, addressBook.size());
        assertEquals(contact, addressBook.get(0));
        assertEquals(NAME, addressBook.get(0).getName());
        assertEquals(STREET_ADDRESS, addressBook.get(0).getStreetAddress());
        assertEquals(EMAIL, addressBook.get(0).getEmail());
        assertEquals(PHONE, addressBook.get(0).getPhone());
        assertEquals(AGE, addressBook.get(0).getAge());
        assertEquals(BIRTHDAY, addressBook.get(0).getBirthday());
    }

    @Test
    void addNotUniqueContact() {
        Person contact = new Person(NAME, STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        phoneBook.addContact(contact);

        boolean result = phoneBook.addContact(contact);
        assertFalse(result);
        assertEquals(1, phoneBook.getAddressBook().size());
    }

    @Test
    void findPersonByName() {
        Person contactOne = new Person(NAME, STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person("test", "test", "email", "phone", 1, new Date());

        phoneBook.addContact(contactOne);
        phoneBook.addContact(contactTwo);

        List<Person> foundPersons = phoneBook.findPersonByName(NAME);
        assertEquals(1, foundPersons.size());
        assertEquals(contactOne, foundPersons.get(0));
    }

    @Test
    void findPersonByNameNoMatch() {
        Person contactOne = new Person(NAME, STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person("test", "test", "email", "phone", 1, new Date());
        addressBook.add(contactOne);
        addressBook.add(contactTwo);

        List<Person> foundPersons = phoneBook.findPersonByName("potato");
        assertTrue(foundPersons.isEmpty());
    }

    @Test
    void searchPersonByName() {
        Person contactOne = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person(NAME + " Second", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        phoneBook.addContact(contactOne);
        phoneBook.addContact(contactTwo);

        List<Person> foundPersons = phoneBook.searchPersonByName(NAME);
        assertEquals(2, foundPersons.size());
        assertTrue(foundPersons.containsAll(Arrays.asList(contactOne, contactTwo)));
    }

    @Test
    void searchPersonByNameNoMatch() {
        Person contactOne = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person(NAME + " Second", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        addressBook.add(contactOne);
        addressBook.add(contactTwo);

        List<Person> foundPersons = phoneBook.searchPersonByName("potato");
        assertTrue(foundPersons.isEmpty());
    }

    @Test
    void getContact() {
        Person contact = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        UUID id = contact.getId();
        phoneBook.addContact(contact);

        Person foundContact = phoneBook.getContact(id);

        assertEquals(contact, foundContact);
    }

    @Test
    void getContactNoMatch() {
        Person contact = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        UUID randomID = UUID.randomUUID();
        addressBook.add(contact);

        Person foundContact = phoneBook.getContact(randomID);

        assertNull(foundContact);
    }

    @Test
    void findContactsOnSameAddress() {
        Person contactOne = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person(NAME + " Second", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactThree = new Person(NAME + " Second", "potato", EMAIL, PHONE, AGE, BIRTHDAY);

        phoneBook.addContact(contactOne);
        phoneBook.addContact(contactTwo);
        phoneBook.addContact(contactThree);

        List<Person> foundPersons = phoneBook.findContactsOnSameAddress(STREET_ADDRESS);

        assertEquals(2, foundPersons.size());
        assertTrue(foundPersons.containsAll(Arrays.asList(contactOne, contactTwo)));
    }

    @Test
    void findContactsOnSameAddressNoMatch() {
        Person contactOne = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person(NAME + " Second", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);

        phoneBook.addContact(contactOne);
        phoneBook.addContact(contactTwo);

        List<Person> foundPersons = phoneBook.findContactsOnSameAddress("potato");

        assertTrue(foundPersons.isEmpty());
    }

    @Test
    void findContactOnSameAddressByPerson() {
        Person contactOne = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person(NAME + " Second", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactThree = new Person(NAME + " Second", "potato", EMAIL, PHONE, AGE, BIRTHDAY);

        phoneBook.addContact(contactOne);
        phoneBook.addContact(contactTwo);
        phoneBook.addContact(contactThree);

        List<Person> foundPersons = phoneBook.findContactsOnSameAddress(contactTwo);
        assertEquals(2, foundPersons.size());
        assertTrue(foundPersons.containsAll(Arrays.asList(contactOne, contactTwo)));
    }

    @Test
    void findContactsOnSameAddressByPersonNoMatch() {
        Person contactOne = new Person(NAME + " First", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactTwo = new Person(NAME + " Second", STREET_ADDRESS, EMAIL, PHONE, AGE, BIRTHDAY);
        Person contactThree = new Person(NAME + " Second", "potato", EMAIL, PHONE, AGE, BIRTHDAY);

        addressBook.add(contactOne);
        addressBook.add(contactTwo);

        List<Person> foundPersons = phoneBook.findContactsOnSameAddress(contactThree);
        assertTrue(foundPersons.isEmpty());
    }


}