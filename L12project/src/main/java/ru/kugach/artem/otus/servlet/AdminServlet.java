package ru.kugach.artem.otus.servlet;

import ru.kugach.artem.otus.base.DBService;
import ru.kugach.artem.otus.cache.CacheEngine;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AdminServlet extends HttpServlet {

    private static final String DEFAULT_USER_NAME = "UNKNOWN";
    private static final String ADMIN_PAGE_TEMPLATE = "admin.html";
    private DBService dbService;

    public AdminServlet(DBService dbService){
        this.dbService = dbService;
    }

    private Map<String, Object> createPageVariablesMap(HttpServletRequest request) {
        Map<String, Object> pageVariables = new HashMap<>();
        CacheEngine cache = dbService.getCache();
        pageVariables.put("sessionId", request.getSession().getId());
        pageVariables.put("hitCount", cache.getHitCount());
        pageVariables.put("missCount", cache.getMissCount());
        String login = (String) request.getSession().getAttribute(LoginServlet.LOGIN_PARAMETER_NAME);
        pageVariables.put("login", login != null ? login : DEFAULT_USER_NAME);
        return pageVariables;
    }

    protected void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        Map<String, Object> pageVariables = createPageVariablesMap(request);
        response.getWriter().println(TemplateProcessor.instance().getPage(ADMIN_PAGE_TEMPLATE, pageVariables));
        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }


}
