import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    public static void main(String[] args) throws IOException {

        int port = 8080;

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port),0);

        httpServer.createContext("/tasks", new TasksHandler());
    //    httpServer.createContext("/subtasks", new SubtasksHandler());
      //  httpServer.createContext("/epics", new EpicsHandler());
     //   httpServer.createContext("/history", new HistoryHandler());
    //    httpServer.createContext("/prioritized", new PrioritizedHandler());

        httpServer.start();
        System.out.println("Local Server started on port " + port);
    }
}