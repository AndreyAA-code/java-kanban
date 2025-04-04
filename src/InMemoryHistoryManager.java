import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    public Node head;
    public Node tail;
    private List<Task> historyListForPrint; // для отдачи истории просмотров
    private Map<Integer, Node<Task>> nodesListLink = new HashMap<>();//для быстрого поиска нода для удаления


    @Override
    public List<Task> getHistory() {

        return historyListForPrint;
    }

    @Override
    public void add(Task task) {
        linkLast(task);
    }

    @Override
    public void remove(int id) {

    }

    public void linkLast(Task task) {


        final Node<Task> oldTail = tail;
      //  System.out.println("oldTail: " + oldTail);
        final Node<Task> newNode = new Node(task, tail,null);
       // System.out.println("newNode: " + newNode);
        tail = newNode;
        System.out.println("tail: " + newNode);
        nodesListLink.put(task.taskId, newNode);
     //   System.out.println("nodesListLink: " + nodesListLink);
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
    }


    public void getTasks() {

    }


}
