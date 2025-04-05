import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {


    public Node head;
    public Node tail;
    public List<Task> historyListForPrint; // для отдачи истории просмотров

    public static Map<Integer, Node<Task>> getNodesListLink() {
        return historyNodesListLink;
    }

    public static Map<Integer, Node<Task>> historyNodesListLink; //для быстрого поиска нода для удаления

    public InMemoryHistoryManager() {
        historyNodesListLink = new HashMap<>();
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void add(Task task) {
        linkLast(task);
    }

    @Override
    public void remove(int id) {


    }

    public void linkLast(Task task) {
        if (historyNodesListLink.containsKey(task.taskId)){
            removeNode(historyNodesListLink.get(task.taskId));
        }

        final Node<Task> oldTail = tail;
        //   System.out.println("oldTail: " + oldTail);
        final Node<Task> newNode = new Node<Task>(oldTail, task, null);
        //    System.out.println("newNode: " + newNode);
        tail = newNode;
        //   System.out.println("tail: " + newNode);
        if (oldTail == null) {
            head = newNode;
            //        System.out.println("head: " + head);
        } else {
            oldTail.next = newNode;
            //     System.out.println("oldTail.next: " + oldTail.next);
        }
        historyNodesListLink.put(task.taskId, newNode);
        System.out.println("task.taskId: " + task.taskId + "  node.prev: " + newNode.prev + "  node.data: "
                + newNode.data + "  node.next: " + newNode.next);
        System.out.println("nodesListLink: " + historyNodesListLink);

    }
public List<Task> getTasks(){
        historyListForPrint = new LinkedList<>();
        Node<Task> node = head;
        while (node != null) {
            historyListForPrint.add(node.data);
            node = node.next;
        }
        for (int i = historyListForPrint.size() - 1; i >= 0; i--) {
            System.out.println("запрос " + (i + 1) + ": " + historyListForPrint.get(i));
        }
        return historyListForPrint;
    }

    public void removeNode (Node<Task> node) {

    }



}
