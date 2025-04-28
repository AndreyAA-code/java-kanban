import java.util.List;


public interface HistoryManager {

    List<Task> getHistory(); // возвращает последние просмотренные задачи

    void add(Task task); //добавляет задачу в лист истории при ее просмотре

    void remove(int id); //удаление задачи из просмотра при удалении задачи

    void removeAll(); //удаление всех задач

}

