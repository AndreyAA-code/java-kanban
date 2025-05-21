import java.io.*;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.nio.file.Path;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private Path file = Paths.get("kanbanSave.csv");

    public FileBackedTaskManager() {
        super();
        //  this.file = file;
        //loadFromFile(file);
    }


    @Override
    public void addTask(Task task) {  //метод добавления задачи
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) { //метод добавления Эпика
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) { //метод добавления подзадачи
        super.addSubtask(subtask);
        save();
    }

    @Override  //метод update задачи
    public void updateTask(Task task) { //метод update задачи
        super.updateTask(task);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) { //метод update подзадачи
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {  //метод update эпика
        super.updateEpic(epic);
        save();
    }

    @Override
    public void deleteAllTasks() {  //удаление всех задач всех типов
        super.deleteAllTasks();
        save();
    }

    @Override
    public Task deleteTaskById(Integer taskId) { //удаление задачи по идентификатору
        super.deleteTaskById(taskId);
        save();
        return tasks.get(taskId);
    }

    @Override
    public void deleteEpicById(Integer taskId) { //удаление эпика по идентификатору
        super.deleteEpicById(taskId);
        save();
    }


    private void save() {

        try (Writer fileWriter = new FileWriter(file.toString())) {
            fileWriter.write("id,type,name,status,description,epic,subtasks,duration,startTime,endTime\n");

            for (Integer task : tasks.keySet()) {
                String string = tasks.get(task).toStringFile();

                fileWriter.write(string + "\n");
            }

            for (Integer epic : epics.keySet()) {
                String string = epics.get(epic).toStringFileEpic();

                fileWriter.write(string + "\n");
            }

            for (Integer subtask : subtasks.keySet()) {
                String string = subtasks.get(subtask).toStringFileSubtask();

                fileWriter.write(string + "\n");
            }
            fileWriter.write("Счетчик заданий: " + taskId);
        } catch (IOException exception) {
            throw new SaveRestoreException("Error. can't write file");
        }

    }

    static FileBackedTaskManager loadFromFile(Path file) {
        FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager();

        try (Reader fileReader = new FileReader(file.toString())) {
            BufferedReader br = new BufferedReader(fileReader);

            while (br.ready()) {

                String line = br.readLine();
                fileBackedTaskManager.fromString(line);

            }
        } catch (IOException exception) {
            throw new SaveRestoreException("Error. can't read file");
        }
        return fileBackedTaskManager;
    }

    private void fromString(String value) {

        if (value.contains("id,type,name,status,description,epic,subtasks,duration,startTime,endTime")) {
            return;
        }
        if (value.contains("Счетчик заданий")) {
            String string = value.replace("Счетчик заданий: ", "");
            taskId = Integer.parseInt(string);
            return;
        }

        String[] line = value.split(",");

        TaskType taskType = TaskType.valueOf(line[1]);

        if (taskType.equals(TaskType.TASK)) {
            Task task = new Task(line[2], line[4], TaskStatus.valueOf(line[3]), Duration.ofMinutes(Long.parseLong(line[7])), LocalDateTime.parse(line[8]));
            taskId = Integer.parseInt(line[0]) - 1;
            super.addTask(task);
        } else if (taskType.equals(TaskType.SUBTASK)) {
            Subtask subtask = new Subtask(line[2], line[4], Integer.parseInt(line[5]), TaskStatus.valueOf(line[3]), Duration.ofMinutes(Long.parseLong(line[7])), LocalDateTime.parse(line[8]));
            taskId = Integer.parseInt(line[0]) - 1;
            super.addSubtask(subtask);

        } else if (taskType.equals(TaskType.EPIC)) {
            Epic epic = new Epic(line[2], line[4], TaskStatus.valueOf(line[3]));
            taskId = Integer.parseInt(line[0]) - 1;
            super.addEpic(epic);

        } else {
            System.out.println("Данные в файле повреждены. Загрузка невозможна");
        }

    }
}
