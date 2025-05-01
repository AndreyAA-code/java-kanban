import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Epic extends Task {
    private List<Integer> epicSubtasks;
    private Duration duration;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Epic(String taskName, String taskDescription, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        epicSubtasks = new ArrayList<>();
        this.duration = Duration.of(0,ChronoUnit.MINUTES);
        this.startTime = LocalDateTime.now();
        this.endTime = this.startTime;
    }

    public Epic(String taskName, String taskDescription, Integer taskId, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        this.taskId = taskId;
        this.duration = Duration.of(0,ChronoUnit.MINUTES);
        this.startTime = LocalDateTime.now();
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
    public String toStringFileEpic() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                taskId, TaskType.EPIC, taskName, taskStatus, taskDescription,"",epicSubtasks, duration.toMinutes(), startTime,endTime);

    }
}