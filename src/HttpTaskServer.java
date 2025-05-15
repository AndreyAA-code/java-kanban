import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

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

class TasksHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        Path path = Paths.get("kanbanSave.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //создаем новый менеджер с сохранением и передаем файл
        TaskManager manager = FileBackedTaskManager.loadFromFile(path);

        System.out.println("Received tasks request");
        String method = httpExchange.getRequestMethod();
        String path1 = httpExchange.getRequestURI().getPath();
        String[] pathArray = httpExchange.getRequestURI().getPath().split("/");

        if (method.equals("GET") && pathArray.length == 2) {

            httpExchange.sendResponseHeaders(200, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write((manager.getTasks()).toString().getBytes());
            }

        } else if (method.equals("GET") && pathArray.length == 3) {
            httpExchange.sendResponseHeaders(202, 0);
            Integer id = Integer.parseInt(pathArray[2]);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write((manager.getTaskById(id)).toString().getBytes());
            }
            System.out.println(method + " id " + path1);
        } else if (method.equals("POST")) {


        } else if (method.equals("DELETE")) {

        } else {
            System.out.println("Unknown method and path " + method + " " + path1);
        }


    }

}

        /* String method = httpExchange.getRequestMethod();
        switch (method) {
            case "GET":
                handleGetRequest(httpExchange);
                break;
            case "POST":
                handlePostRequest(httpExchange);
                break;
            case "DELETE":
                handleDeleteRequest(httpExchange);
                break;
        }
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("HTTP/1.1 200 OK tasks\r\n\r\n".getBytes());
        }
    }
    void handleGetRequest (HttpExchange httpExchange) throws IOException {
        System.out.println("Received GET request");
        String path = httpExchange.getRequestURI().getPath();
        String id = path.split("/")[2];
        System.out.println("ID: " + id);
        httpExchange.sendResponseHeaders(200, 0);
        String response = "ID: " + id + "\r\n\r\n";
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
    void handlePostRequest (HttpExchange httpExchange) throws IOException {
        System.out.println("Received POST request");
    }
    void handleDeleteRequest (HttpExchange httpExchange) throws IOException {
        System.out.println("Received DELETE request");
    } */
