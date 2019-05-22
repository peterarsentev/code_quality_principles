package ru.job4j.io.assembly;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

/**
 * Assembling.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/15/2019
 */
public class Assembling implements Assembly {
    /**
     * field reader.
     */
    private DataInputStream reader;
    /**
     * field writer.
     */
    private DataOutputStream writer;


    @Override
    public final void setOutWriter(final Socket socket) throws IOException {
        Objects.requireNonNull(socket, "must not be null");
        this.writer = new DataOutputStream(socket.getOutputStream());
    }


    @Override
    public final void setInputReader(final Socket socket) throws IOException {
        Objects.requireNonNull(socket, "must not be null");
        this.reader = new DataInputStream(socket.getInputStream());
    }

    @Override
    public final String inputLine() throws IOException {
        final String ln = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        while (this.reader.available() > 0) {
            if (sb.toString().endsWith(ln)) {
                sb.replace(sb.length() - 2, sb.length(), "");
                break;
            }
            final var read = this.reader.read();
            if (read != -1) {
                sb.append((char) read);
            } else {
                break;
            }
        }
        return sb.toString().trim();

    }

    @Override
    public final void outLine(final String key) throws IOException {
        this.writer.writeUTF(key);
    }
}
