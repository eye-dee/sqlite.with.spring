package skillself.spring.sqlite.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import skillself.spring.sqlite.object.Contact;

import java.sql.PreparedStatement;
import java.util.List;

import static org.springframework.dao.support.DataAccessUtils.singleResult;

/**
 * sqlite.with.spring
 * Created by igor on 18.04.17.
 */

@AllArgsConstructor
public class ContactDaoImpl implements ContactDao {
    private static final RowMapper<Contact> CONTACT_ROW_MAPPER =
            (rs, rowNum) ->
                    Contact.builder()
                            .id(rs.getLong("id"))
                            .firstName(rs.getString("firstName"))
                            .lastName(rs.getString("lastName"))
                            .birthDate(rs.getDate("birthDate"))
                            .build();

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM Contact",
                CONTACT_ROW_MAPPER
        );
    }

    @Override
    public List<Contact> findByFirstName(final String firstName) {
        return jdbcTemplate.query(
                "SELECT * FROM Contact WHERE firstName = ?",
                new Object[]{firstName},
                CONTACT_ROW_MAPPER
        );
    }

    @Override
    public String findLastNameById(final Long id) {
        final List<Contact> contacts = jdbcTemplate.query(
                "Select * from Contact WHERE id = ?",
                new Object[]{id},
                CONTACT_ROW_MAPPER
        );

        if (contacts.size() == 0) {
            throw new IllegalArgumentException(String.format("Id %s is not exists",id));
        }

        return singleResult(contacts).getLastName();
    }

    @Override
    public String findFirstNameByid(final Long id) {
        final List<Contact> contacts = jdbcTemplate.query(
                "Select * from Contact WHERE id = ?",
                new Object[]{id},
                CONTACT_ROW_MAPPER
        );

        if (contacts.size() == 0) {
            throw new IllegalArgumentException(String.format("Id %s is not exists",id));
        }

        return singleResult(contacts).getFirstName();
    }

    @Override
    public Contact insert(final Contact contact) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
                    final PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Contact(firstName, lastName, birthDate) VALUES (?,?,?)");
                    preparedStatement.setString(1, contact.getFirstName());
                    preparedStatement.setString(2, contact.getLastName());
                    preparedStatement.setDate(3, contact.getBirthDate());
                    return preparedStatement;
                },
                keyHolder);

        /*jdbcTemplate.update(
                "INSERT INTO Contact(firstName, lastName, birthDate) VALUES (?,?,?)",
                contact.getFirstName(), contact.getLastName(), contact.getBirthDate());
*/
        return Contact.builder()
                .id(keyHolder.getKey().longValue())
                .firstName(contact.getFirstName())
                .lastName(contact.getLastName())
                .birthDate(contact.getBirthDate())
                .build();
    }

    @Override
    public boolean update(final Contact contact) {
        return 1 == jdbcTemplate.update(
                "UPDATE Contact SET firstName = ?, lastName = ?, birthDate = ? " +
                        "WHERE id = ?",
                contact.getFirstName(),contact.getLastName(),contact.getBirthDate(),contact.getId());
    }

    @Override
    public boolean delete(final Long contactId) {
        return 1 == jdbcTemplate.update(
                "DELETE from Contact WHERE id = ?",
                contactId);
    }

    @Override
    public List<Contact> findAllWithDetails() {
        return null;
    }
}