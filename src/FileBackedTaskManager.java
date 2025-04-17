import java.io.*;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class FileBackedTaskManager extends InMemoryTaskManager {

    private Path file;

    public FileBackedTaskManager(Path file) throws IOException {
        super();
        this.file = file;
        restore();
    }


    @Override
    public Integer addTask(Task task) throws IOException {  //метод добавления задачи
        super.addTask(task);
        save();
        return task.taskId;
    }

    @Override
    public void addEpic(Epic epic) throws IOException { //метод добавления Эпика
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) throws IOException { //метод добавления подзадачи
        super.addSubtask(subtask);
        save();
    }

    @Override
    public void updateTask(Task task) throws IOException { //метод update задачи
        super.updateTask(task);
        save();
    }  //метод update задачи

    @Override
    public void updateSubtask(Subtask subtask) throws IOException { //метод update подзадачи
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void updateEpic(Epic epic) throws IOException {  //метод update эпика
        super.updateEpic(epic);
        save();
    }

    public void save() throws IOException {

        Writer fileWriter = null;
        try {
            fileWriter = new FileWriter(file.toString());
        } catch (IOException exception) {
            throw new InputException("Внимание");
        }
        fileWriter.write("id,type,name,status,description,epic\n");

        for (Integer task : tasks.keySet()) {
            String string = toString(tasks.get(task));

            fileWriter.write(string + "\n");
            System.out.println(string);
        }

        for (Integer epic : epics.keySet()) {
            String string = toString(epics.get(epic));

            fileWriter.write(string + "\n");
            System.out.println(string);
        }

        for (Integer subtask : subtasks.keySet()) {
            String string = toString(subtasks.get(subtask));

            fileWriter.write(string + "\n");
            System.out.println(string);
        }


        fileWriter.close();
    }

    public String toString(Task task) {
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

    public void restore() throws IOException {
        if (!Files.exists(file)) {
            return;
        }
        Reader fileReader = new FileReader(file.toString());
        BufferedReader br = new BufferedReader(fileReader);

        while (br.ready()) {

            String line = br.readLine();
            fromString(line);

        }

        fileReader.close();
    }


    public void fromString(String value) throws IOException {

        if (value.contains("id,type,name,status,description,epic")) {
            return;
        }

        String[] line = value.split(",");
        TaskType taskType = TaskType.valueOf(line[1]);

        if (taskType.equals(TaskType.TASK)) {
           // System.out.println("task");

           // String result = String.join(",", line[2], line[4], "TaskStatus." + line[3]);

            Task task = new Task(line[2], line[4], TaskStatus.valueOf(line[3]));
            tasks.put(Integer.parseInt(line[0]), task);
            task.setTaskId(Integer.parseInt(line[0]));


        } else if (taskType.equals(TaskType.SUBTASK)) {
            System.out.println("subtask");
        } else if (taskType.equals(TaskType.EPIC)) {
            System.out.println("epic");
        } else {
            System.out.println("error");
        }


    }

}
