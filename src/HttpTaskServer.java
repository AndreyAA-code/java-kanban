import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
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

String basePath = "/tasks";
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Received tasks request");
        String method = httpExchange.getRequestMethod();
        String path = httpExchange.getRequestURI().getPath();
        String param = getPath(httpExchange,basePath);

        if (method.equals("GET")) {
            if (param.isEmpty()) {
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write((method + " пустой ").getBytes());
                }
                System.out.println(method + " пустой " + path);
            } else {
                httpExchange.sendResponseHeaders(200, 0);
                Integer id = Integer.parseInt(param);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write((method + " id " + id ).getBytes());
                }
                System.out.println(method + " id " + path);
            }
            return;

        } else if (method.equals("POST")) {
            if (param.isEmpty()) {
                httpExchange.sendResponseHeaders(200, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write((method + " пустой " + path).getBytes());
                }
                System.out.println(method + " пустой " + path);
            } else {
                httpExchange.sendResponseHeaders(404, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(("ошибка").getBytes());
                }
                System.out.println(method + " id " + path);
                return;
            }
        }  else if (method.equals("DELETE")) {
                if (param.isEmpty()) {
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write((method + " пустой " + path).getBytes());
                    }
                    System.out.println(method + " пустой " + path);
                } else {
                    httpExchange.sendResponseHeaders(404, 0);
                    try (OutputStream os = httpExchange.getResponseBody()) {
                        os.write(("ошибка").getBytes());
                    }
                    System.out.println(method + " id " + path);
                    return;
                }
        } else {
            System.out.println("Unknown method and path " + method + " " + path);
        }


    }
    public String getPath(HttpExchange httpExchange, String basePath) {
        String param;
        String path = httpExchange.getRequestURI().getPath();
        if (path.contains("/basePath/")) {
            param = path.substring(basePath.length() + 1);
        }else{
            param = path.substring(basePath.length());
        }
        return param;
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
