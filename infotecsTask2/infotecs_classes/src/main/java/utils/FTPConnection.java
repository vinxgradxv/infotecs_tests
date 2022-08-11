package utils;

import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpProtocolException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class FTPConnection {
    private FtpClient ftp;
    private String hostName;
    private String login;
    private String password;
    private final int PORT = 21;
    public FTPConnection(String hostName, String login, String password) {
        this.hostName = hostName;
        this.login = login;
        this.password = password;
    }

    public void establishConnection() throws FtpProtocolException, IOException {
        SocketAddress addr = new InetSocketAddress(hostName, PORT);
        ftp = FtpClient.create();
        ftp.connect(addr);
        ftp.login(login, password.toCharArray());
    }

    public FtpClient getFTPClient(){
        return ftp;
    }


}
