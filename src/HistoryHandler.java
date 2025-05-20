import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class HistoryHandler extends BaseHttpHandler {
    public HistoryHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {


        System.out.println("Received get history request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2) {
            httpExchange.sendResponseHeaders(200, 0);
            String json = gson.toJson(manager.getPrioritizedTasks());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(json.getBytes());
            }

        } else {
            System.out.println("Unknown method and path");
        }

    }

}