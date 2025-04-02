public class Task {

    protected String taskName;
    protected String taskDescription;
    protected Integer taskId;
    protected TaskStatus taskStatus;

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

    @Override
    public String toString() {
        return "Task{" +
                "taskName='" + taskName + '\'' +
                ", taskDescription='" + taskDescription + '\'' +
                ", taskId=" + taskId +
                ", taskStatus=" + taskStatus +
                '}';
    }
}
