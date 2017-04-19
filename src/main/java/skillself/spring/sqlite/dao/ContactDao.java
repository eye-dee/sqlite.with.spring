package skillself.spring.sqlite.dao;

import skillself.spring.sqlite.object.Contact;

import java.util.List;

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
    boolean update(Contact contact);
    boolean delete(Long contactId);

    List<Contact> findAllWithDetails();
}
