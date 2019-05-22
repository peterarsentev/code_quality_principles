package ru.job4j.io.connect;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * SocketServer.
 *
 * @author Maxim Vanny
 * @version 5.0
 * @since 4/13/2019
 */
public class SocketServer {
    /**
     * field socket.
     */
    private final Socket socket;

    /**
     * Constructor.
     *
     * @param aSocket socket
     */
    public SocketServer(final Socket aSocket) {
        this.socket = aSocket;
    }

    /**
     * Method startServer.
     *
     * @throws IOException exception
     */
    public final void startServer() throws IOException {
        SocketMenu menu = new SocketMenu();
        menu.fillStorage();
        menu.setOut(this.socket);
        menu.setIn(this.socket);
        String client;
        String data;
        do {
            menu.print("wait command...");
            client = menu.in();
            menu.print("Shadow: " + client);
            if (!("exit".equals(client))) {
                data = menu.getAnswerServer(client);
                menu.out(data);
                menu.print("Oracle: " + data);
            }
        } while (!"exit".equals(client));
        menu.print("");
        this.socket.close();
    }

    /**
     * Method point to program.
     *
     * @param args args
     * @throws IOException exception
     */
    public static void main(final String[] args) throws IOException {
        final var port = 2000;
        final Socket socket = new ServerSocket(port).accept();
        new SocketServer(socket).startServer();

    }
}
