package commands;

import data.Student;
import utils.FTPConnection;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class FindByIdCommand extends Command{

    public FindByIdCommand() {

        description = "Получение информации о студенте по id";
        serialNumber = 2;
    }

    @Override
    public String execute(List<Student> students) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите id студента");
            int id = scanner.nextInt();
            for (Student student : students) {
                if (student.getId() == id) {
                    return student.toString();
                }
            }
            return "Студент с id = " + id + " не найден";
        }catch (InputMismatchException e){
            return "Неверный формат id";
        }
    }
}
