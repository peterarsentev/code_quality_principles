package ru.job4j.io.connect;

import ru.job4j.io.assembly.Assembling;
import ru.job4j.io.assembly.Assembly;
import ru.job4j.io.source.StorageServer;

import java.io.IOException;
import java.net.Socket;

/**
 * MenuServerClient.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/15/2019
 */
public class SocketMenu {
    /**
     * field assembly get class which set/get out and input  streams to socket.
     */
    private final Assembly assembly = new Assembling();
    /**
     * field storage answers server.
     */
    private final StorageServer storage = new StorageServer();

    /**
     * Method get answer from storage server.
     *
     * @param ask ask client
     * @return answer from storage server
     */
    public final String getAnswerServer(final String ask) {
        return this.storage.getDataStorage(ask);
    }

    /**
     * Method fill storage server.
     */
    public final void fillStorage() {
        this.storage.setDataStorage("Hello Oracle",
                "Hello, dear friend, I'm a Oracle.");
    }

    /**
     * Method set output stream.
     *
     * @param socket this.socket
     * @throws IOException exception
     */
    public final void setOut(final Socket socket) throws IOException {
        this.assembly.setOutWriter(socket);
    }

    /**
     * Method set input stream.
     *
     * @param socket this.socket
     * @throws IOException exception
     */
    public final void setIn(final Socket socket) throws IOException {
        this.assembly.setInputReader(socket);
    }

    /**
     * Method print line from writer stream.
     *
     * @param out out line
     * @throws IOException ioException
     */
    public final void out(final String out) throws IOException {
        this.assembly.outLine(out + System.lineSeparator());
    }

    /**
     * Method get line for reader stream.
     *
     * @return get line
     *
     * @throws IOException exception
     */

    public final String in() throws IOException {
        return this.assembly.inputLine();
    }

    /**
     * Method print line in standard stream.
     *
     * @param print line for print
     */
    public final void print(final String print) {
        System.out.println(print);
    }
}
