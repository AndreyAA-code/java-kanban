import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);
        final TaskManager manager = new InMemoryTaskManager();

        httpServer.createContext("/tasks", new TasksHandler(manager));
        httpServer.createContext("/subtasks", new SubtasksHandler(manager));
        httpServer.createContext("/epics", new EpicsHandler(manager));
        httpServer.createContext("/history", new HistoryHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(manager));

        httpServer.start();
        System.out.println("Local Server started on port: " + port);
        httpServer.stop(1);
    }
}