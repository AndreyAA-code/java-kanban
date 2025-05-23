package http_server;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;
import tasks.Task;

import java.io.IOException;
import java.io.InputStream;

public class TasksHandler extends BaseHttpHandler {

    public TasksHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Received tasks.Task request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2 && pathArray[1].equals("tasks")) {
            String json = gson.toJson(manager.getTasks());
            writeResponse(httpExchange, json, 200);

        } else if (method.equals("GET") && pathArray.length == 3 && pathArray[1].equals("tasks")) {
            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getTaskById(id));
                writeResponse(httpExchange, json, 200);

            } catch (NullPointerException | NumberFormatException e) {
                writeResponse(httpExchange, "Not Found", 404);
            }

        } else if (method.equals("POST") && pathArray.length == 2 && pathArray[1].equals("tasks")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
            manager.addTask(newTask);
            writeResponse(httpExchange, "tasks.Task created", 201);

        } else if (method.equals("POST") && pathArray.length == 3 && pathArray[1].equals("tasks")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
            if (manager.ifTaskExists(newTask.getTaskId())) {
                manager.updateTask(newTask);
                writeResponse(httpExchange, "tasks.Task changed", 201);
            } else {
                writeResponse(httpExchange, "Not Acceptable", 406);
            }

        } else if (method.equals("DELETE") && pathArray.length == 3 && pathArray[1].equals("tasks")) {
           try {
               int id = Integer.parseInt(pathArray[2]);
               if (manager.ifTaskExists(id)) {
                   manager.deleteTaskById(id);
                   writeResponse(httpExchange, "tasks.Task deleted", 200);
               } else {
                   writeResponse(httpExchange, "Not Acceptable", 406);
               }

           } catch (NullPointerException | NumberFormatException e) {
               writeResponse(httpExchange, "Not Found", 404);
           }

        } else {
            writeResponse(httpExchange, "Unknown method or incorrect url", 404);
        }
    }
}