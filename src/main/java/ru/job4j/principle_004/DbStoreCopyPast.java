package ru.job4j.principle_004;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbStoreCopyPast implements Store<User> {
    private final BasicDataSource source;

    public DbStoreCopyPast(BasicDataSource source) {
        this.source = source;
    }

    @Override
    public User add(User user) {
        try (final PreparedStatement statement = this.source.getConnection()
                .prepareStatement("insert into client (name) values (?)", Statement.RETURN_GENERATED_KEYS)) {
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
                users.add(new User(rs.getInt("uid"), rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void update(User model) {

    }

    @Override
    public void delete(int id) {
    }

    @Override
    public User findById(int id) {
        return null;
    }
}
