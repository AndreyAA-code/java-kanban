import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseHttpHandler implements HttpHandler {
    String method;
    String path;
    String[] pathArray;
    TaskManager manager;

    public BaseHttpHandler(TaskManager manager) {
        this.manager = manager;
    }

    Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
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

//class EpicTypeToken extends TypeToken<Epic> { //Токен для перевода из json в Epic
//}


