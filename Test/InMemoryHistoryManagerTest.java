import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class InMemoryHistoryManagerTest {

    @Test
    void addHistoryTaskToList() {

        TaskManager manager = Managers.getDefault();
        HistoryManager historyManager = Managers.getDefaultHistory();
        Task task1 = new Task("taskname1", "taskdescr1", TaskStatus.NEW);
        manager.addTask(task1);

        historyManager.addHistoryTaskToList(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
}