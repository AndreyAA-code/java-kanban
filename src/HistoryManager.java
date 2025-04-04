import java.util.List;


public interface HistoryManager {

    public List<Task> getHistory(); // возвращает последние просмотренные задачи

    void addHistoryTaskToList(Task task); //добавляет задачу в лист истории при ее просмотре
}

