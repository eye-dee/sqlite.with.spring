package skillself.spring.sqlite.dao;

import skillself.spring.sqlite.object.Contact;

import java.util.List;
import java.util.Optional;

/**
 * sqlite.with.spring
 * Created by igor on 18.04.17.
 */
public class ContactDaoImpl implements ContactDao {
    @Override
    public List<Contact> findAll() {
        return null;
    }

    @Override
    public List<Contact> findByFirstName(String firstName) {
        return null;
    }

    @Override
    public String findLastNameById(Long id) {
        return null;
    }

    @Override
    public String findFirstNameByid(Long id) {
        return null;
    }

    @Override
    public Contact insert(Contact contact) {
        return null;
    }

    @Override
    public Optional<Contact> update(Contact contact) {
        return Optional.empty();
    }

    @Override
    public void delete(Long contactId) {

    }

    @Override
    public List<Contact> findAllWithDetails() {
        return null;
    }
}
