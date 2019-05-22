package ru.job4j.io.assembly;

import java.io.IOException;
import java.net.Socket;

/**
 * Assembly.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/15/2019
 */
public interface Assembly {
    /**
     * Method set input reader.
     *
     * @param socket socket
     * @throws IOException exception
     */
    void setInputReader(final Socket socket) throws IOException;

    /**
     * Method set output writer.
     *
     * @param socket socket
     * @throws IOException exception
     */
    void setOutWriter(final Socket socket) throws IOException;

    /**
     * Method get line to reader.
     *
     * @return get line
     *
     * @throws IOException exception
     */
    String inputLine() throws IOException;

    /**
     * Method get out line from writer.
     *
     * @param key key lint
     * @throws IOException ioException
     */
    void outLine(final String key) throws IOException;

}
