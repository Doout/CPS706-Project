package com.cps706.hercinema;


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
public class HerCinema extends DDNSHandler {


    public HerCinema() {
        super(getHerCinemaNS(), false, false);
    }

    public static DDNS getHerCinemaNS() {
        DDNS dns = new DDNS();
        dns.addDNSRecord(new DNSRecord("herCDN.com", "www.herCDN.com", DNSType.CName));
        dns.addDNSRecord(new DNSRecord("www.herCDN.com", Settings.SETTINGSMAP.get("herCDN"), DNSType.A));
        return dns;
    }


}
