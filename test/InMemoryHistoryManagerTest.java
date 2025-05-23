import managers.HistoryManager;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.Test;
import tasks.Task;
import tasks.TaskStatus;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

class InMemoryHistoryManagerTest extends TaskManagerTest {

    @Test
    void add() {

        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025,05,1,14,25));
        manager.addTask(task1);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void ifSameIdTaskremovedFromListWhileAdd() {

        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025,05,1,14,25));
        manager.addTask(task1);
        manager.getTaskById(1); // получить данные по задаче по номеру ID
        manager.getTaskById(1);
        manager.getHistory();
        List<Task> history = manager.getHistory();
        System.out.println(history);
        assertEquals(1, history.size(), "Объект не один");
    }

    @Test
    void addAndRemoveTaskFromMiddleOflist() {

        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 25));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(5),
                LocalDateTime.of(2025, 05, 01, 14, 40));
        Task task3 = new Task("taskname 3", "taskdescr3", TaskStatus.NEW,Duration.ofMinutes(10),
                LocalDateTime.of(2025,05,01,14,46));
        manager.addTask(task2);
        manager.addTask(task3);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);
        manager.getHistory();
        manager.deleteTaskById(2);
        System.out.println(manager.getPrioritizedTasks());

        List<Task> history = manager.getHistory();
        assertEquals(2, history.size(), "List не пустой");
    }


    @Test
    void addAndRemoveTaskFromEndOflist() {

        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();

        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 25));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(5),
                LocalDateTime.of(2025, 05, 01, 14, 40));
        Task task3 = new Task("taskname 3", "taskdescr3", TaskStatus.NEW,Duration.ofMinutes(10),
                LocalDateTime.of(2025,05,01,14,46));
        manager.addTask(task2);
        manager.addTask(task3);
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getTaskById(3);
        manager.getHistory();
        manager.deleteTaskById(3);
        System.out.println(manager.getPrioritizedTasks());

        List<Task> history = manager.getHistory();
        assertEquals(2, history.size(), "List не пустой");
    }


}