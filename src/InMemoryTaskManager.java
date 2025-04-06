
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements TaskManager {

    private Integer taskId = 0;//счетчик идентификатор сквозной для всех типов задач
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        historyManager = Managers.getDefaultHistory();
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public void taskIdCount() { //метод - счетчик идентификатора
        taskId++;
    }

    @Override
    public Integer addTask(Task task) {  //метод добавления задачи
        taskIdCount();
        task.setTaskId(taskId);
        tasks.put(taskId, task);
        return taskId;
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void addSubtask(Subtask subtask) { //метод добавления подзадачи
        taskIdCount();
        subtask.setTaskId(taskId);
        subtasks.put(taskId, subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getEpicSubtasks().add(subtask.taskId);
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public List<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void addEpic(Epic epic) { //метод добавления Эпика
        taskIdCount();
        epic.setTaskId(taskId);
        epics.put(taskId, epic);
    }

    @Override
    public void printAllTasks() { //печать всех задач//
        for (Integer task : tasks.keySet()) {
            if (task == null) {
                break;
            } else {
                System.out.println("Задача " + task + ". " + tasks.get(task));
            }
        }
        for (Integer epic : epics.keySet()) {
            if (epic == null) {
                break;
            } else {
                System.out.println("Задача " + epic + ". " + epics.get(epic));
            }
        }
        for (Integer subtask : subtasks.keySet()) {
            if (subtask == null) {
                break;
            } else {
                System.out.println("Задача " + subtask + ". " + subtasks.get(subtask));
            }
        }
    }

    @Override
    public void deleteAllTasks() {  //удаление всех задач всех типов
        tasks.clear();
        subtasks.clear();
        epics.clear();
        historyManager.removeAll();
        taskId = 1; //сброс счетчика идентификатора
    }

    @Override
    public Task getTaskById(Integer taskId) { //получение задачи по идентификатору
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }

    @Override
    public Subtask getSubtaskByID(Integer taskId) {  //получение подзадачи по идентификатору
        historyManager.add(subtasks.get(taskId));
        return subtasks.get(taskId);
    }

    @Override
    public Epic getEpicById(Integer taskId) { //получение эпика по идентификатору
        historyManager.add(epics.get(taskId));
        return epics.get(taskId);
    }

    @Override
    public void deleteTaskById(Integer taskId) { //удаление задачи по идентификатору
        if (tasks.get(taskId) != null) {
            tasks.remove(taskId);
            historyManager.remove(taskId);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void deleteEpicById(Integer taskId) { //удаление эпика по идентификатору
        if (epics.get(taskId) != null) {
            Epic epic = epics.get(taskId);
            for (Integer subtask : epic.getEpicSubtasks()) {
                subtasks.remove(subtask);
                historyManager.remove(subtask);
            }
            epics.remove(taskId);
            historyManager.remove(taskId);
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void deleteSubtaskById(Integer taskId) { //удаление подзадачи по идентификатору
        if (subtasks.get(taskId) != null) {
            Subtask subtask = subtasks.get(taskId);
            Epic epic = epics.get(subtask.getEpicId());
            List<Integer> epicSubtasks = epic.getEpicSubtasks();
            epicSubtasks.remove(taskId);
            subtasks.remove(taskId);
            historyManager.remove(taskId);
            updateEpicStatus(subtask.getEpicId());
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void updateTask(Task task) { //метод update задачи
        tasks.put(task.getTaskId(), task);
    }  //метод update задачи

    @Override
    public void updateSubtask(Subtask subtask) { //метод update подзадачи
        subtasks.put(subtask.getTaskId(), subtask);
        updateEpicStatus(subtask.getEpicId());
    }

    @Override
    public void updateEpic(Epic epic) {  //метод update эпика
        Epic oldEpic = epics.get(epic.getTaskId());
        List<Integer> epicSubtasks1 = oldEpic.getEpicSubtasks();
        epics.put(epic.getTaskId(), epic);
        epic.setEpicSubtasks(epicSubtasks1);
        updateEpicStatus(epic.getTaskId());
    }

    @Override
    public void updateEpicStatus(Integer epicID) {   //метод update статуса Эпика
        Epic epic = epics.get(epicID);

        boolean isStatusNew = true;
        boolean isStatusDone = true;

        for (Integer subtaskId : epic.getEpicSubtasks()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.taskStatus != TaskStatus.NEW) {
                isStatusNew = false;
            }
            if (subtask.taskStatus != TaskStatus.DONE) {
                isStatusDone = false;
            }
        }
        if (isStatusNew) {
            epic.taskStatus = TaskStatus.NEW;
        } else if (isStatusDone) {
            epic.taskStatus = TaskStatus.DONE;
        } else {
            epic.taskStatus = TaskStatus.IN_PROGRESS;
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

}
