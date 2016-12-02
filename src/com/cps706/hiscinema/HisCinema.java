package com.cps706.hiscinema;

import com.cps706.DDNS.DDNS;
import com.cps706.DDNS.DDNSHandler;
import com.cps706.DDNS.DNSRecord;
import com.cps706.DDNS.DNSType;

/**
 * Created by Baheer.
 */
public class HisCinema extends DDNSHandler {

    public HisCinema() {
        super(getHiscinemaNS(), false,false);
    }

    public static DDNS getHiscinemaNS() {
        DDNS dns = new DDNS();
        dns.addDNSRecord(new DNSRecord("video.hiscinema.com", "herCDN.com", DNSType.R));
        return dns;
    }

}
