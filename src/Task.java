import java.time.Duration;
import java.time.LocalDateTime;

public class Task {

    protected String taskName;
    protected String taskDescription;
    protected Integer taskId;
    protected TaskStatus taskStatus;
    protected Duration duration;
    protected LocalDateTime startTime;


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Task(String taskName, String taskDescription, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
    }

    public Task(String taskName, String taskDescription, Integer taskId, TaskStatus taskStatus,Duration duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Task(String taskName, String taskDescription, TaskStatus taskStatus, Duration duration, LocalDateTime startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.duration = duration;
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return startTime.plus(duration);
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }


    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskId=" + taskId +
                ", taskStatus=" + taskStatus +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + getEndTime() +
                '}';
    }

    public String toStringFile() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                taskId, TaskType.TASK, taskName, taskStatus, taskDescription, "", "", duration.toMinutes(), startTime, getEndTime());
    }

}
