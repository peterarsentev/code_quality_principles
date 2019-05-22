package ru.job4j.io.source;

import java.util.HashMap;
import java.util.Map;

/**
 * StorageServer.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/15/2019
 */
public class StorageServer {
    /**
     * field storage server with key and answer data.
     */
    private final Map<String, String> storage = new HashMap<>();

    /**
     * Method get answer from storage server by ask.
     *
     * @param ask ask client
     * @return answer from storage server
     */
    public final String getDataStorage(final String ask) {
        return storage.getOrDefault(ask, "I don't understand.");
    }

    /**
     * Method set data to storage server.
     *
     * @param ask    ask client
     * @param answer answer server
     */
    public final void setDataStorage(final String ask, final String answer) {
        storage.put(ask, answer);
    }
}
