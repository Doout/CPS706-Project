package com.cps706.DDNS;

import com.cps706.Server;
import com.cps706.Settings;
import com.cps706.Until;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Baheer.
 */
public class DDNSHandler {

    private Server server;
    private DDNS dns;
    private boolean solveDNS;
    private boolean keepRecord;

    /**
     * @param startDNS   What dns record does this DNS hold on start.
     * @param keepRecord Should the new record that this dns run into be save.
     * @param solveDNS   Should the DNS solve the record.
     */
    public DDNSHandler(DDNS startDNS, boolean keepRecord, boolean solveDNS) {
        this.solveDNS = solveDNS;
        this.keepRecord = keepRecord;
        server = new Server();
        if (startDNS != null)
            this.dns = startDNS;
        else this.dns = new DDNS();
    }

    /**
     * @param port The port number in which this DNS will be bound to.
     */
    public void startUpDNS(int port) {

        server.setupUDP(port, pros -> {
            String host = new String(pros.getData(), 0, pros.getLength());
            System.out.println(host);
            DNSRecord rec = dns.findDNSRecord(host);
            //    System.out.println("Start with NS " + rec.getName().startsWith("NS") + "  " + solveDNS);
            if (rec.getType() == DNSType.R || rec.getName().startsWith("NS")) {
                while (solveDNS && (rec.getType() == DNSType.R || rec.getName().startsWith("NS"))) {
                    if (rec.getName().startsWith("NS")) {
                        System.out.println(host + " find at " + rec.getValue());
                        DatagramSocket socket = Until.sendUDPPacket(host.getBytes(), rec.getValue(), Integer.parseInt(Settings.SETTINGSMAP.get("port")));
                        System.out.println("getting data on port " + socket.getLocalPort());
                        socket.setSoTimeout(10_000); // one sec only
                        DatagramPacket data = Until.getPacket(socket);
                        String dnsRecord = new String(data.getData(), 0, data.getLength());
                        System.out.println(dnsRecord);
                        rec = DNSRecord.parseString(dnsRecord);
                    } else if (rec.getType() == DNSType.R) {
                        rec = dns.findDNSRecord(rec.getValue());
                        if (keepRecord) {
                            if (!dns.table.contains(rec))
                                dns.table.add(rec);
                        }


                    }
                }
                if (keepRecord) {
                    if (!dns.table.contains(rec))
                        dns.table.add(rec);
                }

            }
            System.out.println("Sending to " + pros.getAddress().getHostAddress() + " @ " + pros.getPort());
            System.out.println(rec.toString());
            Until.sendUDPPacket((rec.getName() + ":" + rec.getValue() + ":" + rec.getType().type).getBytes(), pros.getAddress().getHostAddress(), pros.getPort())
            ;
            System.out.println("DDNS have been sent");
        });
    }
}
