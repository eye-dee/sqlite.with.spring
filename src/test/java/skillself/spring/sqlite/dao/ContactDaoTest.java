package skillself.spring.sqlite.dao;

import org.junit.Before;
import org.junit.Test;
import skillself.spring.sqlite.object.Contact;

import java.sql.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * sqlite.with.spring
 * Created by igor on 18.04.17.
 */
public class ContactDaoTest {
    private ContactDao contactDao;

    @Before
    public void setUp() {
        contactDao = new ContactDaoImpl();
    }

    @Test
    public void findAllEmpty() {
        assertThat(contactDao.findAll())
                .hasSize(0)
                .isNotNull();
    }

    @Test
    public void findAllNotEmpty() {
        contactDao.insert(someContact());

        assertThat(contactDao.findAll())
                .isNotNull()
                .hasSize(1)
                .contains(someContact());
    }

    @Test
    public void findByFirstNameNotExists() {
        assertThat(contactDao.findByFirstName("someName"))
                .isNotNull()
                .hasSize(0);
    }

    @Test
    public void findByFirstNameExists() {
        Contact someContact = someContact();
        contactDao.insert(someContact);

        assertThat(contactDao.findByFirstName(someContact.getFirstName()))
                .isNotNull()
                .hasSize(1)
                .contains(someContact);

    }

    @Test
    public void insert() {
        Contact contact = someContact();
        assertThat(contactDao.insert(contact))
                .isNotNull()
                .isEqualTo(contact);
    }

    @Test
    public void findLastNameByIdNotExists() {
        assertThatThrownBy(() -> contactDao.findLastNameById(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id is not exists");
    }

    @Test
    public void findLastNameByIdExists() {
        Contact contact = someContact();
        contactDao.insert(contact);

        assertThat(contactDao.findLastNameById(contact.getId()))
                .isEqualTo(contact);
    }

    @Test
    public void findFirstNameByIdNotExists() {
        assertThatThrownBy(() -> contactDao.findFirstNameByid(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Id is not exists");
    }

    @Test
    public void findFirstNameByIdExists() {
        Contact contact = someContact();
        contactDao.insert(contact);

        assertThat(contactDao.findFirstNameByid(contact.getId()))
                .isEqualTo(contact);
    }

    @Test
    public void updateNotExists() {
        assertThat(contactDao.update(someContact()))
                .isNotNull()
                .isInstanceOf(Optional.class);
    }

    @Test
    public void updateExists() {
        Contact contact = someContact();
        Contact newContact = Contact
                .builder()
                .id(contact.getId())
                .birthDate(contact.getBirthDate())
                .lastName(contact.getLastName())
                .firstName("newFirstName")
                .build();

        contactDao.insert(contact);
        assertThat(contactDao.update(newContact))
                .matches(optional -> optional.get().equals(newContact));
    }

    @Test
    public void delete() {
        Contact contact = someContact();
        contactDao.insert(contact);
        contactDao.delete(contact.getId());

        assertThat(contactDao.findAll())
                .hasSize(0);
    }

    private Contact someContact() {
        return Contact.builder()
                .firstName("1")
                .lastName("2")
                .birthDate(Date.valueOf("2017-03-03"))
                .build();
    }
}
