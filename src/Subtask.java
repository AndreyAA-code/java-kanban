import java.time.Duration;
import java.time.LocalDateTime;

public class Subtask extends Task {

    private Integer epicId;

    protected Subtask(String taskName, String taskDescription, Integer epicId, TaskStatus taskStatus, Duration duration, LocalDateTime startTime) {
        super(taskName, taskDescription, taskStatus, duration, startTime);
        this.epicId = epicId;
    }

    protected Subtask(String taskName, String taskDescription, Integer taskId, Integer epicId, TaskStatus taskStatus,Duration duration, LocalDateTime startTime) {
        super(taskName, taskDescription, taskStatus);
        this.epicId = epicId;
        this.taskId = taskId;
        this.duration = duration;
        this.startTime = startTime;
    }

    public Integer getEpicId() {
        return epicId;
    }


    @Override
    public String toString() {
        return "Subtask{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskId=" + taskId +
                ", taskStatus=" + taskStatus +
                ", epicId=" + epicId +
                '}';
    }

    public String toStringFileSubtask() {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                taskId, TaskType.SUBTASK, taskName, taskStatus, taskDescription,epicId,"", duration.toMinutes(), startTime,"");

    }
}