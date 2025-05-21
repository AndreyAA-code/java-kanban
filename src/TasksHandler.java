import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.InputStream;

class TasksHandler extends BaseHttpHandler {

    public TasksHandler(TaskManager manager) {
        super(manager);
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {

        System.out.println("Received Task request");
        splitData(httpExchange);

        if (method.equals("GET") && pathArray.length == 2) {
            String json = gson.toJson(manager.getTasks());
            writeResponse(httpExchange, json, 200);

        } else if (method.equals("GET") && pathArray.length == 3) {
            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getTaskById(id));
                writeResponse(httpExchange, json, 202);

            } catch (NullPointerException | NumberFormatException e) {
                writeResponse(httpExchange, "Not Found", 404);
            }

        } else if (method.equals("POST") && pathArray.length == 2) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
            manager.addTask(newTask);
            writeResponse(httpExchange, "Задача размещена", 201);

        } else if (method.equals("POST") && pathArray.length == 3) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
            if (manager.IfTaskExists(newTask.taskId)) {
                manager.updateTask(newTask);
                writeResponse(httpExchange, "Задача изменена", 201);
            } else {
                writeResponse(httpExchange, "Такой задачи нет", 406);
            }

        } else if (method.equals("DELETE") && pathArray.length == 3) {
            manager.deleteTaskById(Integer.parseInt(pathArray[2]));
            writeResponse(httpExchange, "Задача удалена", 200);

        } else {
            writeResponse(httpExchange, "Неизвестный метод или ошибка в url", 404);
        }
    }
}