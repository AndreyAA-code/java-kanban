import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.nio.file.Path;


public class FileBackedTaskManager extends InMemoryTaskManager {

    private Path file = Paths.get("kanbanSave.csv");

    public FileBackedTaskManager() {
        super();
        this.file = file;
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
    public void deleteTaskById(Integer taskId) { //удаление задачи по идентификатору
        super.deleteTaskById(taskId);
        save();
    }

    @Override
    public void deleteEpicById(Integer taskId) { //удаление эпика по идентификатору
        super.deleteEpicById(taskId);
        save();
    }

    private void save() {

        try (Writer fileWriter = new FileWriter(file.toString())) {
            fileWriter.write("id,type,name,status,description,epic\n");

            for (Integer task : tasks.keySet()) {
                String string = toString(tasks.get(task));

                fileWriter.write(string + "\n");
            }

            for (Integer epic : epics.keySet()) {
                String string = toString(epics.get(epic));

                fileWriter.write(string + "\n");
            }

            for (Integer subtask : subtasks.keySet()) {
                String string = toString(subtasks.get(subtask));

                fileWriter.write(string + "\n");
            }
            fileWriter.write("Счетчик заданий: " + taskId);
        } catch (IOException exception) {
            throw new SaveRestoreException("Error. can't write file");
        }

    }

    private String toString(Task task) {
        String string = task.toString();
        StringBuilder sb = new StringBuilder(string);

        sb = sb.replace(sb.indexOf("{"), sb.indexOf("{") + 1, ",");
        sb = sb.delete(sb.indexOf("taskName="), (sb.indexOf("taskName=") + 9));
        sb = sb.delete(sb.indexOf("taskDescription="), (sb.indexOf("taskDescription=") + 16));
        sb = sb.delete(sb.indexOf("taskId="), (sb.indexOf("taskId=") + 7));
        sb = sb.delete(sb.indexOf("taskStatus="), (sb.indexOf("taskStatus=") + 11));
        while (sb.indexOf("epicId=") != -1) {
            sb = sb.delete(sb.indexOf("epicId="), (sb.indexOf("epicId=") + 7));
        }

        while (sb.indexOf("'") != -1) {
            sb = sb.replace(sb.indexOf("'"), sb.indexOf("'") + 1, "");
        }
        while (sb.indexOf("}") != -1) {
            sb = sb.replace(sb.indexOf("}"), sb.indexOf("}") + 1, "");
        }
        String[] stringArray = sb.toString().split(",");
        String sbResult = String.join(",", stringArray[3].trim(),
                stringArray[0].toUpperCase(), stringArray[1], stringArray[4].toUpperCase().trim(),
                stringArray[2].trim());
        if (stringArray.length == 6 && stringArray[0].equals("Subtask")) {
            sbResult = sbResult + "," + stringArray[5].trim();
        }

        return sbResult.toString();
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

        if (value.contains("id,type,name,status,description,epic")) {
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
            Task task = new Task(line[2], line[4], TaskStatus.valueOf(line[3]));
            taskId = Integer.parseInt(line[0]) - 1;
            super.addTask(task);

        } else if (taskType.equals(TaskType.SUBTASK)) {
            Subtask subtask = new Subtask(line[2], line[4], Integer.parseInt(line[5]), TaskStatus.valueOf(line[3]));
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
