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

        Writer fileWriter = new FileWriter(file.toString());
        fileWriter.write("id,type,name,status,description,epic\n");

        for (Integer task : tasks.keySet()) {
            String string = toString(tasks.get(task));

            fileWriter.write(string + "\n");
            System.out.println(string);
        }

        fileWriter.close();
    }

    public String toString (Task task) {
       String string = task.toString();
       StringBuilder sb = new StringBuilder(string);
       sb = sb.replace(sb.indexOf("{"), sb.indexOf("{") + 1, ",");

       sb = sb.delete(sb.indexOf("taskName="), (sb.indexOf("TaskName=") + 1));
       while (sb.indexOf("'")!=-1) {
           sb = sb.replace(sb.indexOf("'"), sb.indexOf("'") + 1, "");

       }

       String[] stringArray = task.toString().split(",");


      //  StringBuilder sb = new StringBuilder(task.toString());


       return sb.toString();
    }

}
