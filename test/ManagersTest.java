import managers.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {

    @Test
    void getDefaultManager() {
        TaskManager manager =  Managers.getDefault();
        System.out.println(manager);
        manager.getClass();
        assertEquals(manager.getClass(), InMemoryTaskManager.class, "Не равно");
    }


    @Test
    void getDefaultHistory() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertEquals(historyManager.getClass(), InMemoryHistoryManager.class, "Не равно");
    }
}