import org.testng.TestListenerAdapter;
import org.testng.TestNG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        testng.setTestClasses(new Class[] { TestCommandManager.class, TestStudent.class, TestFTPConnection.class, TestCommands.class });
        testng.run();
    }
}
