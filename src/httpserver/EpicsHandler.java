package httpserver;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;
import tasks.Epic;
import tasks.TaskStatus;

import java.io.IOException;
import java.io.InputStream;

public class EpicsHandler extends BaseHttpHandler {

    public EpicsHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Received tasks.Epic request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2 && pathArray[1].equals("epics")) {
            String json = gson.toJson(manager.getEpics());
            writeResponse(httpExchange, json, 200);

        } else if (method.equals("GET") && pathArray.length == 3 && pathArray[1].equals("epics")) {

            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getEpicById(id));
                writeResponse(httpExchange, json, 200);

            } catch (NullPointerException | NumberFormatException e) {
                writeResponse(httpExchange, "Not Found", 404);
            }

        } else if (method.equals("POST") && pathArray.length == 2 && pathArray[1].equals("epics")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Epic deserialisation = gson.fromJson(body, Epic.class);
            Epic newEpic = new Epic(deserialisation.getTaskName(), deserialisation.getTaskDescription(), TaskStatus.NEW);
            manager.addEpic(newEpic);
            writeResponse(httpExchange, "Эпик размещен", 201);

        } else if (method.equals("DELETE") && pathArray.length == 3 && pathArray[1].equals("epics")) {
            try {
                int id = Integer.parseInt(pathArray[2]);
                if (manager.isEpicExists(id)) {
                    manager.deleteEpicById(id);
                    writeResponse(httpExchange, "tasks.Epic created", 200);
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