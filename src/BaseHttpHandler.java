import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseHttpHandler implements HttpHandler {
    String method;
    String path;
    String[] pathArray;

    Path pathBackupFile = Paths.get("kanbanSave.csv");
    /*        if (!Files.exists(pathBackupFile)) {
            Files.createFile(pathBackupFile);   !!!!!! подумать
        } */
    //создаем новый менеджер с сохранением и передаем файл
    TaskManager manager = FileBackedTaskManager.loadFromFile(pathBackupFile);

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

}

class TasksHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Received Task request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2) {

            httpExchange.sendResponseHeaders(200, 0);
            String json = gson.toJson(manager.getTasks());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(json.getBytes());
            }

        } else if (method.equals("GET") && pathArray.length == 3) {

            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getTaskById(id));
                httpExchange.sendResponseHeaders(202, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write(json.getBytes());
                }
            } catch (NullPointerException|NumberFormatException e) {
                httpExchange.sendResponseHeaders(404, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write("Not Found".getBytes());
                }
            }

        } else if (method.equals("POST") && pathArray.length == 2) {
               InputStream inputStream = httpExchange.getRequestBody();
               String body = new String(inputStream.readAllBytes());
               Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
               manager.addTask(newTask);
               httpExchange.sendResponseHeaders(201, 0);
               try (OutputStream os = httpExchange.getResponseBody()) {
                   os.write("Задача размещена".getBytes());
               }

        } else if (method.equals("POST") && pathArray.length == 3) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
            manager.updateTask(newTask);
            httpExchange.sendResponseHeaders(201, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Задача изменена".getBytes());
            }

        } else if (method.equals("DELETE") && pathArray.length == 3) {
            httpExchange.sendResponseHeaders(201, 0);
            manager.deleteTaskById(Integer.parseInt(pathArray[2]));
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Задача удалена".getBytes());
            }

        } else {
            System.out.println("Unknown method and path");
        }

    }

}
class DurationAdapter extends TypeAdapter<Duration> { //адаптер json для поля duration

    @Override
    public void write(final JsonWriter jsonWriter, final Duration duration) throws IOException {
        jsonWriter.value(duration.toMinutes());
    }

    @Override
    public Duration read(final JsonReader jsonReader) throws IOException {
        return Duration.ofMinutes(Integer.parseInt(jsonReader.nextString()));
    }
}

class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> { //адаптер json для полей startTime и endTime
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @Override
    public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
        jsonWriter.value(localDateTime.format(dtf));
    }

    @Override
    public LocalDateTime read(final JsonReader jsonReader) throws IOException {
        return LocalDateTime.parse(jsonReader.nextString(), dtf);
    }
}

class TaskTypeToken extends TypeToken<Task> { //Токен для перевода из json в Task
}
