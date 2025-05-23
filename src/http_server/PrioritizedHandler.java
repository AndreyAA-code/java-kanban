package http_server;

import com.sun.net.httpserver.HttpExchange;
import managers.TaskManager;

import java.io.IOException;

public class PrioritizedHandler extends BaseHttpHandler {
    public PrioritizedHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Received get prioritised request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2 && pathArray[1].equals("prioritized")) {
            String json = gson.toJson(manager.getPrioritizedTasks());
            writeResponse(httpExchange, json, 200);

        } else {
            writeResponse(httpExchange, "Unknown method or incorrect url", 404);
        }

    }

}