package ru.job4j.io;


import ru.job4j.io.connect.SocketServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SocketServerTest {
    public static final String LN = System.getProperty("line.separator");
    Socket socket = mock(Socket.class);
    ByteArrayOutputStream bos = new ByteArrayOutputStream();

    @Before
    public void before() {
        System.setOut(new PrintStream(bos));
    }

    @After
    public void close() throws IOException {
        bos.close();
        System.setOut(System.out);
        socket.close();
    }

    public void testServer(final String request, final String expected) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(request.getBytes(StandardCharsets.UTF_8));
        when(socket.getOutputStream()).thenReturn(out);
        when(socket.getInputStream()).thenReturn(in);
        SocketServer server = new SocketServer(socket);
        server.startServer();
        final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        StringBuilder sb = new StringBuilder();
        while (dis.available() > 0) {
            sb.append(dis.readUTF());
        }
        assertThat(sb.toString(), is(expected));
        dis.close();
        in.close();
        out.close();

    }

    @Test
    public void whenClientAnswerThenChooseRandom() throws IOException {
        this.testServer(String.join(LN,"exit", ""), "");
    }

    @Test
    public void whenClientHelloThenBackGreatOracle() throws IOException {
        this.testServer(
                String.join(LN,
                        "Hello Oracle",
                        "exit"
                ),
                String.format("Hello, dear friend, I'm a Oracle.%s", LN)
        );
    }

    @Test
    public void whenClientAnyThenBackDontUnderstand() throws IOException {
        this.testServer(
                String.join(LN,"be or not to be?", "exit"),
                String.join(LN,"I don't understand.", ""));
    }
}
