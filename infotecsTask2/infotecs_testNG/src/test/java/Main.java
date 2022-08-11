import org.testng.TestNG;

import java.util.ArrayList;
import java.util.List;

public class Main {
    static TestNG testing = new TestNG();

    public static void main(String[] args) {
        String xmlFileName = "testng.xml";
        List<String> suites = new ArrayList<>();
        suites.add(xmlFileName);
        testing.setTestSuites(suites);
        testing.run();
    }
}
