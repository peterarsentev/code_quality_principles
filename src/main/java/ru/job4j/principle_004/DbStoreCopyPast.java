package ru.job4j.principle_004;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Storage with copy past anti pattern.
 */
public class DbStoreCopyPast implements Store<User> {
    /**
     * db source.
     */
    private final BasicDataSource source;

    /**
     * Constructor.
     * @param source db source.
     */
    public DbStoreCopyPast(BasicDataSource source) {
        this.source = source;
    }

    @Override
    public User add(User user) {
        try (Connection connection = this.source.getConnection();
             final PreparedStatement statement = connection
                .prepareStatement("insert into users (login) values (?)",
                        Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getLogin());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException("Could not create new user");
    }

    @Override
    public List<User> findAll() {
        final List<User> users = new ArrayList<>();
        try (final PreparedStatement statement = this.source.getConnection()
                .prepareStatement("select * from users");
             final ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                users.add(new User(rs.getInt("id"), rs.getString("login")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User user) {
        try (final PreparedStatement ps = this.source.getConnection()
                .prepareStatement("update users set login=? where id=?")) {
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getLogin());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
    }

    @Override
    public User findById(int id) {
        User rsl = new User();
        try (final PreparedStatement ps = this.source.getConnection()
                .prepareStatement("select * from users where id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    rsl = new User(rs.getInt("id"), rs.getString("login"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rsl;
    }
}
