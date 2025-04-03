import java.util.List;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

   // private Integer maxHistoryNumberlist = 10; //не нужно в спринте 6
   // private Integer historyNumberlist = 0; //не нужно в спринте 6
    private List<Task> historyList = new ArrayList<>();

    @Override
    public List<Task> getHistory() {
        for (int i = 0; i < historyList.size(); i++) { //хотя может надо просто вернуть ArrayList, а распечатывать его потом
            System.out.println("history" + i + ".   " + historyList.get(i));
        }
        return historyList;
    }

    @Override
    public void add(Task task) {
       // if (historyList.size() >= maxHistoryNumberlist) { //не нужно в спринте 6
         //   historyList.remove(0); //не нужно в спринте 6
       // }
        historyList.add(task);
       // historyNumberlist++; //не нужно в спринте 6
    }

    @Override
    public void remove (int id) {

    }
}
