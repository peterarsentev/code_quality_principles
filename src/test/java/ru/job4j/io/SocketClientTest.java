package ru.job4j.io;

import ru.job4j.io.connect.SocketClient;
import ru.job4j.io.input.Input;
import ru.job4j.io.input.StubInput;
import ru.job4j.io.input.ValidateInput;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class SocketClientTest {
    public static final String LN = System.getProperty("line.separator");
    final Socket socket = mock(Socket.class);
    ByteArrayOutputStream os = new ByteArrayOutputStream();

    @Before
    public void before() {
        System.setOut(new PrintStream(os));
    }

    @After
    public void after() throws IOException {
        os.close();
        socket.close();
        System.setOut(System.out);
    }

    public final void setTestClient(final String request, final String result) throws IOException {
        final Input input = new ValidateInput(new StubInput(request.split(LN)));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF(request);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
        when(socket.getInputStream()).thenReturn(in);
        when(socket.getOutputStream()).thenReturn(out);
        SocketClient client = new SocketClient(socket, input);
        client.startClient();
        final DataInputStream dis = new DataInputStream(new ByteArrayInputStream(out.toByteArray()));
        StringBuilder sb = new StringBuilder();
        while (dis.available() > 0) {
            sb.append(dis.readUTF());
        }
        assertThat(sb.toString(), is(result));
        dis.close();
        in.close();
        out.close();
    }

    @Test
    public void whenClientOutExit() throws IOException {
        final String query = "exit";
        final String result = String.join(LN,"exit", "");
        setTestClient(query, result);
    }


    @Test
    public void whenClientExit() throws IOException {
        final String query = String.join(LN, "Hello Oracle", "exit");
        final String result = String.join(LN,"Hello Oracle", "exit", "");
        setTestClient(query, result);

    }


    @Test
    public void whenClientHelloThenBackGreatOracle() throws IOException {
        final String query = String.join(LN,"to be or not to be?", "exit");
        final String result = String.join(LN,"to be or not to be?", "exit", "");
        setTestClient(query, result);
    }
}
