import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> epicSubtasks;
    private Duration duration;
    private LocalDateTime startTime;

    public Epic(String taskName, String taskDescription, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        epicSubtasks = new ArrayList<>();
        this.duration = duration;
        this.startTime = startTime;
      //  this.endTime = LocalDateTime.of(3000,1,1,1,0);
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
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", epicSubtasks=" + epicSubtasks +
                '}';
    }
}