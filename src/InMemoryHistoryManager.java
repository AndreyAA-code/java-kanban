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
        removeNode(historyNodesListLink.get(id));
    }

    public void linkLast(Task task) {
        if (historyNodesListLink.containsKey(task.taskId)) {
            removeNode(historyNodesListLink.get(task.taskId));
        }
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<Task>(oldTail, task, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
        historyNodesListLink.put(task.taskId, newNode);
    }

    public List<Task> getTasks() {
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

    public void removeNode(Node<Task> node) {
        Node<Task> nodePrev = node.prev;
        Node<Task> nodeNext = node.next;
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = nodeNext;
            nodeNext.prev = null;
        } else if (node == tail) {
            tail = nodePrev;
            nodePrev.next = null;
        } else {
            nodePrev.next = nodeNext;
            nodeNext.prev = nodePrev;
        }
    }

    @Override
    public void removeAll() {
        historyListForPrint.clear();
        head = null;
        tail = null;
        historyNodesListLink.clear();
    }

}

