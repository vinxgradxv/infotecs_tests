import commands.CommandManager;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import sun.net.ftp.FtpProtocolException;
import utils.FTPConnection;

import java.io.IOException;
import java.util.ArrayList;

public class TestCommandManager {

    FTPConnection connection;

    CommandManager commandManager;

    public String hostName = "ftpupload.net";
    public String login = "epiz_32313951";
    public String password = "zi6b5YuiJgk";

    @BeforeClass
    public void connect() throws FtpProtocolException, IOException {
        connection = new FTPConnection(hostName, login, password);
        connection.establishConnection();
        commandManager = new CommandManager(connection);
    }

    @Test
    public void commandManagerWithConnectionServerUpdate() {
        Assert.assertTrue(commandManager.updateServer(new ArrayList<>()));
    }


    @Test
    public void commandManagerWithNoConnectionServerUpdate() throws FtpProtocolException, IOException {
        connection.getFTPClient().abort();
        // True, потому что внутри метода updateServer мы пытаемся восстановить соединение, если оно не установлено
        Assert.assertTrue(commandManager.updateServer(new ArrayList<>()));
        connection.establishConnection();
    }
}
