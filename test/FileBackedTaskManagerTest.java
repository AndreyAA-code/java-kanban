import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    Path path = Paths.get("test.csv");

    @Test
    void testAddTask() throws IOException {

        TaskManager taskManager = Managers.getDefaultWithSave(path);

        Task task1 = new Task("taskname1", "taskdescr1", TaskStatus.NEW);
        taskManager.addTask(task1);

        List<Task> temp = taskManager.getTasks();
        Task task1test = temp.get(0);

        assertNotNull(task1, "Задача не найдена.");
        assertEquals(task1, task1test, "Задачи не совпадают.");

        assertNotNull(temp, "Задачи не возвращаются.");
        assertEquals(1, temp.size(), "Неверное количество задач.");
        assertEquals(task1, temp.getFirst(), "Задачи не совпадают.");
    }
    @Test
    void tryToSaveInformationsInFile() throws IOException {

        TaskManager taskManager = Managers.getDefaultWithSave(path);
        String saveLine;
        String restoreLine;
        try (Writer fileWriter = new FileWriter(path.toString())) {
            saveLine = "id,type,name,status,description,epic";
            fileWriter.write(saveLine);

        } catch (IOException exception) {
            throw new SaveRestoreException("Error. can't write file");
        }

        try (Reader fileReader = new FileReader(path.toString())) {
            BufferedReader br = new BufferedReader(fileReader);
            restoreLine = br.readLine();


        } catch (IOException exception) {
            throw new SaveRestoreException("Error. can't read file");
        }
        assertEquals(saveLine, restoreLine, "Read != Write");
    }


}