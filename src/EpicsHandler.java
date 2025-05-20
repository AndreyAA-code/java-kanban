import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class EpicsHandler extends BaseHttpHandler {


    public EpicsHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Received Epic request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2) {
            httpExchange.sendResponseHeaders(200, 0);
            String json = gson.toJson(manager.getEpics());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(json.getBytes());
            }

        } else if (method.equals("GET") && pathArray.length == 3) {
            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getEpicById(id));
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
            System.out.println(manager);
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Epic newEpic = gson.fromJson(body, new EpicTypeToken().getType());
            manager.addEpic(newEpic);

            httpExchange.sendResponseHeaders(201, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Задача размещена".getBytes());
            }

        } else if (method.equals("DELETE") && pathArray.length == 3) {
            httpExchange.sendResponseHeaders(201, 0);
            manager.deleteEpicById(Integer.parseInt(pathArray[2]));
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Эпик удален".getBytes());
            }

        } else {
            System.out.println("Unknown method and path");
        }
    }
}