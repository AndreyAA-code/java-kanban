import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class TaskManagerTest<T extends TaskManager> {
    protected T manager;
    Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
            LocalDateTime.of(2025, 05, 1, 14, 25));

    @Test
    void addTask() {

        manager.addTask(task1);

        List<Task> temp = manager.getTasks();
        Task task1test = temp.get(0);

        assertNotNull(task1, "Задача не найдена.");
        assertEquals(task1, task1test, "Задачи не совпадают.");
        assertNotNull(temp, "Задачи не возвращаются.");
        assertEquals(1, temp.size(), "Неверное количество задач.");
        assertEquals(task1, temp.getFirst(), "Задачи не совпадают.");
    }
}