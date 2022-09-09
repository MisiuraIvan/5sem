package sample;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.Arrays;

public class Connection {
    private static Connection connection;
    public static Socket socket;
    public static DataOutputStream writer;
    public static DataInputStream reader;
    Connection(){
        try {
            FileReader readfile=new FileReader("B:\\КурсачJava\\server\\port.txt");
            char[] buf=new char[256];
            int c;
            while((c=readfile.read(buf))>0){
                buf= Arrays.copyOf(buf,c);
            }
            int port=Integer.parseInt(new String(buf));
            socket = new Socket("127.0.0.1", port);
            writer = new DataOutputStream(socket.getOutputStream());
            reader = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        if(connection==null){
            connection=new Connection();
        }
        return connection;
    }

}
