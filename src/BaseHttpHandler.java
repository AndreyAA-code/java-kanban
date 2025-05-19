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

class EpicTypeToken extends TypeToken<Epic> { //Токен для перевода из json в Epic
}
