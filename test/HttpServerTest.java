import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.net.URI;
import java.net.http.HttpClient;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HttpServerTest {
    final TaskManager manager = new InMemoryTaskManager();

    Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
            LocalDateTime.of(2025, 06, 1, 14, 25));
    Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(10),
            LocalDateTime.of(2025, 05, 01, 14, 40));

    Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
    Epic epic2 = new Epic("epicname2", "epicdescr2", TaskStatus.NEW);

    Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW, Duration.ofMinutes(60),
            LocalDateTime.of(2025, 05, 01, 15, 10));
    Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 1, TaskStatus.DONE, Duration.ofMinutes(30),
            LocalDateTime.of(2025, 05, 01, 16, 20));

    Gson gson = new GsonBuilder()
            .serializeNulls()
            .setPrettyPrinting()
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();
    HttpServer httpServer;

    @BeforeEach
    public void SetUpServer() throws IOException, InterruptedException {
        int port = 8080;
        httpServer = HttpServer.create(new InetSocketAddress(port), 0);

        httpServer.createContext("/tasks", new TasksHandler(manager));
        httpServer.createContext("/subtasks", new SubtasksHandler(manager));
        httpServer.createContext("/epics", new EpicsHandler(manager));
        httpServer.createContext("/history", new HistoryHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(manager));

        httpServer.start();
        System.out.println("Local Server started on port: " + port);

    }

    @AfterEach
    public void TearDownServer() {
        manager.deleteAllTasks();
        manager.getEpics();
        manager.getSubtasks();
        manager.getTasks();

        httpServer.stop(1);
        System.out.println("Local Server stopped");
    }

    @Test
    public void testTasksEndpoint() throws IOException, InterruptedException {
        // создаём задачу

        // конвертируем её в JSON
        String taskJson1 = gson.toJson(task1);
        String taskJson2 = gson.toJson(task2);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/tasks");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson1)).build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson2)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response1.statusCode());
        assertEquals(201, response1.statusCode());

        List<Task> tasksFromManager = manager.getTasks();
        System.out.println(tasksFromManager.size());
        assertNotNull(tasksFromManager, "Задачи не возвращаются");
        assertEquals(2, tasksFromManager.size(), "Некорректное количество задач");
        assertEquals("taskname 1", tasksFromManager.get(0).taskName, "Некорректное имя задачи");

        URI url1 = URI.create("http://localhost:8080/tasks/1");
        HttpRequest request3 = HttpRequest.newBuilder().uri(url1).DELETE().build();
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        List<Task> tasksFromManager1 = manager.getTasks();

        assertEquals(200, response3.statusCode());
        assertEquals(2, tasksFromManager1.get(0).taskId, "Удаление не прошло");
    }

    @Test
    public void testEpicsEndpoint() throws IOException, InterruptedException {
        // создаём задачу
        // конвертируем её в JSON
        String taskJson1 = gson.toJson(epic1);
        String taskJson2 = gson.toJson(epic2);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson1)).build();
        HttpRequest request2 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson2)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response1.statusCode());
        assertEquals(201, response2.statusCode());

        List<Epic> epicsFromManager = manager.getEpics();

        assertNotNull(epicsFromManager, "Задачи не возвращаются");
        assertEquals(2, epicsFromManager.size(), "Некорректное количество задач");
        assertEquals("epicname1", epicsFromManager.get(0).taskName, "Некорректное имя задачи");

        URI url2 = URI.create("http://localhost:8080/epics/1");
        HttpRequest request3 = HttpRequest.newBuilder().uri(url2).DELETE().build();

        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response3.statusCode());

        List<Epic> epicsFromManager1 = manager.getEpics();

        assertEquals(2, epicsFromManager1.get(0).taskId, "Удаление не прошло");

    }

    @Test
    public void testSubtasksEndpoint() throws IOException, InterruptedException {
        // создаём задачу
        // конвертируем её в JSON
        String taskJson1 = gson.toJson(epic1);
        String taskJson2 = gson.toJson(subtask1);
        String taskJson3 = gson.toJson(subtask2);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson1)).build();

        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(HttpRequest.BodyPublishers.ofString(taskJson2)).build();
        HttpRequest request3 = HttpRequest.newBuilder().uri(url2).POST(HttpRequest.BodyPublishers.ofString(taskJson3)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        // проверяем код ответа
        assertEquals(201, response1.statusCode());
        assertEquals(201, response2.statusCode());
        assertEquals(201, response3.statusCode());

        List<Epic> epicsFromManager = manager.getEpics();
        assertEquals("IN_PROGRESS", epicsFromManager.get(0).taskStatus.toString());

        // проверяем, что создались две задача с корректным именем

        List<Subtask> subtasksFromManager = manager.getSubtasks();

        assertNotNull(subtasksFromManager, "Задачи не возвращаются");
        assertEquals(2, subtasksFromManager.size(), "Некорректное количество задач");
        assertEquals("subtaskname1", subtasksFromManager.get(0).taskName, "Некорректное имя задачи");

        URI url4 = URI.create("http://localhost:8080/subtasks/2");
        HttpRequest request4 = HttpRequest.newBuilder().uri(url4).DELETE().build();

        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response4.statusCode());

        List<Subtask> subtasksFromManager1 = manager.getSubtasks();
        List<Epic> epicsFromManager1 = manager.getEpics();

        assertEquals(2, subtasksFromManager.get(0).taskId, "Удаление не прошло");
        assertEquals("DONE", epicsFromManager.get(0).taskStatus.toString());

    }

    @Test
    public void testProiritizedEndpoint() throws IOException, InterruptedException {
        String taskJson = gson.toJson(epic1);
        String taskJson1 = gson.toJson(task1);
        String taskJson2 = gson.toJson(subtask1);
        String taskJson3 = gson.toJson(subtask2);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        URI url1 = URI.create("http://localhost:8080/tasks");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).POST(HttpRequest.BodyPublishers.ofString(taskJson1)).build();

        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(HttpRequest.BodyPublishers.ofString(taskJson2)).build();
        HttpRequest request3 = HttpRequest.newBuilder().uri(url2).POST(HttpRequest.BodyPublishers.ofString(taskJson3)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        URI url4 = URI.create("http://localhost:8080/tasks/2");
        HttpRequest request4 = HttpRequest.newBuilder().uri(url4).GET().build();
        HttpResponse<String> response4 = client.send(request4, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response4.statusCode());

        URI url5 = URI.create("http://localhost:8080/subtasks/3");
        HttpRequest request5 = HttpRequest.newBuilder().uri(url5).GET().build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response5.statusCode());

        URI url6 = URI.create("http://localhost:8080/subtasks/4");
        HttpRequest request6 = HttpRequest.newBuilder().uri(url6).GET().build();
        HttpResponse<String> response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response6.statusCode());

        URI url7 = URI.create("http://localhost:8080/prioritized");
        HttpRequest request7 = HttpRequest.newBuilder().uri(url7).GET().build();
        HttpResponse<String> response7 = client.send(request7, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response7.statusCode());

        List<Task> prioritizedTasksFromManager = manager.getPrioritizedTasks();

        assertNotNull(prioritizedTasksFromManager, "Задачи не возвращаются");
        assertEquals(3, prioritizedTasksFromManager.size(), "Некорректное количество задач");
        assertEquals("subtaskname1", prioritizedTasksFromManager.get(0).taskName, "Некорректное имя задачи");

    }

    @Test
    public void testHistoryEndpoint() throws IOException, InterruptedException {
        String taskJson = gson.toJson(epic1);
        String taskJson1 = gson.toJson(task1);
        String taskJson2 = gson.toJson(subtask1);
        String taskJson3 = gson.toJson(subtask2);

        // создаём HTTP-клиент и запрос
        HttpClient client = HttpClient.newHttpClient();
        URI url = URI.create("http://localhost:8080/epics");
        HttpRequest request = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();

        URI url1 = URI.create("http://localhost:8080/tasks");
        HttpRequest request1 = HttpRequest.newBuilder().uri(url1).POST(HttpRequest.BodyPublishers.ofString(taskJson1)).build();

        URI url2 = URI.create("http://localhost:8080/subtasks");
        HttpRequest request2 = HttpRequest.newBuilder().uri(url2).POST(HttpRequest.BodyPublishers.ofString(taskJson2)).build();
        HttpRequest request3 = HttpRequest.newBuilder().uri(url2).POST(HttpRequest.BodyPublishers.ofString(taskJson3)).build();

        // вызываем рест, отвечающий за создание задач
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response1 = client.send(request1, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response2 = client.send(request2, HttpResponse.BodyHandlers.ofString());
        HttpResponse<String> response3 = client.send(request3, HttpResponse.BodyHandlers.ofString());

        URI url5 = URI.create("http://localhost:8080/subtasks/3");
        HttpRequest request5 = HttpRequest.newBuilder().uri(url5).GET().build();
        HttpResponse<String> response5 = client.send(request5, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response5.statusCode());

        URI url6 = URI.create("http://localhost:8080/subtasks/4");
        HttpRequest request6 = HttpRequest.newBuilder().uri(url6).GET().build();
        HttpResponse<String> response6 = client.send(request6, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response6.statusCode());

        URI url7 = URI.create("http://localhost:8080/history");
        HttpRequest request7 = HttpRequest.newBuilder().uri(url7).GET().build();
        HttpResponse<String> response7 = client.send(request7, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response7.statusCode());

        List<Task> historyFromManager = manager.getHistory();

        assertNotNull(historyFromManager, "Задачи не возвращаются");
        assertEquals(2, historyFromManager.size(), "Некорректное количество задач");
        assertEquals("subtaskname1", historyFromManager.get(0).taskName, "Некорректное имя задачи");

    }
}
