import java.util.List;

public interface TaskManager {

    void addTask(Task task);//метод добавления задачи

    List<Task> getTasks(); //getter для hash map tasks

    List<Epic> getEpics(); //getter для hash map epics

    List<Subtask> getSubtasks(); //getter для hash map subtasks

    void addSubtask(Subtask subtask);//метод добавления подзадачи

    void addEpic(Epic epic);//метод добавления Эпика

    void printAllTasks();//печать всех задач//

    void deleteAllTasks();//удаление всех задач всех типов

    Task getTaskById(Integer taskId);//получение задачи по идентификатору

    Subtask getSubtaskByID(Integer taskId);//получение подзадачи по идентификатору

    Epic getEpicById(Integer taskId); //получение эпика по идентификатору

    void deleteTaskById(Integer taskId); //удаление задачи по идентификатору

    void deleteEpicById(Integer taskId); //удаление эпика по идентификатору

    void deleteSubtaskById(Integer taskId);//удаление подзадачи по идентификатору

    void updateTask(Task task); //метод update задачи

    void updateSubtask(Subtask subtask); //метод update подзадачи

    void updateEpic(Epic epic); //метод update эпика

    void updateEpicStatus(Integer epicID); //метод update статуса Эпика

    void updateEpicTimes(Integer epicId);

    List<Task> getHistory();// получение истории просмотра объектов

    void addPrioritizedTask(Task task); // добавление задачи в TreeSet для хранения сортировки по времени начала задачи

    boolean intersection(Task task); // проверка пересечения времен выполнения задач

    List<Task> getPrioritizedTasks();//получение задач отсортированных по времени старта

}
