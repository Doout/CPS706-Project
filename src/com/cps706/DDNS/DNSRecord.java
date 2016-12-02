package com.cps706.DDNS;

import java.io.Serializable;
import java.net.DatagramPacket;

/**
 * Created by Baheer.
 */
public class DNSRecord implements Serializable {

    public static final long serialVersionUID = 75421865432864521L;

    private DNSType type;
    private String name, value;

    /**
     * @param name  The URL
     * @param value The value that his url point to
     * @param type  What type of record is this.
     */
    public DNSRecord(String name, String value, DNSType type) {
        this.name = name.trim();
        this.value = value.trim();
        this.type = type;
    }

    /**
     * @param packet The packet in which have the DNS record in.
     */
    public static DNSRecord parseStearm(DatagramPacket packet) {
        String data = new String(packet.getData(), 0, packet.getLength());
        return parseString(data);
    }

    /**
     * The String in which hold the DNS record
     **/
    public static DNSRecord parseString(String dnsrecord) {
        String rec[] = dnsrecord.split(":");
        return new DNSRecord(rec[0], rec[1], DNSType.getType(Integer.parseInt(rec[2])));
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public DNSType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "DNSRecord{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
