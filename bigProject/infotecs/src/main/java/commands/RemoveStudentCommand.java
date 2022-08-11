package commands;

import data.Student;
import main.Main;
import utils.FTPConnection;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RemoveStudentCommand extends Command{

    public RemoveStudentCommand() {
        description = "Удаление студента по id";
        serialNumber = 4;
    }

    @Override
    public String execute(List<Student> students) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите id студента");
            int id = scanner.nextInt();
            for (int i = 0; i < students.size(); i++) {
                if (students.get(i).getId() == id) {
                    Main.idPool.add(students.get(i).getId());
                    students.remove(i);
                    changedList = true;
                    return "Студент с id = " + id + " был удален из списка";
                }
            }
            return "В списке нет студента с таким id";
        } catch (InputMismatchException e){
            return "Неверный формат id";
        }
    }
}
