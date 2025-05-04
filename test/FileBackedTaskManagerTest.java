import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTaskManagerTest {

    Path path = Paths.get("test.csv");

    @Test
    void testAddTask() throws IOException {

        TaskManager taskManager = Managers.getDefaultWithSave();

        Task task1 = new Task("taskname 1", "taskdescr1", TaskStatus.NEW, Duration.ofMinutes(15),
                LocalDateTime.of(2025,05,1,14,25));
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

        Path path = Paths.get("test.csv");
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        TaskManager manager = FileBackedTaskManager.loadFromFile(path);
        String saveLine;
        String restoreLine;
        try (Writer fileWriter = new FileWriter(path.toString())) {
            saveLine = "id,type,name,status,description,epic,subtasks,duration,startTime,endTime";
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