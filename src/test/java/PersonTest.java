import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PersonTest {

    @Test
    void testEquals() {
        var id = UUID.randomUUID();

        var personOne = new Person("John", "Street One", "john@email.com", "111",
                12, new Date());

        personOne.setId(id);

        var personTwo = new Person("Mary", "Street Two", "mary@email.com", "222",
                112, new Date());

        personTwo.setId(id);

        assertEquals(personOne, personTwo);
        assertEquals(personTwo, personOne);
    }

    @Test
    void testNotEquals() {
        var id = UUID.randomUUID();

        var personOne = new Person("John", "Street One", "john@email.com", "111",
                12, new Date());

        var personTwo = new Person("Mary", "Street Two", "mary@email.com", "222",
                112, new Date());

        assertNotEquals(personOne, personTwo);
        assertNotEquals(personTwo, personOne);
    }
}
