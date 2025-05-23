package tasks;

public enum TaskType {
    TASK,
    SUBTASK,
    EPIC;

    public static Object valueOf() {
        return TaskType.valueOf(TaskType.TASK.name());
    }
}
