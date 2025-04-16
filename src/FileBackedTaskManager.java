import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.File;

public class FileBackedTaskManager extends InMemoryTaskManager{

    private Path file;

    public FileBackedTaskManager(Path file) {
        super();
        this.file=file;
    }

    @Override
    public Integer addTask(Task task) throws IOException {  //метод добавления задачи
       super.addTask(task);
       save();
       return task.taskId;
    }

    public void save() throws IOException {
        System.out.println("hhh");
        System.out.println(getTasks());
        Writer fileWriter = new FileWriter(file.toString(),true);
        fileWriter.write("hello");
        fileWriter.write(getTasks().toString());
        fileWriter.close();
    }



}
