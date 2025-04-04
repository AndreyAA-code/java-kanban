public static void main(String[] args) {

    //Проверка по ТЗ спринта 4,5

     TaskManager manager = Managers.getDefault();
    //создаем 2 задачи
    Task task1 = new Task("taskname1", "taskdescr1", TaskStatus.NEW);
     manager.addTask(task1);
     Task task2 = new Task("taskname2", "taskdescr2", TaskStatus.NEW);
     manager.addTask(task2);
    //Task task3 = new Task("taskname3", "taskdescr3", TaskStatus.NEW);
    //manager.addTask(task3);
     //System.out.println("pppppppp"+manager.getTasks());
    //создаем 2 эпика
    Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
    manager.addEpic(epic1);
    //Epic epic2 = new Epic("epicname2", "epicdescr2", TaskStatus.NEW);
   // manager.addEpic(epic2);
    //создаем одну подзадачу в epicname1
   //  Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 3, TaskStatus.NEW);
  // manager.addSubtask(subtask1);
    //создаем 2 подзадачи в epicname2
   // Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 3, TaskStatus.NEW);
  //  manager.addSubtask(subtask2);
  //  Subtask subtask3 = new Subtask("subtaskname3", "subtaskdescr3", 3, TaskStatus.NEW);
  //  manager.addSubtask(subtask3);
    // проверка правильности работы кода
     manager.printAllTasks(); // напечатать все задачи
    //manager.deleteAllTasks(); //удалить все задачи
    System.out.println("Запросили по ид 1 и вторую задачи");
    manager.getTaskById(1); // получить данные по задаче по номеру ID
    manager.getTaskById(2);
   // manager.getTaskById(3);
   manager.getEpicById(3);
  //  System.out.println(manager.getTaskById(1)); // получить данные по задаче по номеру ID
   // System.out.println(manager.getTaskById(2));
    System.out.println("смотрим историю");
    manager.getHistory();
    System.out.println("Обновляем задачи (меняем статус 1 и 2 на ин-прогресс и запрашиваем их");
   // manager.updateTask(new Task("taskname1", "taskdescr1", 1,TaskStatus.IN_PROGRESS));
   // manager.updateTask(new Task("taskname2", "taskdescr2", 2,TaskStatus.IN_PROGRESS));
   // System.out.println(manager.getTaskById(1)); // получить данные по задаче по номеру ID
   // System.out.println(manager.getTaskById(2));
    manager.getTaskById(1); // получить данные по задаче по номеру ID
    manager.getTaskById(2);
    System.out.println("смотрим историю");
   // manager.getHistory();
    System.out.println("удаляем 2 задачу");
   // manager.deleteTaskById(2);
    System.out.println("смотрим историю");
   // manager.getHistory();
   // System.out.println(manager.getTaskById(1)); // получить данные по задаче по номеру ID
   // System.out.println(manager.getTaskById(2));
   // manager.deleteTaskById(2); //удалить задачу (передаем ID)
   // manager.getHistory();
    //System.out.println(manager.getTaskById(3));// manager.getHistory();
   //  System.out.println(manager.getSubtaskByID(5)); // получить данные по подзадаче по номеру ID
    // System.out.println(manager.getEpicById(3)); // получить данные по эпику по номеру ID
    //manager.deleteTaskById(2); //удалить задачу (передаем ID)
    //manager.deleteSubtaskById(5); //удалить задачу (передаем ID)
    //manager.deleteEpicById(6); //удалить задачу (передаем ID)
   // manager.updateTask(new Task("taskname2", "taskdescr2", 2,TaskStatus.IN_PROGRESS)); //поменять статус задачи (передаем ID и новый статус)
    //manager.updateSubtask(new Subtask("subtaskname2new", "subtaskdescr2new",6, 3, TaskStatus.DONE)); //поменять статус задачи (передаем ID и новый статус)
    //manager.updateSubtask(new Subtask("subtaskname3new", "subtaskdescr3new",7, 3, TaskStatus.DONE)); //поменять с
    // manager.updateEpic(new Epic("epicname1new", "epicdescr1new", 4, TaskStatus.NEW));
    //manager.updateSubtask(new Subtask("subtaskname2", "subtaskdescr2", 4, TaskStatus.DONE));
    // manager.updateSubtask(new Subtask("subtaskname3", "subtaskdescr3", 4, TaskStatus.DONE));
    //manager.updateEpic(new Epic("epicname1new", "epicdescr1new", 4, TaskStatus.NEW));
    //manager.changeTaskStatus(7,TaskStatus.DONE); //поменять статус задачи (передаем ID и новый статус)
   // manager.getHistory();
}