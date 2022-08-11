import data.Student;
import org.testng.Assert;
import org.testng.annotations.Test;


public class TestStudent {
    @Test
    public void getName() {
        String name = "Ivan";
        Student student = new Student(name, 19);
        Assert.assertEquals(student.getName(), name);
    }

    @Test
    public void getID() {
        int id = 14;
        Student student = new Student("Ivan", id);
        Assert.assertEquals(student.getId(), id);
    }

    @Test
    public void stringEquivalent() {
        String name = "Ivan";
        int id = 0;
        Student student = new Student(name, id);
        Assert.assertEquals(student.toString(), "id: " + id + ", name: " + name);
    }
}
