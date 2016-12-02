package com.cps706;

import com.cps706.DDNS.DDNS;
import com.cps706.DDNS.DNSRecord;
import com.cps706.DDNS.DNSType;
import com.cps706.hercinema.HerCinema;
import com.cps706.hercnd.HerCND;
import com.cps706.hiscinema.HisCinema;
import com.cps706.myDNS.MyDNS;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Baheer.
 */
public class CPS706Project {

    public static void main(String... args) throws IOException, InterruptedException, ClassNotFoundException {
        if (args.length == 0) {
            args = new String[]{"my"};
        }
        if (args.length > 0) {
            String mode = args[0]; // test case
            if (mode.equalsIgnoreCase("her")) {
                HerCinema her = new HerCinema();
                her.startUpDNS(Integer.parseInt(Settings.SETTINGSMAP.get("port")));
            } else if (mode.equalsIgnoreCase("his")) {
                HisCinema his = new HisCinema();
                his.startUpDNS(Integer.parseInt(Settings.SETTINGSMAP.get("port")));
            } else if(mode.equalsIgnoreCase("herCDN")){
                HerCND hercnd = new HerCND();
                hercnd.startUp(Integer.parseInt(Settings.SETTINGSMAP.get("port")), false);
            } else if (mode.equalsIgnoreCase("my")) {
                MyDNS his = new MyDNS();
                his.startUpDNS(Integer.parseInt(Settings.SETTINGSMAP.get("port")));

                HerCND hercnd = new HerCND();
                hercnd.startUp(Integer.parseInt(Settings.SETTINGSMAP.get("port")), true);
              /*  DatagramSocket ret = Until.sendUDPPacket("video.hiscinema.com".getBytes(), "127.0.0.1", Settings.PORT);
                DNSRecord r = DNSRecord.parseStearm(getPacket(ret));
                System.out.println(r.getValue());
                */

            }

        }

    }





}
