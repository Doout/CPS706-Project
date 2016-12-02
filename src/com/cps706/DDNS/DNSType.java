package com.cps706.DDNS;

import java.io.Serializable;

/**
 * Created by Baheer.
 */

public enum DNSType implements Serializable {

    A(0),
    CName(1),
    NS(2),
    MX(3),
    R(4);

    public static final long serialVersionUID = 7542186543286452L;
    int type;

    /**
     * @param type The type of the DNS
     */
    DNSType(int type) {
        this.type = type;
    }

    public static DNSType getType(int i) {
        for (DNSType d : DNSType.values()) {
            if (d.type == i) return d;
        }
        return null;
    }

    @Override
    public String toString() {
        return "DNSType{" +
                "type=" + type +
                '}';
    }
}