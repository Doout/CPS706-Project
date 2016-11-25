import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Baheer.
 */
public class Until {

    public static void sendData(ByteInputStream data, Socket socket){

    }

    private static void sendData(ObjectInputStream in, Socket socket) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(in);
            out.flush();
        } catch (IOException e) {
            //nothing we can do if this happen
        }
    }

    public void makeGETRequest() {

    }

    public void makeGETRequest(String httpV, byte[] bodys) {

    }

}
