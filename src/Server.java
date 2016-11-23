import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Created by Baheer.
 */
public class Server {

    private volatile boolean running = false;

    private Thread listenThread;
    private Thread server;

    private volatile ConcurrentBlockQueue<DatagramPacket> UDPPackets = new ConcurrentBlockQueue<>(100);
    private volatile ConcurrentBlockQueue<Socket> serverSockets = new ConcurrentBlockQueue<>(100);

    public void setupUDP(final int port, ServerProcess<? extends DatagramPacket> serverProcess) {
        if (running) return;
        running = true;
        listenThread = new Thread(() -> {
            DatagramSocket serverSocket;
            try {
                serverSocket = new DatagramSocket(port);
            } catch (SocketException e) {
                System.err.println("Can not created server on port " + port);
                return;
            }

            int datasize = (1 << 16) - 1;
            while (running) {
                byte[] receiveData = new byte[datasize];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                try {
                    serverSocket.receive(receivePacket);
                } catch (IOException e) {// nothing we can do.
                }
                if (UDPPackets.size() > 100) // packet drop.
                    continue;
                UDPPackets.push(receivePacket);
            }

        });
        listenThread.start();

        server = new Thread(() -> {
            final ServerProcess serverProcess1 = serverProcess;
            while (running) {
                while (!serverSockets.isEmpty()) {
                    DatagramPacket ptr = UDPPackets.pop();
                    if (ptr != null)
                        serverProcess1.process(ptr);
                }
            }

        });
        server.start();

    }

    public void setupServerSocket(int port, ServerProcess<? extends Socket> serverProcess) {
        if (running) return;
        listenThread = new Thread(() -> {
            ServerSocket serverSock = null;
            try {
                serverSock = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace(); //end the program since this can not fail.
                System.exit(0);
            }

            while (running) {
                try {
                    Socket sock = serverSock.accept();
                    //we got a packet
                    if (serverSockets.size() > 100) // packet drop.
                        continue;
                    serverSockets.push(sock);
                } catch (IOException e) {
                }
            }
        });
        listenThread.start();
        server = new Thread(() -> {
            final ServerProcess serverProcess1 = serverProcess;
            while (running) {
                while (!serverSockets.isEmpty()) {
                    Socket ptr = serverSockets.pop();
                    if (ptr != null)
                        serverProcess1.process(ptr);
                }
            }

        });
        server.start();
    }

    public void sendData(ObjectInputStream in, Socket socket) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(in);
            out.flush();
        } catch (IOException e) {
            //nothing we can do if this happen
        }
    }


    public void stop() {
        running = false;
        try {
            server.join(2); // just kill it
            listenThread.join(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
