import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    protected Node head = null;
    protected Node tail;

    protected static Map<Integer, Node<Task>> historyNodesListLink; //для быстрого поиска нода для удаления

    public InMemoryHistoryManager() {
        historyNodesListLink = new HashMap<>();
    }

    public static Map<Integer, Node<Task>> getNodesListLink() {
        return historyNodesListLink;
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
       if (historyNodesListLink.get(id)==null){
           return;
       }
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
            oldTail.setNext(newNode);
        }
        historyNodesListLink.put(task.taskId, newNode);
    }

    public static void setHistoryNodesListLink(Map<Integer, Node<Task>> historyNodesListLink) {
        InMemoryHistoryManager.historyNodesListLink = historyNodesListLink;
    }



    public List<Task> getTasks() {
        List<Task> historyListForPrint = new LinkedList<>(); // для отдачи истории просмотров
        Node<Task> node = head;
        while (node != null) {
            historyListForPrint.add(node.getData());
            node = node.getNext();
        }
        for (int i = historyListForPrint.size() - 1; i >= 0; i--) {
            System.out.println("запрос " + (i + 1) + ": " + historyListForPrint.get(i));
        }
        return historyListForPrint;
    }

    public void removeNode(Node<Task> node) {
        Node<Task> nodePrev = node.getPrev();
        var nodeNext = node.getNext();
        if (node == head && node == tail) {
            head = null;
            tail = null;
        } else if (node == head) {
            head = nodeNext;
            nodeNext.setPrev(null);
        } else if (node == tail) {
            tail = nodePrev;
            nodePrev.setNext(null);
        } else {
            nodePrev.setNext(nodeNext);
            nodeNext.setPrev(nodePrev);
        }
    }

    @Override
    public void removeAll() {
        head = null;
        tail = null;
        historyNodesListLink.clear();
    }

}

