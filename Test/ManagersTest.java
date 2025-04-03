import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultManager() {
        TaskManager manager = Managers.getDefault();
        assertEquals(manager, new InMemoryTaskManager(), "Не равно");
    }

    @Test
    void getDefaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertEquals(historyManager, new ArrayList<Task>(), "Не равно");
    }
}