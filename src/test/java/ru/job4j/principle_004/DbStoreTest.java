package ru.job4j.principle_004;

import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import static org.hamcrest.core.IsNot.not;

/**
 * Integration tests.
 */
public class DbStoreTest {

    /**
     * Build db course.
     * @param con consumer.
     */
    private void source(ConEx<BasicDataSource> con) {
        BasicDataSource source = new PoolRollback();
        try {
            Properties settings = new Properties();
            try (InputStream in = new FileInputStream(new File("./gradle.properties"))) {
                settings.load(in);
            }
            source.setDriverClassName(settings.getProperty("databaseQaDriver"));
            source.setUrl(settings.getProperty("databaseQaUrl"));
            source.setUsername(settings.getProperty("databaseQaUsername"));
            source.setPassword(settings.getProperty("databaseQaPassword"));
            source.setMinIdle(5);
            source.setMaxIdle(10);
            source.setMaxOpenPreparedStatements(100);
            con.accept(source);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                source.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Check that table is empty.
     */
    @Test
    public void whenReadsThenTableEmpty() {
        this.source(
                source -> {
                    DbStore store = new DbStore(source);
                    assertThat(
                            store.findAll().isEmpty(),
                            is(true)
                    );
                }
        );
    }

    /**
     * Test. Adding user.
     */
    @Test
    public void whenAddUserThenGenerateId() {
        this.source(
                source -> {
                    DbStore store = new DbStore(source);
                    assertThat(
                            store.add(new User(-1, "parsentev")),
                            not(-1)
                    );
                }
        );
    }
}