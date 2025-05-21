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

        if (method.equals("GET") && pathArray.length == 2 && pathArray[1].equals("tasksgg")) {
            String json = gson.toJson(manager.getTasks());
            writeResponse(httpExchange, json, 200);

        } else if (method.equals("GET") && pathArray.length == 3 && pathArray[1].equals("tasksgg")) {
            try {
                Integer id = Integer.parseInt(pathArray[2]);
                String json = gson.toJson(manager.getTaskById(id));
                writeResponse(httpExchange, json, 200);

            } catch (NullPointerException | NumberFormatException e) {
                writeResponse(httpExchange, "Not Found", 404);
            }

        } else if (method.equals("POST") && pathArray.length == 2 && pathArray[1].equals("tasksgg")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
            manager.addTask(newTask);
            writeResponse(httpExchange, "Task created", 201);

        } else if (method.equals("POST") && pathArray.length == 3 && pathArray[1].equals("tasksgg")) {
            InputStream inputStream = httpExchange.getRequestBody();
            String body = new String(inputStream.readAllBytes());
            Task newTask = gson.fromJson(body, new TaskTypeToken().getType());
            if (manager.IfTaskExists(newTask.taskId)) {
                manager.updateTask(newTask);
                writeResponse(httpExchange, "Task changed", 201);
            } else {
                writeResponse(httpExchange, "No such task", 406);
            }

        } else if (method.equals("DELETE") && pathArray.length == 3 && pathArray[1].equals("tasksgg")) {
           try{
               int id = Integer.parseInt(pathArray[2]);
               if (manager.IfTaskExists(id)) {
                   manager.deleteTaskById(id);
                   writeResponse(httpExchange, "Task deleted", 200);
               } else {
                   writeResponse(httpExchange, "No such task", 406);
               }

           } catch (NullPointerException | NumberFormatException e) {
               writeResponse(httpExchange, "Not Found", 404);
           }

        } else {
            writeResponse(httpExchange, "Unknown method or incorrect url", 404);
        }
    }
}