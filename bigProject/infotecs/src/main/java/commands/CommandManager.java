package commands;

import data.Student;
import sun.net.ftp.FtpProtocolException;
import utils.FTPConnection;
import utils.Parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommandManager {
    private FTPConnection ftpConnection;
    private List<Command> commandList = new ArrayList<>();

    public CommandManager(FTPConnection ftpConnection) {
        this.ftpConnection = ftpConnection;
        commandList.add(new GetStudentsCommand());
        commandList.add(new FindByIdCommand());
        commandList.add(new AddStudentCommand());
        commandList.add(new RemoveStudentCommand());
        commandList.add(new ExitCommand());
    }

    public void execute(Command command, List<Student> students) {
        if (command != null) {
            System.out.println(command.execute(students));
        }
        else {
            /*System.out.println("Такой команды не существует, введите номер от 1 до 5"); */
            throw new NumberFormatException();
        }
        boolean isUpdated = false;
        boolean wantToUpdate = true;
        while (!isUpdated && wantToUpdate && command.isChangedList()) {
            isUpdated = updateServer(students);
            if (!isUpdated) {
                System.out.println("Хотите попробовать сохранить изменения на сервер еще раз (yes)");
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (input.equals("yes")) {
                    wantToUpdate = true;
                }
                else {
                    wantToUpdate = false;
                }
            }
        }
    }

    public List<Command> getCommandList(){
        return commandList;
    }

    public boolean updateServer(List<Student> students) {
        String output = new Parser().fromStudentsListToJSON(students);
        try {
            File file = File.createTempFile("students", ".json");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(output.getBytes(StandardCharsets.UTF_8));
            FileInputStream fis = new FileInputStream(file);
            if (!ftpConnection.getFTPClient().isConnected()) {
                ftpConnection.establishConnection();
            }
            ftpConnection.getFTPClient().putFile("/htdocs/students.json", fis);
            System.out.println("Все данные на сервере актуализированы");
            return true;
        } catch (FtpProtocolException | IOException e) {
            System.out.println("Не удалось сохранить изменения на сервер");
            return false;
        }
    }

}
