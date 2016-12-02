package com.cps706.myDNS;

import com.cps706.DDNS.DDNS;
import com.cps706.DDNS.DDNSHandler;
import com.cps706.DDNS.DNSRecord;
import com.cps706.DDNS.DNSType;
import com.cps706.Server;
import com.cps706.Settings;
import com.cps706.Until;


/**
 * Created by Baheer.
 */
public class MyDNS extends DDNSHandler{


    public MyDNS() {
        super(myDNS(), true,true);
    }

    private static DDNS myDNS() {
        DDNS dns = new DDNS();
        dns.addDNSRecord(new DNSRecord("herCDN.com", "NSherCDN.com", DNSType.NS));
        dns.addDNSRecord(new DNSRecord("NSherCDN.com", Settings.SETTINGSMAP.get("herDNS"), DNSType.A));
        dns.addDNSRecord(new DNSRecord("hiscinema.com", "NShiscinema.com", DNSType.NS));
        dns.addDNSRecord(new DNSRecord("NShiscinema.com", Settings.SETTINGSMAP.get("hisDNS"), DNSType.A));
        return dns;
    }

}
