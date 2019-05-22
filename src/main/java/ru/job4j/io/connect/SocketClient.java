package ru.job4j.io.connect;

import ru.job4j.io.input.ConsoleInput;
import ru.job4j.io.input.Input;
import ru.job4j.io.input.ValidateInput;

import java.io.IOException;
import java.net.Socket;

/**
 * SocketClient.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/13/2019
 */
public class SocketClient {
    /**
     * field input.
     */
    private final Input input;
    /**
     * field socket.
     */
    private final Socket socket;

    /**
     * Constructor.
     *
     * @param aSocket socket
     * @param aInput  input
     */
    public SocketClient(final Socket aSocket, final Input aInput) {
        socket = aSocket;
        input = aInput;
    }

    /**
     * Method start Client.
     *
     * @throws IOException exception
     */
    public final void startClient() throws IOException {
        SocketMenu menu = new SocketMenu();
        menu.setIn(this.socket);
        menu.setOut(this.socket);
        String client;
        String answer;
        do {
            client = this.input.request();
            if (!"exit".equals(client)) {
                menu.out(client);
                answer = menu.in();
                menu.print("Oracle: " + answer);
            } else {
                menu.out(client);
            }
        } while (!"exit".equals(client));
        menu.print("");
    }

    /**
     * Method point to program.
     *
     * @param args args
     * @throws IOException exception
     */
    public static void main(final String[] args) throws IOException {
        final var port = 2000;
        try (final Socket socket = new Socket("127.0.0.1", port)) {
            new SocketClient(socket, new ValidateInput(
                    new ConsoleInput())).startClient();
        }
    }
}
