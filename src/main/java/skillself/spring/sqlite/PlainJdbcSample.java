package skillself.spring.sqlite;

import skillself.spring.sqlite.dao.ContactDao;
import skillself.spring.sqlite.dao.ContactDaoSimpleImpl;
import skillself.spring.sqlite.object.Contact;

import java.sql.Date;
import java.util.Arrays;
import java.util.List;

/**
 * Polytech
 * Created by igor on 05.04.17.
 */
public class PlainJdbcSample {
    private static final ContactDao contactDao = new ContactDaoSimpleImpl();

    public static void main(final String[] args) {
        listAllContacts("Listing initial contact data:");

        System.out.println("Insert а new contact");
        final Contact contact = Contact.builder()
                .firstName("Jack")
                .lastName("Chan")
                .birthDate(Date.valueOf("2017-03-03"))
                .build();
        final long insertedId = contactDao.insert(contact);

        listAllContacts("Listing contact data after new contact created:");

        System.out.println("Deleting the previous created contact");
        contactDao.delete(insertedId);
        listAllContacts("Listing contact data after new contact deleted:");
    }

    private static void listAllContacts(final String ... msg) {
        Arrays.stream(msg).forEach(System.out::println);

        final List<Contact> contacts = contactDao.findAll();
        contacts.forEach(System.out::println);

        System.out.println();
    }
}
