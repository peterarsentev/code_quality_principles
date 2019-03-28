package ru.job4j.principle_005;

import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Contains tests of config class.
 */
public class ConfigTest {

    /**
     * Check remove operations.
     * @throws IOException possible.
     */
    @Test
    public void whenRemoveKeyConfig() throws IOException {
        File path = data(
                String.valueOf(System.currentTimeMillis()),
                "email=parsentev@yandex.ru"
        );
        Config config = new Config(path.getAbsolutePath()).load();
        config.delete("email");
        config.save();
        config.load();
        assertThat(config.value("email"), is(nullValue()));
        path.deleteOnExit();
    }

    /**
     * Check read operations.
     * @throws IOException possible.
     */
    @Test
    public void whenReadConfig() throws IOException {
        File path = data(
                String.valueOf(System.currentTimeMillis()),
                "email=parsentev@yandex.ru"
        );
        Config config = new Config(path.getAbsolutePath()).load();
        assertThat(config.value("email"), is("parsentev@yandex.ru"));
        path.deleteOnExit();
    }

    /**
     * Check write operations.
     * @throws IOException possible.
     */
    @Test
    public void whenWriteConfig() throws IOException {
        File path = data(
                String.valueOf(System.currentTimeMillis()),
                "email=parsentev@yandex.ru"
        );
        Config config = new Config(path.getAbsolutePath()).load();
        config.put("email", "123@google.ru");
        config.save();
        config.load();
        assertThat(config.value("email"), is("123@google.ru"));
        path.deleteOnExit();
    }

    /**
     * When config has comments.
     * @throws IOException possible.
     */
    @Test
    public void whenConfigHasComment() throws IOException {
        File path = data(
                String.valueOf(System.currentTimeMillis()),
                "### email for git",
                "email=parsentev@yandex.ru"
        );
        Config config = new Config(path.getAbsolutePath()).load();
        config.put("email", "123@google.ru");
        config.save();
        StringBuilder content = new StringBuilder();
        try (BufferedReader file = new BufferedReader(new FileReader(path))) {
            file.lines().forEach(
                    line -> content.append(line).append(System.lineSeparator())
            );
        }
        assertThat(
                content.toString(),
                is(
                        String.join(System.lineSeparator(),
                                "### email for git",
                                "email=123@google.ru", ""
                        )
                )
        );
        path.deleteOnExit();
    }

    /**
     * Fill input data.
     * @param file properties file.
     * @param properties data, key=value.
     * @return file.
     * @throws IOException possible.
     */
    private File data(String file, String... properties) throws IOException {
        File path = new File(
                System.getProperty("java.io.tmpdir")
                        + System.getProperty("file.separator")
                        + file
        );
        if (!path.createNewFile()) {
            throw new IllegalStateException(String.format("File could not created %s", path.getAbsoluteFile()));
        }
        try (final PrintWriter store = new PrintWriter(path)) {
            Stream.of(properties).forEach(store::println);
        }
        return path;
    }
}