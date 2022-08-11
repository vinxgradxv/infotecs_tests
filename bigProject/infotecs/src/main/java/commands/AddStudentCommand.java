package commands;

import data.Student;
import main.Main;
import utils.FTPConnection;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class AddStudentCommand extends Command{

    public AddStudentCommand() {
        description = "Добавление студента ( id генерируется автоматически)";
        serialNumber = 3;
    }

    @Override
    public String execute(List<Student> students) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите имя студента");
        String name = scanner.nextLine();
        try {
            students.add(new Student(name, Main.idPool.first()));
            Main.idPool.remove(Main.idPool.first());
            changedList = true;
            return "Студент был добавлен в список";
        }
        catch (NoSuchElementException e){
            return "Не удалось добавить студента в список, из-за отсутствия свободных id";
        }
    }
}
