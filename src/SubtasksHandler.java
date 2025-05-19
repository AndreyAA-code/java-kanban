import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class SubtasksHandler extends BaseHttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Received Subtask request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2) {
            httpExchange.sendResponseHeaders(200, 0);
            String json = gson.toJson(manager.getSubtasks());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(json.getBytes());
            }

        } else if (method.equals("GET") && pathArray.length == 3) {
            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getSubtaskByID(id));
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
            Subtask newSubtask = gson.fromJson(body, new SubtaskTypeToken().getType());
            manager.addSubtask(newSubtask);
            httpExchange.sendResponseHeaders(201, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Подзадача размещена".getBytes());
            }

        } else if (method.equals("POST") && pathArray.length == 3) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Subtask newSubtask = gson.fromJson(body, new SubtaskTypeToken().getType());
            manager.updateSubtask(newSubtask);
            httpExchange.sendResponseHeaders(201, 0);
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Подзадача изменена".getBytes());
            }

        } else if (method.equals("DELETE") && pathArray.length == 3) {
            httpExchange.sendResponseHeaders(201, 0);
            manager.deleteSubtaskById(Integer.parseInt(pathArray[2]));
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write("Подзадача удалена".getBytes());
            }

        } else {
            System.out.println("Unknown method and path");
        }

    }

}