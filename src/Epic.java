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
        this.startTime = LocalDateTime.of(3000, 1, 1, 0, 0);
        this.endTime = LocalDateTime.of(1000, 1, 1, 0, 0);
    }

    public Epic(String taskName, String taskDescription, Integer taskId, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        this.taskId = taskId;
        this.duration = Duration.of(0,ChronoUnit.MINUTES);
        this.startTime = LocalDateTime.now();
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public List<Integer> getEpicSubtasks() {
        return epicSubtasks;
    }

    public void setEpicSubtasks(List<Integer> epicSubtasks) {
        this.epicSubtasks = epicSubtasks;

    }
    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    @Override
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
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
                ", endTime=" + endTime +
                '}';
    }

    public String toStringFileEpic() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                taskId, TaskType.EPIC, taskName, taskStatus, taskDescription,"",epicSubtasks, duration.toMinutes(), startTime,endTime);

    }
}