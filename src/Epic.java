import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> epicSubtasks;

    public Epic(String taskName, String taskDescription, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        epicSubtasks = new ArrayList<>();

    }

    public Epic(String taskName, String taskDescription, Integer taskId, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        this.taskId = taskId;
    }

    public List<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void setEpicSubtasks(List<Integer> epicSubtasks) {
        this.epicSubtasks = epicSubtasks;
    }

    @Override
    public String toString() {
        return "Epic{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskId=" + taskId +
                ", taskStatus=" + taskStatus +
                ", epicSubtasks=" + epicSubtasks +
                '}';
    }
}