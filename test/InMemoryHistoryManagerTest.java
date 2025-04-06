import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class InMemoryHistoryManagerTest {

    @Test
    void add() {

        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task1 = new Task("taskname1", "taskdescr1", TaskStatus.NEW);
        manager.addTask(task1);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void ifSameIdTaskremovedFromListWhileAdd() {

        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname1", "taskdescr1", TaskStatus.NEW);
        manager.addTask(task1);
        manager.getTaskById(1); // получить данные по задаче по номеру ID
        manager.getTaskById(1);
        manager.getHistory();
        List<Task> history = manager.getHistory();
        System.out.println(history);
        assertEquals(1, history.size(), "Объект не один");
    }

    @Test
    void ifHistoryNullwhileDeleteAllTasks() {

        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname1", "taskdescr1", TaskStatus.NEW);
        manager.addTask(task1);
        Task task2 = new Task("taskname2", "taskdescr2", TaskStatus.NEW);
        manager.addTask(task2);
        manager.getTaskById(1); // получить данные по задаче по номеру ID
        manager.getTaskById(2);
        manager.getHistory();
        manager.deleteAllTasks();
        List<Task> history = manager.getHistory();
        System.out.println(history);
        assertEquals(0, history.size(), "List не пустой");
    }
}