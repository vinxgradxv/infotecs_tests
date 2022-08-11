import org.testng.Assert;
import org.testng.annotations.Test;
import sun.net.ftp.FtpProtocolException;
import utils.FTPConnection;

import java.io.IOException;
import java.net.UnknownHostException;

public class TestFTPConnection {

    public String hostName = "ftpupload.net";
    public String login = "epiz_32313951";
    public String password = "zi6b5YuiJgk";

    @Test
    public void successfulEstablishConnection() throws FtpProtocolException, IOException {
        FTPConnection ftpConnection = new FTPConnection(hostName, login, password);
        ftpConnection.establishConnection();
        Assert.assertTrue(ftpConnection.getFTPClient().isConnected());
    }

    @Test
    public void establishConnectionWithWrongHostName() {
        FTPConnection ftpConnection = new FTPConnection(hostName + "!", login, password);
        Assert.assertThrows(UnknownHostException.class, ftpConnection::establishConnection);
    }

    @Test
    public void establishConnectionWithWrongAuthorizationData() {
        FTPConnection ftpConnection = new FTPConnection(hostName, login + password, login + password);
        Assert.assertThrows(FtpProtocolException.class, ftpConnection::establishConnection);
    }


}
