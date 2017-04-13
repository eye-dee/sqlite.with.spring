package skillself.spring.sqlite.dao;

import skillself.spring.sqlite.object.Contact;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.stream.IntStream;

/**
 * Polytech
 * Created by igor on 04.04.17.
 */
public class ContactDaoSimpleImpl implements ContactDao {
    private static final String URL_TO_DATABASE = "jdbc:sqlite:/home/igor/projects/SQLite/SkillSelf/skillself.db";
    
    private final Logger logger = Logger.getLogger(ContactDaoSimpleImpl.class.getName());
    {
        try {
            Arrays.stream(logger.getHandlers()).forEach(logger::removeHandler);
            logger.setUseParentHandlers(false);
            logger.addHandler(new FileHandler(logger.getName()));

        } catch (final IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (final ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL_TO_DATABASE);
    }
    
    @Override
    public List<Contact> findAll() {
        final List<Contact> contacts = new ArrayList<>();
        try (Connection connection = getConnection()) {
            final PreparedStatement statement = connection.prepareStatement("select * from contact");
            final ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                final Contact contact = Contact.builder()
                        .id(resultSet.getLong("id"))
                        .firstName(resultSet.getString("firstName"))
                        .lastName(resultSet.getString("lastName"))
                        .birthDate(resultSet.getDate("birthDate"))
                        .build();
                contacts.add(contact);
            }
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    @Override
    public List<Contact> findAllWithDetails() {
        return null;
    }

    @Override
    public List<Contact> findByFirstName(final String firstName) {
        final List<Contact> contacts = new ArrayList<>();
        try (Connection connection = getConnection()) {
            final ResultSet resultSet = executeQuery(connection, "select * from contact where firstName = ?", firstName);

            addAll(contacts, resultSet);
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
        return contacts;
    }

    @Override
    public String findLastNameById(final Long id) {
        try (Connection connection = getConnection()) {
            final ResultSet resultSet = executeQuery(
                    connection,
                    "select * from contact where id = ?",
                    String.valueOf(id));

            resultSet.next();
            return resultSet.getString("lastName");
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Override
    public String findFirstNameByid(final Long id) {
        try (Connection connection = getConnection()) {
            final ResultSet resultSet = executeQuery(connection, "select * from contact where id = ?", String.valueOf(id));

            resultSet.next();
            return resultSet.getString("firstName");
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    @Override
    public long insert(final Contact contact) {
        try (Connection connection = getConnection()) {
            final PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into Contact (firstName, lastName, birthDate) values (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1,contact.getFirstName());
            preparedStatement.setString(2,contact.getLastName());
            preparedStatement.setDate(3,contact.getBirthDate());

            final int rowAffected = preparedStatement.executeUpdate();
            final ResultSet resultSet = preparedStatement.getGeneratedKeys();

            long insertedId = -1;
            while(resultSet.next()) {
                insertedId = resultSet.getLong(1);
            }

            if (rowAffected > 0) {
                logger.info("New contact inserted");
            } else {
                logger.warning("New contact not inserted");
            }

            return insertedId;
        } catch (final SQLException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    @Override
    public void update(final Contact contact) {

    }

    @Override
    public void delete(final Long contactId) {
        try (Connection connection = getConnection()) {
           final PreparedStatement preparedStatement = connection.prepareStatement("delete from Contact where id = ?");
           preparedStatement.setString(1, String.valueOf(contactId));
           preparedStatement.execute();
           logger.info(String.format("Contact with id %s was deleted",contactId));
        } catch (final SQLException ex) {
            logger.warning(String.format("Contact with id %s wasn't deleted",contactId));
            System.out.println(ex.getMessage());
        }
    }

    private void addAll(final List<Contact> contacts, final ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            final Contact contact = Contact.builder()
                    .id(resultSet.getLong("id"))
                    .firstName(resultSet.getString("firstName"))
                    .lastName(resultSet.getString("lastName"))
                    .birthDate(resultSet.getDate("birthDate"))
                    .build();
            contacts.add(contact);
        }
    }

    private ResultSet executeQuery(final Connection connection, final String query, final String... args) throws SQLException {
        final PreparedStatement statement = connection.prepareStatement(query);
        IntStream.range(1, args.length + 1).forEach(i -> {
            try {
                statement.setString(i, args[i - 1]);
            } catch (final SQLException e) {
                System.out.println(e.getMessage());
            }
        });
        return statement.executeQuery();
    }
}
