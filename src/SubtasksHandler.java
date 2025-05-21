import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;

class SubtasksHandler extends BaseHttpHandler {
    public SubtasksHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Received Subtask request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2 &&pathArray[1].equals("subtasks")) {
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
            writeResponse(httpExchange, "Подзадача размещена", 201);

        } else if (method.equals("POST") && pathArray.length == 3 && pathArray[1].equals("subtasks")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Subtask newSubtask = gson.fromJson(body, new SubtaskTypeToken().getType());

            if (manager.IfSubtaskExists(newSubtask.taskId)) {
            manager.updateSubtask(newSubtask);
            writeResponse(httpExchange, "Подзадача изменена", 201);
            } else {
                writeResponse(httpExchange, "Такой подзадачи нет", 406);
            }

        } else if (method.equals("DELETE") && pathArray.length == 3 && pathArray[1].equals("subtasks")) {
            manager.deleteSubtaskById(Integer.parseInt(pathArray[2]));
                writeResponse(httpExchange, "Задача удалена", 200);

        } else {
            writeResponse(httpExchange, "Неизвестный метод или ошибка в url", 404);
        }

    }
}