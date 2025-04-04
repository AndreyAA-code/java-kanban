import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {



    public Node head;
    public Node tail;
    public List<Task> historyListForPrint; // для отдачи истории просмотров

    public static Map<Integer, Node<Task>> getNodesListLink() {
        return nodesListLink;
    }

    public static Map<Integer, Node<Task>> nodesListLink; //для быстрого поиска нода для удаления

    public InMemoryHistoryManager() {
        nodesListLink = new HashMap<>();
    }
    @Override
    public List<Task> getHistory() {
        historyListForPrint = new LinkedList<>();
        Node<Task> currentNode = head;
        while (currentNode != null) {
            historyListForPrint.add(currentNode.data);
            currentNode = currentNode.next;
        }
        for (Task task : historyListForPrint){
            System.out.println(task);
        }
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
     //   System.out.println("oldTail: " + oldTail);
        final Node<Task> newNode = new Node<Task>(oldTail, task,null);
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
       nodesListLink.put(task.taskId, newNode);
        System.out.println("task.taskId: " + task.taskId+ "  node.prev: " + newNode.prev +"  node.data: "
                + newNode.data +"  node.next: " + newNode.next );
        System.out.println("nodesListLink: " + nodesListLink);

    }



}
