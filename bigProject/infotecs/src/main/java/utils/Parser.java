package utils;

import data.Student;
import main.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
    public List<Student> fromJSONToStudentsList(String json) {
        String beautifulJSON = makeItBeautiful(json);
        String[] words = beautifulJSON.split("\"\"");
        List<Student> res = new ArrayList<>();
        words[0] = words[0].substring(1);
        words[words.length-1] = words[words.length-1].substring(0, words[words.length - 1].length() - 1);
        if (words[0].equals("students")) {
            for (int i = 1; i < words.length; i += 4) {
                res.add(new Student(words[i + 3], Integer.parseInt(words[i + 1])));
                Main.idPool.remove(Integer.parseInt(words[i + 1]));
            }
        }
        return res;
    }

    private String makeItBeautiful(String json) {
        String beautifulJSON = "";
        String pattern = "[\\d\\w\"]";
        boolean inBrackets = false;
        for (char c: json.toCharArray()){
            if (inBrackets) {
                if (c == '"') {
                    inBrackets = false;
                }
                beautifulJSON += c;
            }
            else {
                if (c == '"') {
                    inBrackets = true;
                }
                if (Pattern.matches(pattern, Character.toString(c))) {
                    beautifulJSON += c;
                }
            }
        }
        return beautifulJSON;
    }

    public String fromStudentsListToJSON(List<Student> students) {
        String res = "";
        res += "{\n";
        res += "\"students\": [\n";
        for (int i = 0; i < students.size(); i++){
            res += "{\n";
            res += "\"id\": \"" + students.get(i).getId() + "\",\n";
            res += "\"name\": \"" + students.get(i).getName() + "\"\n";
            res += "}";
            if (i != students.size() - 1) {
                res += ",";
            }
            res += "\n";
        }
        res += "]\n";
        res += "}";
        return res;
    }
}
