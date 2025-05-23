
/*
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
*/
import java.io.IOException;




public class Main {
    public static void main(String[] args) throws IOException {

/*
        //Проверка по ТЗ спринта 7

        // создаем файл для сохранения всех видов задач;
        // Path pathBackupFile = Paths.get("/Users/andrey/IdeaProjects/java-kanban/kanbanSave.csv");
        Path path = Paths.get("kanbanSave.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //создаем новый менеджер с сохранением и передаем файл
        managers.TaskManager manager = managers.FileBackedTaskManager.loadFromFile(path);

        Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .registerTypeAdapter(Duration.class, new httpServer.DurationAdapter())
                .registerTypeAdapter(LocalDateTime.class, new httpServer.LocalDateTimeAdapter())
                .create();


        tasks.Epic epic1 = new tasks.Epic("epicname1", "epicdescr1", tasks.TaskStatus.NEW);
        manager.addEpic(epic1);
        tasks.Task task1 = new tasks.Task("taskname 1", "taskdescr1", tasks.TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 25));
        manager.addTask(task1);
        //System.out.println(epics);
        //   Gson gson = new GsonB();
      // String json = gson.toJson(epic1);
    //    System.out.println(json);

   //     tasks.Task newTask = gson.fromJson(json, new httpServer.TaskTypeToken().getType());
   //     System.out.println(newTask);
      //  tasks.TaskStatus idd = tasks.TaskStatus.NEWd;
       // manager.updateTask(new tasks.Task("taskname1", "taskdescr1", 1, idd,Duration.ofMinutes(15),
         //       LocalDateTime.of(2025, 05, 1, 14, 25)));
    /*    try {
            String json = gson.toJson(epic1);
            System.out.println(json);
            tasks.Epic newEpic = gson.fromJson(json, new EpicTypeToken().getType());
            manager.addEpic(newEpic);
            System.out.println(newEpic);
        } catch (NullPointerException| NumberFormatException exception) {
            System.out.println("No task found");
        }
     //   System.out.println(epics);
*/
    }
/*
    static class httpServer.DurationAdapter extends TypeAdapter<Duration> {
      //  private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("mm");

        @Override
        public void write(final JsonWriter jsonWriter, final Duration duration) throws IOException {
            jsonWriter.value(duration.toMinutes());
        }

        @Override
        public Duration read(final JsonReader jsonReader) throws IOException {
            return Duration.ofMinutes(Integer.parseInt(jsonReader.nextString()));
        }
    }
    static class httpServer.LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.format(dtf));
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), dtf);
        }
    }
    static class httpServer.TaskTypeToken extends TypeToken<tasks.Task> {
        // здесь ничего не нужно реализовывать
    }

*/
}
        /*
       Проверка по ТЗ спринта 8
        tasks.Task task1 = new tasks.Task("taskname 1", "taskdescr1", tasks.TaskStatus.NEW, Duration.ofMinutes(15),
              LocalDateTime.of(2025, 05, 1, 14, 25));
       manager.addTask(task1);
        tasks.Task task2 = new tasks.Task("taskname 2", "taskdescr2", tasks.TaskStatus.NEW, Duration.ofMinutes(10),
                LocalDateTime.of(2025, 05, 01, 14, 40));
         manager.addTask(task2);
         tasks.Epic epic1 = new tasks.Epic("epicname1", "epicdescr1", tasks.TaskStatus.NEW);


        manager.addEpic(epic1);

        tasks.Subtask subtask1 = new tasks.Subtask("subtaskname1", "subtaskdescr1", 3, tasks.TaskStatus.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2025, 05, 01, 15, 10));
        manager.addSubtask(subtask1);
        tasks.Subtask subtask2 = new tasks.Subtask("subtaskname2", "subtaskdescr2", 3, tasks.TaskStatus.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 05, 01, 16, 20));
        manager.addSubtask(subtask2);
        tasks.Subtask subtask3 = new tasks.Subtask("subtaskname3", "subtaskdescr3", 3, tasks.TaskStatus.NEW, Duration.ofMinutes(10),
                LocalDateTime.of(2025, 05, 03, 16, 50));
        manager.addSubtask(subtask3);
        System.out.println(manager.getPrioritizedTasks());

        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getHistory();


        // manager.updateSubtask(new tasks.Subtask("subtaskname1", "subtaskdescr1", 4, 3, tasks.TaskStatus.DONE, Duration.ofMinutes(10),LocalDateTime.of(2025,05,01,15,10)));
        //  manager.printAllTasks();

        //   manager.updateSubtask(new tasks.Subtask("subtaskname1", "subtaskdescr1", 4, 3, tasks.TaskStatus.DONE, Duration.ofMinutes(10), LocalDateTime.now()));
        //    manager.updateSubtask(new tasks.Subtask("subtaskname2", "subtaskdescr2", 5, 3, tasks.TaskStatus.DONE, Duration.ofMinutes(10), LocalDateTime.now()));
        //    manager.updateSubtask(new tasks.Subtask("subtaskname3", "subtaskdescr3", 6, 3, tasks.TaskStatus.DONE, Duration.ofMinutes(10), LocalDateTime.now()));
*/

        //Проверка по ТЗ спринта 7

        // tasks.Task task3 = new tasks.Task("taskname 4", "taskdescr3", tasks.TaskStatus.NEW, Duration.ofMinutes(2), LocalDateTime.now());
        // manager.addTask(task3);
        //   tasks.Subtask subtask1 = new tasks.Subtask("subtaskname1", "subtaskdescr1", 3, tasks.TaskStatus.NEW);
        //  manager.addSubtask(subtask1);
        // manager.deleteTaskById(10); //удалить задачу (передаем ID)
        // manager.deleteTaskById(11);
        // manager.updateSubtask(new tasks.Subtask("subtaskname1", "subtaskdescr1", 5, 3, tasks.TaskStatus.DONE));
        // manager.updateSubtask(new tasks.Subtask("subtaskname2", "subtaskdescr2", 6, 3, tasks.TaskStatus.DONE));
        // manager.updateSubtask(new tasks.Subtask("subtaskname3", "subtaskdescr3", 7, 3, tasks.TaskStatus.DONE));
        // manager.printAllTasks();
        // manager.deleteTaskById(2);
        // tasks.Epic epic1 = new tasks.Epic("epicname1", "epicdescr1", tasks.TaskStatus.NEW);
        // tasks.Epic epic1 = new tasks.Epic("epicname1", "epicdescr1", tasks.TaskStatus.NEW);
        //   manager.addEpic(epic1);
        //  manager.printAllTasks();
    /*    //создаем 2 задачи
        tasks.Task task1 = new tasks.Task("taskname 1", "taskdescr1", tasks.TaskStatus.NEW);
        manager.addTask(task1);
        tasks.Task task2 = new tasks.Task("taskname 2", "taskdescr2", tasks.TaskStatus.NEW);
        manager.addTask(task2);


        //создаем 2 эпика
        tasks.Epic epic1 = new tasks.Epic("epicname1", "epicdescr1", tasks.TaskStatus.NEW);
        manager.addEpic(epic1);
        tasks.Epic epic2 = new tasks.Epic("epicname2", "epicdescr2", tasks.TaskStatus.NEW);
        manager.addEpic(epic2);

        //создаем три подзадачи в epicname1
        tasks.Subtask subtask1 = new tasks.Subtask("subtaskname1", "subtaskdescr1", 3, tasks.TaskStatus.NEW);
        manager.addSubtask(subtask1);
        tasks.Subtask subtask2 = new tasks.Subtask("subtaskname2", "subtaskdescr2", 3, tasks.TaskStatus.NEW);
        manager.addSubtask(subtask2);
        tasks.Subtask subtask3 = new tasks.Subtask("subtaskname3", "subtaskdescr3", 3, tasks.TaskStatus.NEW);
        manager.addSubtask(subtask3);

        // проверка правильности работы кода
        manager.printAllTasks(); // напечатать все задачи
        manager.deleteTaskById(1);
        manager.deleteSubtaskById(7);
        manager.deleteEpicById(4);

        //  manager.deleteSubtaskById(7);
        manager.printAllTasks();
        manager.getHistory();

    /*    manager.updateTask(new tasks.Task("taskname1", "taskdescr1", 1, tasks.TaskStatus.IN_PROGRESS));
        manager.updateSubtask(new tasks.Subtask("subtaskname1", "subtaskdescr1", 5, 3, tasks.TaskStatus.DONE));
        manager.updateSubtask(new tasks.Subtask("subtaskname2", "subtaskdescr2", 6, 3, tasks.TaskStatus.DONE));
        manager.updateSubtask(new tasks.Subtask("subtaskname3", "subtaskdescr3", 7, 3, tasks.TaskStatus.DONE));
     manager.getHistory();
     manager.getEpicById(3);
     manager.getEpicById(4);
     manager.getSubtaskByID(5);
     manager.getSubtaskByID(6);
     manager.deleteTaskById(3);
     manager.deleteSubtaskById(5);

     manager.getSubtaskByID(7);
     manager.getHistory();

    //


       /* КОД 6 СПРИНТА

         //Проверка по ТЗ спринта 6
        managers.TaskManager manager = managers.Managers.getDefault(); ДЛЯ ПРОВЕРКИ 6 СПРИНТА

        //Пустой список. Два запроса истории просмотра одной и той же задачи + update ее статуса
        System.out.println("\n пустой список. два запроса истории просмотра одной и той же задачи + update ее статуса");
        manager.getTaskById(1); // получить данные по задаче по номеру ID
        manager.getHistory(); //список истории просмотра
        manager.updateTask(new tasks.Task("taskname1", "taskdescr1", 1, tasks.TaskStatus.IN_PROGRESS));
        manager.getTaskById(1); // получить данные по задаче по номеру ID
        manager.getHistory(); //список истории просмотра

        //просматриваем все остальные задачи и получаем историю
        System.out.println("\n + запросы просмотра остальных задач");
        manager.getTaskById(2); // получить данные по задаче по номеру ID
        manager.getEpicById(3);
        manager.getEpicById(4);
        manager.getSubtaskByID(5);
        manager.getSubtaskByID(6);
        manager.getSubtaskByID(7);
        manager.getHistory(); //список истории просмотра

        //повторный просмотр задачи в середине списка просмотра
        System.out.println("\n повторный просмотр задачи в середине списка просмотра");
        manager.getEpicById(3);
        manager.getHistory(); //список истории просмотра

        //повторный просмотр задачи в конце списка просмотра + это эпик + update статуса всех подзадач на DONE
        System.out.println("\n повторный просмотр задачи в конце списка просмотра + это эпик + update статуса всех подзадач на DONE");
        manager.updateSubtask(new tasks.Subtask("subtaskname1", "subtaskdescr1", 5, 3, tasks.TaskStatus.DONE));
        manager.updateSubtask(new tasks.Subtask("subtaskname2", "subtaskdescr2", 6, 3, tasks.TaskStatus.DONE));
        manager.updateSubtask(new tasks.Subtask("subtaskname3", "subtaskdescr3", 7, 3, tasks.TaskStatus.DONE));

        manager.getEpicById(3);
        manager.getHistory(); //список истории просмотра

        //удаление task
        System.out.println("\n просмотр истории при удалении задачи");
        manager.deleteTaskById(2); //удалить задачу (передаем ID)
        manager.getHistory(); //список истории просмотра

        //удаление subtask
        System.out.println("\n просмотр истории + списка подзадач эпика при удалении подзадачи");
        manager.deleteSubtaskById(5); //удалить задачу (передаем ID)
        manager.getHistory(); //список истории просмотра

        //удаление эпик
        System.out.println("\n просмотр истории при удалении эпика (должен исчезнуть эпик и его подзадачи");
        manager.deleteEpicById(3); //удалить задачу (передаем ID)
        manager.getHistory(); //список истории просмотра

        //удаление всех задач
        System.out.println("\n удаление всех задач");
        manager.deleteAllTasks(); //удалить задачу (передаем ID)
        manager.getHistory(); //список истории просмотра

        //заготовки для тестирования

        // System.out.println(manager.getTaskById(1)); // получить данные по задаче по номеру ID
        //System.out.println("смотрим историю");
        //manager.getHistory();
        //manager.updateTask(new tasks.Task("taskname1", "taskdescr1", 1, tasks.TaskStatus.IN_PROGRESS));
        //manager.updateTask(new tasks.Task("taskname2", "taskdescr2", 2, tasks.TaskStatus.IN_PROGRESS));
        //System.out.println(manager.getTaskById(1)); // получить данные по задаче по номеру ID
        //manager.getSubtaskByID(5);
        //manager.getEpicById(4);
        //manager.deleteTaskById(2); //удалить задачу (передаем ID)
        //manager.deleteSubtaskById(5); //удалить задачу (передаем ID)
        //manager.deleteEpicById(6); //удалить задачу (передаем ID)
        //manager.updateTask(new tasks.Task("taskname2", "taskdescr2", 2,tasks.TaskStatus.IN_PROGRESS)); //поменять статус задачи (передаем ID и новый статус)
        //manager.updateSubtask(new tasks.Subtask("subtaskname2new", "subtaskdescr2new",6, 3, tasks.TaskStatus.DONE)); //поменять статус задачи (передаем ID и новый статус)
        //manager.updateEpic(new tasks.Epic("epicname1new", "epicdescr1new", 4, tasks.TaskStatus.NEW));
        //manager.deleteAllTasks(); //удалить все задачи
       */ //КОНЕЦ КОДА 6 СПРИНТА

