import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected Integer taskId = 0;//счетчик идентификатор сквозной для всех типов задач
    protected final HashMap<Integer, Task> tasks;
    protected final HashMap<Integer, Subtask> subtasks;
    protected final HashMap<Integer, Epic> epics;
    protected HistoryManager historyManager;

    protected Set<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(task -> task.startTime));

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
    public void addTask(Task task) {  //метод добавления задачи
        taskIdCount();
        task.setTaskId(taskId);
        tasks.put(taskId, task);
        addPrioritizedTask(task);
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
        addPrioritizedTask(subtask);
        Epic epic = epics.get(subtask.getEpicId());
        epic.getEpicSubtasks().add(subtask.taskId);
        updateEpicStatus(subtask.getEpicId());
        this.updateEpicTimes(subtask.getEpicId());
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
        prioritizedTasks.clear();
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
            prioritizedTasks.removeIf(task -> task.taskId == taskId);
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
            prioritizedTasks.removeIf(task -> task.taskId == taskId);
            if (!historyManager.equals(null)) {
                historyManager.remove(taskId);
            }
            updateEpicStatus(subtask.getEpicId());
            this.updateEpicTimes(subtask.getEpicId());
        } else {
            System.out.println("Такой задачи нет");
        }
    }

    @Override
    public void updateTask(Task task) { //метод update задачи
        tasks.put(task.getTaskId(), task);
        prioritizedTasks.remove(taskId);
        prioritizedTasks.add(task);
    }

    @Override
    public void updateSubtask(Subtask subtask) { //метод update подзадачи
        subtasks.put(subtask.getTaskId(), subtask);
        prioritizedTasks.add(subtask);
        updateEpicStatus(subtask.getEpicId());
        this.updateEpicTimes(subtask.getEpicId());
    }

    @Override
    public void updateEpic(Epic epic) {  //метод update эпика
        Epic oldEpic = epics.get(epic.getTaskId());
        List<Integer> epicSubtasks1 = oldEpic.getEpicSubtasks();
        epics.put(epic.getTaskId(), epic);
        epic.setEpicSubtasks(epicSubtasks1);
        updateEpicStatus(epic.getTaskId());
        this.updateEpicTimes(epic.getTaskId());
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
    public void updateEpicTimes(Integer epicId) {
        Epic epic = epics.get(epicId);
        LocalDateTime epicStartTime = epic.getStartTime();
        LocalDateTime epicEndTime = epic.getEndTime();
        Duration epicDuration = Duration.ofMinutes(0);
        for (Integer subtaskId : epic.getEpicSubtasks()) {
            Subtask subtask = subtasks.get(subtaskId);
            if (subtask.startTime.isBefore(epicStartTime)) {
                epicStartTime = subtask.startTime;
            }
            if (subtask.getEndTime().isAfter(epicEndTime)) {
                epicEndTime = subtask.getEndTime();
            }
            epicDuration = subtask.duration.plus(epicDuration);

        }
        epic.setStartTime(epicStartTime);
        epic.setEndTime(epicEndTime);
        epic.setDuration(epicDuration);
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void addPrioritizedTask(Task task) { //добавление задачи в TreeSet для хранения сортировки по времени начала задачи
        if (task==null) {
            return;
        }
        if (intersection(task)) {
            throw new TaskIntersectionException("Ошибка. Задача пересекается по времени выполнения с существующей!");
        }
        prioritizedTasks.add(task);
    }

    private boolean intersection(Task task) { //проверка пересечения времен выполнения задач
        LocalDateTime startTime = task.getStartTime();
        LocalDateTime endTime = task.getEndTime();
        for (Task taskTime : prioritizedTasks) {
            boolean intersectionInside = startTime.isAfter(taskTime.getStartTime()) && endTime.isBefore(taskTime.getEndTime());
            boolean intersectionOutside = startTime.isBefore(taskTime.getStartTime()) && endTime.isAfter(taskTime.getEndTime());
            boolean intersectionBefore = startTime.isBefore(taskTime.getStartTime()) && endTime.isAfter(taskTime.getStartTime());
            boolean intersectionAfter = startTime.isBefore(taskTime.getEndTime()) && endTime.isAfter(taskTime.getEndTime());
            if (intersectionAfter || intersectionBefore || intersectionOutside || intersectionInside) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Task> getPrioritizedTasks() {
        System.out.println(List.copyOf(prioritizedTasks));
        return List.copyOf(prioritizedTasks);
    }
}
