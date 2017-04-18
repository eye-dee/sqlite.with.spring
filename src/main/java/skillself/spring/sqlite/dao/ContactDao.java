package skillself.spring.sqlite.dao;

import skillself.spring.sqlite.object.Contact;

import java.util.List;
import java.util.Optional;

/**
 * Polytech
 * Created by igor on 04.04.17.
 */

public interface ContactDao {
    List<Contact> findAll();
    List<Contact> findByFirstName(String firstName);
    String findLastNameById(Long id);
    String findFirstNameByid(Long id);
    Contact insert(Contact contact);
    Optional<Contact> update(Contact contact);
    void delete(Long contactId);

    List<Contact> findAllWithDetails();
}
