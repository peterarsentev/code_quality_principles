package ru.job4j.principle_003;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.function.BiConsumer;
/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. UserServlet.
 * @since 13.02.2019.
 */
public class UserServlet extends HttpServlet {
    private Validate store = ValidateService.INSTANCE;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        this.showList(this.store.findAll(), req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String action = req.getParameter("action");
        switch (action) {
            case "add":
                this.store.add(new User(req.getParameter("name"), req.getParameter("login"),
                        req.getParameter("email"), req.getParameter("create")));
                this.doGet(req, resp);
                break;
            case "delete":
                this.store.delete(Integer.valueOf(req.getParameter("id")));
                this.doGet(req, resp);
                break;
            case "update":
                this.store.update(Integer.valueOf(req.getParameter("id")), new User(req.getParameter("name"),
                        req.getParameter("login"), req.getParameter("email"), req.getParameter("create")));
                this.doGet(req, resp);
                break;
        }
    }

    /**
     * Shows Users in list.
     * @param list list of users.
     * @param req request param.
     * @param resp response param.
     */
    private void showList(Map<Integer, User> list, HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter writer = new PrintWriter(resp.getOutputStream())) {
            writer.append("List: \n");
            list.forEach((integer, user) -> {
                writer.append("User id= ").append(String.valueOf(integer)).append("\n");
                writer.append("Name= ").append(user.getName()).append("\n");
                writer.append("Login= ").append(user.getLogin()).append("\n");
                writer.append("Email= ").append(user.getEmail()).append("\n");
                writer.append("Create Date= ").append(user.getCreated()).append("\n");
                writer.append("\n");
            });
            writer.flush();
        } catch (IOException e) {
            LOGGER.error("Input/Output Error!", e);
        }
    }
}
