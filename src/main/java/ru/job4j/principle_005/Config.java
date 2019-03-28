package ru.job4j.principle_005;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class works with file config (key=value format).
 * Example.
 * ##email config
 * email=parsentev@yandex.ru
 * name=Petr Arsentev
 * jdbc.url=...
 */
public class Config {
    /**
     * Separator of key=value.
     */
    private static final String SP = "=";

    /**
     * File with key=value.
     */
    private final String path;

    /**
     * Contains pair of key/value.
     */
    private final Map<String, String> settings = new LinkedHashMap<>();

    /**
     * Contructor.
     * @param path file properties.
     */
    public Config(final String path) {
        this.path = path;
    }

    /**
     * Load properties.
     * @return instance of config.
     */
    public Config load() {
        this.settings.clear();
        try (BufferedReader file = new BufferedReader(new FileReader(this.path))) {
            file.lines().forEach(
                    line -> {
                        if (line.contains(SP)) {
                            int pos = line.indexOf(SP);
                            settings.put(line.substring(0, pos), line.substring(pos + 1));
                        } else {
                            settings.put(line, "");
                        }
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * Get value by key.
     * @param key key.
     * @return value.
     */
    public String value(String key) {
        return this.settings.get(key);
    }

    /**
     * Add or update the key and value.
     * @param key key.
     * @param value value.
     */
    public void put(String key, String value) {
        this.settings.put(key, value);
    }

    /**
     * Delete pair by key.
     * @param key key.
     */
    public void delete(String key) {
        this.settings.remove(key);
    }

    /**
     * Save of properties to file.
     */
    public void save() {
        try (final PrintWriter file = new PrintWriter(this.path)) {
            this.settings.forEach(
                    (key, value) -> {
                        file.append(key);
                        if (!value.isEmpty()) {
                            file.append(SP).append(value);
                        }
                        file.println();
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

