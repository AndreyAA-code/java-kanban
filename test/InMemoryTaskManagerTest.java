import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {

    @Override
    public String toString() {
        return "InMemoryTaskManagerTest{}";
    }

    @Test
    void addTask() {

        TaskManager taskManager = Managers.getDefault();

        Task task1 = new Task("taskname1", "taskdescr1", TaskStatus.NEW);
        taskManager.addTask(task1);

        List <Task> temp = taskManager.getTasks();
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

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.now());
        manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW);
        manager.addSubtask(subtask1);

        List<Subtask> testSubtask = manager.getSubtasks();
        Subtask testSubtask1 = testSubtask.get(0);

        assertNotNull(subtask1, "Задача не найдена.");
        assertEquals(subtask1, testSubtask1, "Задачи не совпадают.");
    }

    @Test
    void addEpic() {

        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.now());
        manager.addEpic(epic1);

        List<Epic> testEpic = manager.getEpics();
        Epic testEpic1 = testEpic.get(0);

        assertNotNull(epic1, "Задача не найдена.");
        assertEquals(epic1, testEpic1, "Задачи не совпадают.");
    }

    @Test
    void updateEpicStatusAfterSubtasksStausChangedDoneDone() {

        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.now());
        manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW);
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 1, TaskStatus.NEW);
        manager.addSubtask(subtask2);

        manager.updateSubtask(new Subtask("subtaskname2new", "subtaskdescr2new",3, 1, TaskStatus.DONE));
        manager.updateSubtask(new Subtask("subtaskname1new", "subtaskdescr1new",2, 1, TaskStatus.DONE));

        List<Epic> testEpic = manager.getEpics();
        Epic testEpic1 = testEpic.get(0);

        assertEquals(TaskStatus.DONE, testEpic1.taskStatus, "Статус не совпадает.");
    }

    @Test
    void updateEpicStatusAfterSubtasksStausChanged() {

        TaskManager manager = Managers.getDefault();

        Epic epic1 = new Epic("epicname1", "epicdescr1", TaskStatus.NEW, Duration.ofMinutes(30), LocalDateTime.now());
        manager.addEpic(epic1);
        Subtask subtask1 = new Subtask("subtaskname1", "subtaskdescr1", 1, TaskStatus.NEW);
        manager.addSubtask(subtask1);
        Subtask subtask2 = new Subtask("subtaskname2", "subtaskdescr2", 1, TaskStatus.NEW);
        manager.addSubtask(subtask2);

        manager.updateSubtask(new Subtask("subtaskname2new", "subtaskdescr2new",3, 1, TaskStatus.DONE));
        manager.updateSubtask(new Subtask("subtaskname1new", "subtaskdescr1new",2, 1, TaskStatus.IN_PROGRESS));

        List<Epic> testEpic = manager.getEpics();
        Epic testEpic1 = testEpic.get(0);

        assertEquals(TaskStatus.IN_PROGRESS, testEpic1.taskStatus, "Статус не совпадает.");
    }


}