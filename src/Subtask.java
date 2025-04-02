public class Subtask extends Task {
    private Integer epicId;

    protected Subtask(String taskName, String taskDescription, Integer epicId, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        this.epicId = epicId;
    }

    protected Subtask(String taskName, String taskDescription, Integer taskId, Integer epicId, TaskStatus taskStatus) {
        super(taskName, taskDescription, taskStatus);
        this.epicId = epicId;
        this.taskId = taskId;
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
}