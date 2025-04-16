import java.io.IOException;
import java.util.List;

public interface TaskManager {

    Integer addTask(Task task) throws IOException;//метод добавления задачи

    List<Task> getTasks(); //getter для hash map tasks

    List<Epic> getEpics(); //getter для hash map epics

    List<Subtask> getSubtasks(); //getter для hash map subtasks

    void addSubtask(Subtask subtask) throws IOException;//метод добавления подзадачи

    void addEpic(Epic epic) throws IOException;//метод добавления Эпика

    void printAllTasks();//печать всех задач//

    void deleteAllTasks();//удаление всех задач всех типов

    Task getTaskById(Integer taskId);//получение задачи по идентификатору

    Subtask getSubtaskByID(Integer taskId);//получение подзадачи по идентификатору

    Epic getEpicById(Integer taskId); //получение эпика по идентификатору

    void deleteTaskById(Integer taskId); //удаление задачи по идентификатору

    void deleteEpicById(Integer taskId); //удаление эпика по идентификатору

    void deleteSubtaskById(Integer taskId);//удаление подзадачи по идентификатору

    void updateTask(Task task) throws IOException; //метод update задачи

    void updateSubtask(Subtask subtask) throws IOException; //метод update подзадачи

    void updateEpic(Epic epic) throws IOException; //метод update эпика

    void updateEpicStatus(Integer epicID); //метод update статуса Эпика

    List<Task> getHistory();// получение истории просмотра объектов

}
