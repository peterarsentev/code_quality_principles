package ru.job4j.principle_003;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User servlet test.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(ValidateService.class)
public class UserServletTest {

    /**
     * Check exception.
     * @throws ServletException possible.
     * @throws IOException possible.
     */
    @Test(expected =  UnsupportedOperationException.class)
    public void whenActionNotDefined() throws ServletException, IOException {
        Whitebox.setInternalState(ValidateService.class, "INSTANCE", mock(ValidateService.class));
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        new UserServlet().doPost(req, resp);
    }

    /**
     * Check adding a user.
     * @throws ServletException possible.
     * @throws IOException possible.
     */
    @Test
    public void whenAddUserThenStoreIt() throws ServletException, IOException {
        Validate validate = mock(ValidateService.class);
        Whitebox.setInternalState(ValidateService.class, "INSTANCE", validate);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        when(req.getParameter("action")).thenReturn("add");
        when(req.getParameter("name")).thenReturn("Petr Arsentev");
        when(resp.getOutputStream()).thenReturn(mock(ServletOutputStream.class));
        new UserServlet().doPost(req, resp);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
        verify(validate).add(argument.capture());
        assertThat(argument.getValue().getName(), is("Petr Arsentev"));
    }
}