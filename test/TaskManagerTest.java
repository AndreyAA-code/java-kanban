import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Subtask;
import tasks.Task;
import tasks.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest <T extends TaskManager>  {

    @Test
   public void addTask() {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 25));
        manager.addTask(task1);

        List<Task> temp = manager.getTasks();
        Task task1test = temp.get(0);

        assertNotNull(task1, "Задача не найдена.");
        assertEquals(task1, task1test, "Задачи не совпадают.");
        assertNotNull(temp, "Задачи не возвращаются.");
        assertEquals(1, temp.size(), "Неверное количество задач.");
        assertEquals(task1, temp.getFirst(), "Задачи не совпадают.");
    }

    @Test
    void addSubtask() {

        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW, Duration.ofMinutes(60), LocalDateTime.of(2025,05,01,15,10));
        manager.addSubtask(subtask1);

        List<Subtask> testSubtask = manager.getSubtasks();
        Subtask testSubtask1 = testSubtask.get(0);

        assertNotNull(subtask1, "Задача не найдена.");
        assertEquals(subtask1, testSubtask1, "Задачи не совпадают.");
        assertEquals(subtask1.getEpicId(), 1, "ИД эпика не совпадает.");
    }

    @Test
    void addEpic() {

        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2025,05,01,15,10));
        manager.addSubtask(subtask1);

        List<Epic> testEpic = manager.getEpics();
        Epic testEpic1 = testEpic.get(0);

        assertNotNull(epic1, "Задача не найдена.");
        assertEquals(epic1, testEpic1, "Задачи не совпадают.");
    }

    @Test
    void updateEpicStatusAfterSubtasksStausChangedDoneDone() {

        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2025,05,01,15,10));
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 1, TaskStatus.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025,05,01,16,20));
        manager.addSubtask(subtask2);

        manager.updateSubtask(new Subtask("subtaskname2new", "subtaskdescr2new",2, 1, TaskStatus.DONE, Duration.ofMinutes(60),
                LocalDateTime.of(2025,05,01,15,10)));
        manager.updateSubtask(new Subtask("subtaskname1new", "subtaskdescr1new",3, 1, TaskStatus.DONE, Duration.ofMinutes(30),
                LocalDateTime.of(2025,05,01,16,20)));
        List<Epic> testEpic = manager.getEpics();
        Epic testEpic1 = testEpic.get(0);

        assertEquals(TaskStatus.DONE, testEpic1.getTaskStatus(), "Статус не совпадает.");
    }

    @Test
    void updateEpicStatusAfterSubtasksStausChanged() {

        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW);
        manager.addEpic(epic1);

        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW, Duration.ofMinutes(60),
                LocalDateTime.of(2025,05,01,15,10));
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 1, TaskStatus.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025,05,01,16,20));
        manager.addSubtask(subtask2);

        manager.updateSubtask(new Subtask("subtaskname2new", "subtaskdescr2new",2, 1, TaskStatus.DONE, Duration.ofMinutes(60),
                LocalDateTime.of(2025,05,01,15,10)));
        manager.updateSubtask(new Subtask("subtaskname1new", "subtaskdescr1new",3, 1, TaskStatus.IN_PROGRESS, Duration.ofMinutes(30),
                LocalDateTime.of(2025,05,01,16,20)));

        List<Epic> testEpic = manager.getEpics();
        Epic testEpic1 = testEpic.get(0);

        assertEquals(TaskStatus.IN_PROGRESS, testEpic1.getTaskStatus(), "Статус не совпадает.");
    }


}