import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class TasksHandler extends BaseHttpHandler {

    public TasksHandler(TaskManager manager) {
        super(manager);
    }

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
            } catch (NullPointerException | NumberFormatException e) {
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

            if (manager.IfTaskExists(newTask.taskId)) {
               manager.updateTask(newTask);
               httpExchange.sendResponseHeaders(201, 0);
               try (OutputStream os = httpExchange.getResponseBody()) {
                   os.write("Задача изменена".getBytes());
               }
           } else {
                httpExchange.sendResponseHeaders(406, 0);
                try (OutputStream os = httpExchange.getResponseBody()) {
                    os.write("Такой задачи нет".getBytes());
                }
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