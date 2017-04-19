package skillself.spring.sqlite.dao;

import org.junit.Test;
import skillself.spring.sqlite.object.Contact;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * sqlite.with.spring
 * Created by igor on 18.04.17.
 */

public class ContactDaoTest {
    private ContactDao contactDao;

    @Test
    public void findAllEmpty() {
        assertThat(contactDao.findAll())
                .hasSize(0)
                .isNotNull();
    }

    @Test
    public void findAllNotEmpty() {
        final Contact contact = someContact();
        contactDao.insert(contact);

        assertThat(contactDao.findAll())
                .isNotNull()
                .hasSize(1)
                .contains(contact);
    }

    @Test
    public void findByFirstNameNotExists() {
        assertThat(contactDao.findByFirstName("someName"))
                .isNotNull()
                .hasSize(0);
    }

    @Test
    public void findByFirstNameExists() {
        final Contact someContact = someContact();
        contactDao.insert(someContact);

        assertThat(contactDao.findByFirstName(someContact.getFirstName()))
                .isNotNull()
                .hasSize(1)
                .contains(someContact);

    }

    @Test
    public void insert() {
        final Contact contact = someContact();
        assertThat(contactDao.insert(contact))
                .isNotNull()
                .matches(contact1 -> contact1.getId() > 0)
                .hasNoNullFieldsOrProperties()
                .isEqualTo(contact);
    }

    @Test
    public void findLastNameByIdNotExists() {
        assertThatThrownBy(() -> contactDao.findLastNameById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id 1 is not exists");
    }

    @Test
    public void findLastNameByIdExists() {
        final Contact contact = contactDao.insert(someContact());

        assertThat(contactDao.findLastNameById(contact.getId()))
                .isEqualTo(contact.getLastName());
    }

    @Test
    public void findFirstNameByIdNotExists() {
        assertThatThrownBy(() -> contactDao.findFirstNameByid(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id 1 is not exists");
    }

    @Test
    public void findFirstNameByIdExists() {
        final Contact contact = contactDao.insert(someContact());

        assertThat(contactDao.findFirstNameByid(contact.getId()))
                .isEqualTo(contact.getFirstName());
    }

    @Test
    public void updateNotExists() {
        assertThat(contactDao.update(someContact()))
                .isFalse();
    }

    @Test
    public void updateExists() {
        final Contact contact = contactDao.insert(someContact());
        final Contact newContact = Contact
                .builder()
                .id(contact.getId())
                .birthDate(contact.getBirthDate())
                .lastName(contact.getLastName())
                .firstName("newFirstName")
                .build();

        assertThat(contactDao.update(newContact))
                .isTrue();
    }

    @Test
    public void delete() {
        final Contact contact = contactDao.insert(someContact());
        contactDao.delete(contact.getId());

        assertThat(contactDao.findAll())
                .hasSize(0);
    }

    private static Contact someContact() {
        return Contact.builder()
                .firstName("1")
                .lastName("2")
                .birthDate(Date.valueOf("2017-03-03"))
                .build();
    }
}
