package http_server;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;
import tasks.Subtask;

import java.io.IOException;
import java.io.InputStream;

public class SubtasksHandler extends BaseHttpHandler {
    public SubtasksHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Received tasks.Subtask request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2 && pathArray[1].equals("subtasks")) {
            String json = gson.toJson(manager.getSubtasks());
            writeResponse(httpExchange, json, 200);

        } else if (method.equals("GET") && pathArray.length == 3 && pathArray[1].equals("subtasks")) {
            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getSubtaskByID(id));
                writeResponse(httpExchange, json, 200);

            } catch (NullPointerException | NumberFormatException e) {
                writeResponse(httpExchange, "Not Found", 404);
            }

        } else if (method.equals("POST") && pathArray.length == 2 && pathArray[1].equals("subtasks")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Subtask newSubtask = gson.fromJson(body, new SubtaskTypeToken().getType());
            manager.addSubtask(newSubtask);
            writeResponse(httpExchange, "tasks.Subtask created", 201);

        } else if (method.equals("POST") && pathArray.length == 3 && pathArray[1].equals("subtasks")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Subtask newSubtask = gson.fromJson(body, new SubtaskTypeToken().getType());

            if (manager.ifSubtaskExists(newSubtask.getTaskId())) {
            manager.updateSubtask(newSubtask);
            writeResponse(httpExchange, "tasks.Subtask changed", 201);
            } else {
                writeResponse(httpExchange, "Not Acceptable", 406);
            }

        } else if (method.equals("DELETE") && pathArray.length == 3 && pathArray[1].equals("subtasks")) {
            try {
                int id = Integer.parseInt(pathArray[2]);
                if (manager.ifSubtaskExists(id)) {
                    manager.deleteSubtaskById(id);
                    writeResponse(httpExchange, "tasks.Subtask deleted", 200);
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