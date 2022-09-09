import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Port {
    private static int port;

    public static int getPort() {
        return port;
    }

    Port(){
        try {
            FileReader readfile = new FileReader("port.txt");
            char[] buf = new char[256];
            int c;
            while ((c = readfile.read(buf)) > 0) {
                buf = Arrays.copyOf(buf, c);
            }
            port = Integer.parseInt(new String(buf));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
