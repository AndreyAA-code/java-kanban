import com.sun.net.httpserver.HttpServer;
import httpServer.*;
import managers.FileBackedTaskManager;
import managers.TaskManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import httpServer.*;

public class HttpTaskServer {
    public static void main(String[] args) throws IOException {

        int port = 8080;
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(port), 0);

        Path pathBackupFile = Paths.get("kanbanSave.csv");
        if (!Files.exists(pathBackupFile)) {
            Files.createFile(pathBackupFile);
        }

        final TaskManager manager = FileBackedTaskManager.loadFromFile(pathBackupFile);

        httpServer.createContext("/tasks", new TasksHandler(manager));
        httpServer.createContext("/subtasks", new SubtasksHandler(manager));
        httpServer.createContext("/epics", new EpicsHandler(manager));
        httpServer.createContext("/history", new HistoryHandler(manager));
        httpServer.createContext("/prioritized", new PrioritizedHandler(manager));

        httpServer.start();
        System.out.println("Local Server started on port: " + port);
    }
}