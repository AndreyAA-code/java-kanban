import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class PrioritizedHandler extends BaseHttpHandler {
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
            writeResponse(httpExchange, "Неизвестный метод или ошибка в url", 404);
        }

    }

}