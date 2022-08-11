import commands.*;
import data.Student;
import main.Main;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class TestCommands {

    public InputStream backUp;

    @Test
    public void exitCommand() {
        Command exit = new ExitCommand();
        Assert.assertEquals(exit.execute(null), "Выполнение программы завершено");
    }

    @BeforeMethod
    public void sysBackup() {
        backUp = System.in;
    }

    @AfterMethod
    public void sysRestore() {
        System.setIn(backUp);
    }

    @Test
    public void addStudent() {
        Main.idPool = new TreeSet<>();
        Main.idPool.add(0);
        String name = "Ivan";
        setStringToInputStream(name);
        List<Student> studentList = new ArrayList<>();
        Command add = new AddStudentCommand();
        Assert.assertEquals(add.execute(studentList), "Студент был добавлен в список");
        Assert.assertEquals(studentList.get(0).getName(), name);
    }

    @Test
    public void addStudentsWhenNoId() {
        Main.idPool = new TreeSet<>();
        String name = "Ivan";
        setStringToInputStream(name);
        List<Student> studentList = new ArrayList<>();
        Command add = new AddStudentCommand();
        Assert.assertEquals(add.execute(studentList), "Не удалось добавить студента в список, из-за отсутствия свободных id");
        Assert.assertEquals(studentList.size(), 0);
    }
    @Test
    public void findExistingStudent() {
        setStringToInputStream("0");
        List<Student> studentList = new ArrayList<>();
        Student student = new Student("Gleb", 0);
        studentList.add(student);
        studentList.add(new Student("Gleb", 1));
        Command find = new FindByIdCommand();
        Assert.assertEquals(find.execute(studentList), student.toString());
    }

    @Test
    public void findNotExistingStudent() {
        String id = "0";
        setStringToInputStream(id);
        List<Student> studentList = new ArrayList<>();
        Command find = new FindByIdCommand();
        Assert.assertEquals(find.execute(studentList), "Студент с id = " + id + " не найден");
    }

    @Test
    public void findStudentUsingNotIntId () {
        String id = "asdfsd";
        setStringToInputStream(id);
        Command find = new FindByIdCommand();
        Assert.assertEquals(find.execute(new ArrayList<>()), "Неверный формат id");
    }
    @Test
    public void removeExistingStudent() {
        String id = "0";
        setStringToInputStream(id);
        Student student = new Student("Gleb", 0);
        List<Student> students = new ArrayList<>();
        students.add(student);
        Command remove = new RemoveStudentCommand();
        Assert.assertEquals(remove.execute(students), "Студент с id = " + id + " был удален из списка");
        Assert.assertEquals(students.size(), 0);
    }

    @Test
    public void removeNotExistingStudent() {
        String id = "0";
        setStringToInputStream(id);
        Command remove = new RemoveStudentCommand();
        Assert.assertEquals(remove.execute(new ArrayList<>()), "В списке нет студента с таким id");
    }

    @Test
    public void removeUsingNotIntID() {
        String id = "aefeml12313";
        setStringToInputStream(id);
        Command remove = new RemoveStudentCommand();
        Assert.assertEquals(remove.execute(new ArrayList<>()), "Неверный формат id");
    }

    private void setStringToInputStream(String str) {
        ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes());
        System.setIn(in);
    }


}
