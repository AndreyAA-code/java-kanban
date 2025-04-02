import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    private Integer maxHistoryNumberlist = 10;
    private Integer historyNumberlist = 0;
    private List<Task> historyList = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        for (int i = 0; i < historyList.size(); i++) { //хотя может надо просто вернуть ArrayList, а распечатывать его потом
            System.out.println("history" + i + ".   " + historyList.get(i));
        }
        return historyList;
    }

    @Override
    public void addHistoryTaskToList(Task task) {
        System.out.println(task);
        if (historyList.size() >= maxHistoryNumberlist) {
            historyList.remove(0);
        }
        historyList.add(task);
        historyNumberlist++;
    }
}
