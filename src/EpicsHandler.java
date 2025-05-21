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
            String json = gson.toJson(manager.getEpics());
            writeResponse(httpExchange, json, 200);

        } else if (method.equals("GET") && pathArray.length == 3) {

            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getEpicById(id));
                writeResponse(httpExchange, json, 202);

            } catch (NullPointerException | NumberFormatException e) {
                writeResponse(httpExchange, "Not Found", 404);
            }

        } else if (method.equals("POST") && pathArray.length == 2) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Epic deserialisation = gson.fromJson(body,Epic.class);
            Epic newEpic = new Epic(deserialisation.taskName, deserialisation.taskDescription, TaskStatus.NEW);
            manager.addEpic(newEpic);
            writeResponse(httpExchange, "Эпик размещен", 201);

        } else if (method.equals("DELETE") && pathArray.length == 3) {
            manager.deleteEpicById(Integer.parseInt(pathArray[2]));
            writeResponse(httpExchange, "Эпик удален", 200);

        } else {
            writeResponse(httpExchange, "Неизвестный метод или ошибка в url", 404);
        }
    }
}