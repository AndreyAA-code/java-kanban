import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;

public class Task {

    protected String taskName;
    protected String taskDescription;
    protected Integer taskId;
    protected TaskStatus taskStatus;
    protected long duration;
    protected Instant startTime;


    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Task(String taskName, String taskDescription, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        //this.taskId = taskId;
        this.taskStatus = taskStatus;
    }

    public Task(String taskName, String taskDescription, Integer taskId, TaskStatus taskStatus) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskId = taskId;
        this.taskStatus = taskStatus;
    }

    public Task(String taskName, String taskDescription, TaskStatus taskStatus, long duration, Instant startTime) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = taskStatus;
        this.duration = duration;
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime(Duration duration, LocalDateTime startTime) {
        return startTime.plus(duration);
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
                '}';
    }

}
