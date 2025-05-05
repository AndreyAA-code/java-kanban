import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {

    @Override
    public String toString() {
        return "InMemoryTaskManagerTest{}";
    }

    @Test
    void taskIntersectionCheckFirstInsideSecond() {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(5),
                LocalDateTime.of(2025, 05, 1, 14, 25));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 20));


        assertThrows(TaskIntersectionException.class, () -> {
            manager.addTask(task2);
        }, "Логика работает неправильно");
    }

    @Test
    void taskIntersectionCheckFirstOutsideSecond () {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(30),
                LocalDateTime.of(2025, 05, 1, 14, 15));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 20));


        assertThrows(TaskIntersectionException.class, () -> {
            manager.addTask(task2);
        }, "Логика работает неправильно");
    }
    @Test
    void taskIntersectionCheckFirstStartBeforeSecondStartFirstEndBeforeSecondEnd () {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 15));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 20));


        assertThrows(TaskIntersectionException.class, () -> {
            manager.addTask(task2);
        }, "Логика работает неправильно");
    }
    @Test
    void taskIntersectionCheckFirstStartAfterSecondStartFirstEndAfterSecondEnd  () {
        TaskManager manager = Managers.getDefault();
        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 25));
        manager.addTask(task1);
        Task task2 = new Task("taskname 2", "taskdescr2", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025, 05, 1, 14, 20));


        assertThrows(TaskIntersectionException.class, () -> {
            manager.addTask(task2);
        }, "Логика работает неправильно");
    }
}