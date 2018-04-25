package ru.kugach.artem.otus.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginServlet extends HttpServlet {

    public static final String LOGIN_PARAMETER_NAME = "login";
    private static final String PASSWORD_PARAMETER_NAME = "password";
    private static final String LOGIN_VARIABLE_NAME = "login";
    private static final String ERR_MSG_VARIABLE_NAME = "err_msg";
    private static final String LOGIN_PAGE_TEMPLATE = "login.html";
    private static final String ERROR_LOGIN_PAGE_TEMPLATE = "error_login.html";

    private String login;


    public LoginServlet() {
        this.login = null;
    }

    private String getPage() throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(LOGIN_VARIABLE_NAME, login == null ? "" : login);
        return TemplateProcessor.instance().getPage(LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    private String getErrorPage() throws IOException {
        Map<String, Object> pageVariables = new HashMap<>();
        pageVariables.put(ERR_MSG_VARIABLE_NAME, "The username or password you entered is incorrect");
        return TemplateProcessor.instance().getPage(ERROR_LOGIN_PAGE_TEMPLATE, pageVariables);
    }

    public void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws IOException {
        String page = getPage();
        response.getWriter().println(page);
        setOK(response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        String requestLogin = request.getParameter(LOGIN_PARAMETER_NAME);
        String password = request.getParameter(PASSWORD_PARAMETER_NAME);

        if (AuthHelper.validate(requestLogin, password)) {
            saveToVariable(requestLogin);
            saveToSession(request, requestLogin);
            response.sendRedirect("/admin");
        } else {
            saveToVariable(null);
            saveToSession(request, null);
            String page = getErrorPage();
            response.getWriter().println(page);
            setOK(response);
        }

    }

    private void saveToSession(HttpServletRequest request, String requestLogin) {
        request.getSession().setAttribute("login", requestLogin);
    }

    private void saveToVariable(String requestLogin) {
        login = requestLogin != null ? requestLogin : login;
    }

    private void setOK(HttpServletResponse response) {
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
