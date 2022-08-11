package commands;

import data.Student;
import sun.net.ftp.FtpClient;
import utils.FTPConnection;

import java.util.List;

public abstract class Command {



    protected boolean changedList = false;

    protected String description;
    protected int serialNumber;
    public String getDescription(){
        return description;
    }
    public int getSerialNumber() {
        return serialNumber;
    }
    public boolean isChangedList() {return changedList;}

    public abstract String execute(List<Student> students);
}
