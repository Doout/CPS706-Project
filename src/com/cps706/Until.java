package com.cps706;

import com.cps706.DDNS.DNSRecord;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by Baheer.
 */
public class Until {


    /**
     * read a packet from the socket
     *
     * @param socket The DatagramSocket in which to receive the packet from
     */
    public static DatagramPacket getPacket(DatagramSocket socket) throws IOException {
        byte[] receiveData = new byte[20000];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        return receivePacket;
    }

    /**
     * Send a UDP packet
     *
     * @param buffer The bytes the will be send
     * @param ip     The ip of the host to send to
     * @param port   The port in which to send it to. If this port is not open, The next available  port will be selected
     */
    public static DatagramSocket sendUDPPacket(byte[] buffer, String ip, int port) throws IOException {
        DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length, InetAddress.getByName(ip), port);
        DatagramSocket socket = null;
        int index = 1;
        while (socket == null && index++ <= 10)
            try {
                socket = new DatagramSocket(Integer.parseInt(Settings.SETTINGSMAP.get("port")) + index);
            } catch (SocketException e) {
            }
        if (socket == null) socket = new DatagramSocket();
        socket.send(packet);
        return socket;
    }

    /**
     * @param url The url in which to format to use with this project.
     * @return The return url will have a port number which the herCDN is host on
     */
    public static String formatURL(String url) throws IOException {
        if (url.startsWith("http://")) url = url.substring(7);
        String domain = url;
        String ext = "";
        if (url.contains("/")) {
            domain = url.substring(0, url.indexOf("/"));
            ext = url.substring(url.indexOf("/"));
        }
        DatagramSocket ret = Until.sendUDPPacket(domain.getBytes(), "127.0.0.1", Integer.parseInt(Settings.SETTINGSMAP.get("port")));
        DNSRecord r = DNSRecord.parseStearm(getPacket(ret));
        url = r.getValue() + ":" + Settings.SETTINGSMAP.get("port") + ext;
        return url;
    }

}
