import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        Path path = Paths.get("kanbanSave.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //создаем новый менеджер с сохранением и передаем файл
        TaskManager manager = FileBackedTaskManager.loadFromFile(path);
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 25));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(10),
                LocalDateTime.of(2025, 05, 01, 14, 40));
        manager.addTask(task2);


        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port),0);
        httpServer.createContext("/tasks", new TasksHandler());
        httpServer.createContext("/subtasks", new SubtasksHandler());
        httpServer.start();
        System.out.println("Local Server started on port " + port);

    }
}

class TasksHandler implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Received tasks request");
        String method = httpExchange.getRequestMethod();
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
    }
}

class SubtasksHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Received subtasks request");
        httpExchange.sendResponseHeaders(200, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write("HTTP/1.1 200 OK subtasks\r\n\r\n".getBytes());
        }
    }
}