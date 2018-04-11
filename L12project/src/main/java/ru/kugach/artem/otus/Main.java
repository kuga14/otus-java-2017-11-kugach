package ru.kugach.artem.otus;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.kugach.artem.otus.base.DBService;
import ru.kugach.artem.otus.base.datasets.StudentDataSet;
import ru.kugach.artem.otus.cache.CacheEngineImpl;
import ru.kugach.artem.otus.dbservice.DBServiceImpl;
import ru.kugach.artem.otus.servlet.AdminServlet;
import ru.kugach.artem.otus.servlet.AuthFilter;
import ru.kugach.artem.otus.servlet.LoginServlet;

public class Main {

    private static final int MAX = 100_000;
    private final static int PORT = 8090;
    private final static String PUBLIC_HTML = "public_html";
    private final static DBService dbService = new DBServiceImpl(new CacheEngineImpl(MAX / 2, MAX, 0, false));

    public static void main(String[] args) throws Exception {
        new Main().run();
    }

    private void run() throws Exception {
        runDBCacheExample();
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setResourceBase(PUBLIC_HTML);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(LoginServlet.class, "/login");
        context.addServlet(new ServletHolder(new AdminServlet(dbService)), "/admin");
        context.addFilter(AuthFilter.class, "/admin", null);
        Server server = new Server(PORT);
        server.setHandler(new HandlerList(resourceHandler, context));
        server.start();
        server.join();
    }

    private void runDBCacheExample(){
        new Thread(() -> {
                StudentDataSet student;
                for (int i = 1; i < MAX; i++) {
                    student = new StudentDataSet();
                    student.setName("Artem" + i);
                    dbService.save(student);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(i%10==0) dbService.load(i-5, StudentDataSet.class);
                }


            }
        ).start();
    }
}
