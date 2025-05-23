package httpserver;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import managers.TaskManager;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;

public abstract class BaseHttpHandler implements HttpHandler {
    protected String method;
    protected String path;
    protected String[] pathArray;
    protected TaskManager manager;
    protected Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    public void splitData(HttpExchange httpExchange) throws IOException { //сплит запроса
        method = httpExchange.getRequestMethod();
        path = httpExchange.getRequestURI().getPath();
        pathArray = httpExchange.getRequestURI().getPath().split("/");
    }

    public void writeResponse(HttpExchange httpExchange, String response, int responseCode) throws IOException {
        httpExchange.sendResponseHeaders(responseCode, 0);
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(response.getBytes());
        }
    }
}


