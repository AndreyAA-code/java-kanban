
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws IOException {


        //Проверка по ТЗ спринта 7

        // создаем файл для сохранения всех видов задач;
        // Path path = Paths.get("/Users/andrey/IdeaProjects/java-kanban/kanbanSave.csv");
        Path path = Paths.get("kanbanSave.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        //создаем новый менеджер с сохранением и передаем файл
        TaskManager manager = FileBackedTaskManager.loadFromFile(path);


       // Проверка по ТЗ спринта 8

        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025,05,1,14,25));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW,Duration.ofMinutes(10),
                LocalDateTime.of(2025,05,01,14,40));
        manager.addTask(task2);

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 3, TaskStatus.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2025,05,01,15,10));
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 3, TaskStatus.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025,05,01,16,20));
        manager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("subtaskname3", "subtaskdescr3", 3, TaskStatus.NEW, Duration.ofMinutes(10),
                LocalDateTime.of(2025,05,03,16,50));
        manager.addSubtask(subtask3);

       // manager.updateSubtask(new Subtask("subtaskname1", "subtaskdescr1", 4, 3, TaskStatus.DONE, Duration.ofMinutes(10),LocalDateTime.of(2025,05,01,15,10)));
      //  manager.printAllTasks();

     //   manager.updateSubtask(new Subtask("subtaskname1", "subtaskdescr1", 4, 3, TaskStatus.DONE, Duration.ofMinutes(10), LocalDateTime.now()));
    //    manager.updateSubtask(new Subtask("subtaskname2", "subtaskdescr2", 5, 3, TaskStatus.DONE, Duration.ofMinutes(10), LocalDateTime.now()));
    //    manager.updateSubtask(new Subtask("subtaskname3", "subtaskdescr3", 6, 3, TaskStatus.DONE, Duration.ofMinutes(10), LocalDateTime.now()));


      //  manager.printAllTasks();

       manager.getPrioritizedTasks();

        //Проверка по ТЗ спринта 7
       // Task task3 = new Task("taskname 4", "taskdescr3", TaskStatus.NEW, Duration.ofMinutes(2), LocalDateTime.now());
       // manager.addTask(task3);
     //   Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 3, TaskStatus.NEW);
      //  manager.addSubtask(subtask1);
        // manager.deleteTaskById(10); //удалить задачу (передаем ID)
        // manager.deleteTaskById(11);
        // manager.updateSubtask(new Subtask("subtaskname1", "subtaskdescr1", 5, 3, TaskStatus.DONE));
        // manager.updateSubtask(new Subtask("subtaskname2", "subtaskdescr2", 6, 3, TaskStatus.DONE));
        // manager.updateSubtask(new Subtask("subtaskname3", "subtaskdescr3", 7, 3, TaskStatus.DONE));
        // manager.printAllTasks();
        // manager.deleteTaskById(2);
       // Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
       // Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
     //   manager.addEpic(epic1);
      //  manager.printAllTasks();
    /*    //создаем 2 задачи
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW);
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW);
        manager.addTask(task2);


        //создаем 2 эпика
        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
        manager.addEpic(epic1);
        Epic epic2 = new Epic("epicname2", "epicdescr2", TaskStatus.NEW);
        manager.addEpic(epic2);

        //создаем три подзадачи в epicname1
        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 3, TaskStatus.NEW);
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 3, TaskStatus.NEW);
        manager.addSubtask(subtask2);
        Subtask subtask3 = new Subtask("subtaskname3", "subtaskdescr3", 3, TaskStatus.NEW);
        manager.addSubtask(subtask3);

        // проверка правильности работы кода
        manager.printAllTasks(); // напечатать все задачи
        manager.deleteTaskById(1);
        manager.deleteSubtaskById(7);
        manager.deleteEpicById(4);

        //  manager.deleteSubtaskById(7);
        manager.printAllTasks();
        manager.getHistory();

    /*    manager.updateTask(new Task("taskname1", "taskdescr1", 1, TaskStatus.IN_PROGRESS));
        manager.updateSubtask(new Subtask("subtaskname1", "subtaskdescr1", 5, 3, TaskStatus.DONE));
        manager.updateSubtask(new Subtask("subtaskname2", "subtaskdescr2", 6, 3, TaskStatus.DONE));
        manager.updateSubtask(new Subtask("subtaskname3", "subtaskdescr3", 7, 3, TaskStatus.DONE));
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
        TaskManager manager = Managers.getDefault(); ДЛЯ ПРОВЕРКИ 6 СПРИНТА

        //Пустой список. Два запроса истории просмотра одной и той же задачи + update ее статуса
        System.out.println("\n пустой список. два запроса истории просмотра одной и той же задачи + update ее статуса");
        manager.getTaskById(1); // получить данные по задаче по номеру ID
        manager.getHistory(); //список истории просмотра
        manager.updateTask(new Task("taskname1", "taskdescr1", 1, TaskStatus.IN_PROGRESS));
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
        manager.updateSubtask(new Subtask("subtaskname1", "subtaskdescr1", 5, 3, TaskStatus.DONE));
        manager.updateSubtask(new Subtask("subtaskname2", "subtaskdescr2", 6, 3, TaskStatus.DONE));
        manager.updateSubtask(new Subtask("subtaskname3", "subtaskdescr3", 7, 3, TaskStatus.DONE));

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
        //manager.updateTask(new Task("taskname1", "taskdescr1", 1, TaskStatus.IN_PROGRESS));
        //manager.updateTask(new Task("taskname2", "taskdescr2", 2, TaskStatus.IN_PROGRESS));
        //System.out.println(manager.getTaskById(1)); // получить данные по задаче по номеру ID
        //manager.getSubtaskByID(5);
        //manager.getEpicById(4);
        //manager.deleteTaskById(2); //удалить задачу (передаем ID)
        //manager.deleteSubtaskById(5); //удалить задачу (передаем ID)
        //manager.deleteEpicById(6); //удалить задачу (передаем ID)
        //manager.updateTask(new Task("taskname2", "taskdescr2", 2,TaskStatus.IN_PROGRESS)); //поменять статус задачи (передаем ID и новый статус)
        //manager.updateSubtask(new Subtask("subtaskname2new", "subtaskdescr2new",6, 3, TaskStatus.DONE)); //поменять статус задачи (передаем ID и новый статус)
        //manager.updateEpic(new Epic("epicname1new", "epicdescr1new", 4, TaskStatus.NEW));
        //manager.deleteAllTasks(); //удалить все задачи
       */ //КОНЕЦ КОДА 6 СПРИНТА
    }
}