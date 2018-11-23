package ru.job4j.principle_004;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.SQLException;

public class DbRunner {
    public static void main(String[] args) throws SQLException {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("org.postgresql.Driver");
        source.setUrl("jdbc:postgresql://127.0.0.1:5432/sqlite");
        source.setUsername("postgres");
        source.setPassword("password");
        source.setMinIdle(5);
        source.setMaxIdle(10);
        source.setMaxOpenPreparedStatements(100);
        DbStore store = new DbStore(source);
        store.add(new User(1, "parsentev"));
        store.findAll().forEach(System.out::println);
        source.close();
    }
}
