package main;

import commands.Command;
import commands.CommandManager;
import data.Student;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;
import utils.FTPConnection;
import utils.Parser;


import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeSet;


public class Main {
    private static FTPConnection ftpConnection;
    private static String hostName;
    private static String login;
    private static String password;
    /*public static PriorityQueue<Integer> idPool = new PriorityQueue<>(); */
    public static TreeSet<Integer> idPool = new TreeSet<>();
    private static Parser jsonParser = new Parser();
    private static final int MAX_CAPACITY = 1000000;
    private static final Integer PORT = 21;
    private static List<Student> students;
    private static CommandManager commandManager;

    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Введите имя хоста:");
        String hostName = console.nextLine();
        System.out.println("Введите логин:");
        String login = console.nextLine();
        System.out.println("Введите пароль:");
        String password = console.nextLine();


        for (int i = 0; i < MAX_CAPACITY; i++) {
            idPool.add(i);
        }

        try {
            ftpConnection = new FTPConnection(hostName, login, password);
            ftpConnection.establishConnection();
        } catch (UnknownHostException e) {
            System.err.println("Не удалось подключиться к серверу");
            return;
        }
        catch (FtpProtocolException | IOException e) {
            System.out.println("Ошибка аутентификации");
            return;
        }



        try {
            String res = getFileTextFromServer("/htdocs/students.json");
            students = jsonParser.fromJSONToStudentsList(res);
            commandManager = new CommandManager(ftpConnection);

            for (Command command : commandManager.getCommandList()) {
                System.out.println(command.getSerialNumber() + ". " + command.getDescription());
            }

            while (true) {
                try {
                    System.out.println("Введите номер команды:");
                    System.out.print("$");
                    String input = console.nextLine();
                    Command com = getCommandFromInput(input);
                    commandManager.execute(com, students);
                    if (com.getSerialNumber() == 5) {
                        break;
                    }
                } catch (NumberFormatException e){
                    System.out.println("Неверный формат команды (введите число от 1 до 5)");
                }
            }


        } catch (FtpProtocolException | IOException e) {
            System.err.println("Файл students.json не найден на сервере");
        }
    }

    public static Command getCommandFromInput(String input) {
        List<Command> commands = commandManager.getCommandList();
        for (Command command : commands) {
            if (Integer.parseInt(input) == command.getSerialNumber()) {
                return command;
            }
        }
        return null;
    }

    /* public static FTPConnection establishConnection(String hostName, String login, String password) throws FtpProtocolException, IOException {
        return new FTPConnection(hostName, login, password);
    }

     */

    public static String getFileTextFromServer(String path) throws FtpProtocolException, IOException {
        InputStream is = ftpConnection.getFTPClient().getFileStream(path);
        InputStreamReader inputStreamReader = new InputStreamReader(is);
        String res = new String("");
        while (true) {
            int b = inputStreamReader.read();
            if (b != -1) {
                res += (char) b;
            } else {
                break;
            }
        }
        return res;
    }

}
