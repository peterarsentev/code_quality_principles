package ru.job4j.principle_003;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author Vladimir Yamnikov (Androedge@gmail.com).
 * @version $1.0$. UserServlet.
 * @since 13.02.2019.
 */
public class UserServlet extends HttpServlet {
    /**
     * Check data.
     */
    private final Validate store = ValidateService.INSTANCE;
    /**
     * Route the actions.
     */
    private final Map<String, Consumer<HttpServletRequest>> acts = new HashMap<>();

    /**
     * Constructor.
     */
    public UserServlet() {
        acts.put("add", this::add);
        acts.put("delete", this::delete);
        acts.put("update", this::update);
    }

    /**
     * Update user.
     * @param req request.
     */
    private void update(HttpServletRequest req) {
        this.store.update(
                Integer.valueOf(req.getParameter("id")),
                new User(
                        req.getParameter("name"), req.getParameter("login"),
                        req.getParameter("email"), req.getParameter("create")
                )
        );
    }

    /**
     * Delete user.
     * @param req request.
     */
    private void delete(HttpServletRequest req) {
        this.store.delete(Integer.valueOf(req.getParameter("id")));
    }

    /**
     * Add user.
     * @param req request.
     */
    private void add(HttpServletRequest req) {
        this.store.add(
                new User(
                        req.getParameter("name"), req.getParameter("login"),
                        req.getParameter("email"), req.getParameter("create")
                )
        );
    }


    /**
     * Draw the table with user.
     * @param req request.
     * @param resp response.
     * @throws ServletException possible.
     * @throws IOException possible.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        this.showList(this.store.findAll(), req, resp);
    }

    /**
     * Handle actions.
     * @param req request.
     * @param resp response.
     * @throws ServletException possible.
     * @throws IOException possible.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String action = req.getParameter("action");
        acts.getOrDefault(
                action,
                request -> {
                    throw new UnsupportedOperationException(String.format("Action %s not found", action));
                }
        ).accept(req);
        doGet(req, resp);
    }

    /**
     * Shows Users in list.
     * @param list list of users.
     * @param req request param.
     * @param resp response param.
     * @throws IOException possible.
     */
    private void showList(Map<Integer, User> list, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
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
    }
}
