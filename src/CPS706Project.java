import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        dns.addDNSRecord(new DNSRecord("herCDN.com", "www.herCDN.com", DNSRecord.Type.CName));
        dns.addDNSRecord(new DNSRecord("www.herCDN.com", "IPwww", DNSRecord.Type.A));
        return dns;
    }

    public static void main(String... args) throws IOException, InterruptedException {
        test();
    }

    public static void test() throws IOException, InterruptedException {
        Server s = new Server();
     /*   s.setupServerSocket(45000, pros -> {

            System.out.println(pros.toString());
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(pros.getInputStream()));
                System.out.println(in.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
*/
        HashMap<String, HttpHandler> map = new HashMap<>();
        map.put("/test", exchange -> {
            String response = "This is the response";
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        s.setupHTTPServer(45000, map);
        Thread.sleep(1000);
        requestHttp("http://localhost:45000/test");
       // sendHTTP("this is a test", "http://localhost:45000");


        //  Until.sendUDPPacket("this is a test".getBytes(), InetAddress.getLocalHost().getHostAddress(), 45000);

    }

    public static void sendHTTP(String buffer, String url) throws IOException {
        byte[] postData = buffer.getBytes(StandardCharsets.UTF_8);
        int postDataLength = postData.length;
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoOutput(true);
        conn.setInstanceFollowRedirects(false);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("charset", "utf-8");
        conn.setRequestProperty("Content-Length", Integer.toString(postDataLength));
        conn.setUseCaches(false);
        try (DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
            wr.write(postData);
            wr.flush();
        }


    }

    public static void requestHttp(String url) throws IOException {
        URL u = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();
        conn.setDoInput(true);
        Map<String, List<String>> map = conn.getHeaderFields();
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() +
                    " ,Value : " + entry.getValue());
        }
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        System.out.println(in.readLine());

    }

    public DDNS getHiscinemaNS() {
        DDNS dns = new DDNS();
        dns.addDNSRecord(new DNSRecord("video.netcinema.com", "herCDN.com", DNSRecord.Type.R));
        return dns;
    }

}
