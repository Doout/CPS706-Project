/**
 * Created by Baheer.
 */
public class CPS706Project {


    public static DDNS getLocalDNS() {
        DDNS localDNS = new DDNS();
        localDNS.addDNSRecord(new DNSRecord("herCDN.com", "NSherCDN.com", DNSRecord.Type.NS));
        localDNS.addDNSRecord(new DNSRecord("NSherCDN.com", "192.168.1.1", DNSRecord.Type.A));
        localDNS.addDNSRecord(new DNSRecord("hiscinema.com", "NShiscinema.com", DNSRecord.Type.NS));
        localDNS.addDNSRecord(new DNSRecord("NShiscinema.com", "192.168.1.2", DNSRecord.Type.A));
        return localDNS;
    }

    public static DDNS getHerCinemaNS() {
        DDNS dns = new DDNS();
        dns.addDNSRecord(new DNSRecord("video.netcinema.com", "herCDN.com", DNSRecord.Type.NS));
        dns.addDNSRecord(new DNSRecord("herCDN.com", "www.herCDN.com", DNSRecord.Type.CName));
        dns.addDNSRecord(new DNSRecord("www.herCDN.com", "IPwww", DNSRecord.Type.A));
        return dns;
    }

    public DDNS getHiscinemaNS() {
        return null;
    }

}
