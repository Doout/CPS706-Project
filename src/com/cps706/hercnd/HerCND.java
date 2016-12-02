package com.cps706.hercnd;

import com.cps706.Server;
import com.cps706.Settings;
import com.cps706.Until;
import com.sun.net.httpserver.HttpHandler;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Baheer.
 */
public class HerCND {


    Server server;

    public HerCND() {
        server = new Server();
    }

    public static Map<String, String> queryToMap(String query) {
        if (query == null) return null;
        if (query.startsWith("{"))
            query = query.substring(1);
        if (query.endsWith("}"))
            query = query.substring(0, query.length() - 2);
        System.out.println(query);
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length > 1) {
                result.put(pair[0], pair[1]);
            } else {
                result.put(pair[0], "");
            }
        }
        return result;
    }

    /**
     * @param port   The post this is bound to
     * @param myView is the view local or server
     */
    public void startUp(int port, boolean myView) {

        Map<String, HttpHandler> map = new HashMap<String, HttpHandler>();
        if (myView) {
            map.put("/home", httpExchange -> {
                String html = "";
                for (int i = 0; i < Settings.VIDEOFILE.size(); i++) {
                    html += "<a href=\"/redir?url=video.hiscinema.com/video%3Fid%3D" + i + "\">" + Settings.VIDEOFILE.get(i) + "</a><br>\n";
                }
                httpExchange.sendResponseHeaders(200, html.getBytes().length);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(html.getBytes());
                outputStream.close();
            });
            map.put("/redir", httpExchange -> {
                String url = httpExchange.getRequestURI().getQuery().substring(4);
                System.out.println("Going to this url(before DNS): " + url);
                url = Until.formatURL(url);
                System.out.println("Going to this url: " + url);
                String body2 = "<?php\n" +
                        "\n" +
                        "header('Location: http://" + url + "');\n" +
                        "\n" +
                        "?>";
                String body = "<html><meta http-equiv=\"refresh\" content=\"0; url=http://" + url + "\" /></html>";
                httpExchange.sendResponseHeaders(200, body.getBytes().length);
                OutputStream outputStream = httpExchange.getResponseBody();
                outputStream.write(body.getBytes());
                outputStream.close();
            });
        } else {
            map.put("/video", httpExchange -> {
                Map<String, String> par = queryToMap(httpExchange.getRequestURI().getQuery());
                String videoID = par.get("id");
                String videoName = Settings.VIDEOFILE.get(Integer.parseInt(videoID));
                File fileToSend = new File(videoName);
                System.out.println(videoName + " " + "is being send");
                httpExchange.sendResponseHeaders(200, fileToSend.length());
                httpExchange.setAttribute("Content-Type", "application/octet-stream");
                httpExchange.setAttribute("content-disposition", "attachment; filename=\"" + "videoName" + "\"");

                OutputStream outputStream = httpExchange.getResponseBody();
                Files.copy(fileToSend.toPath(), outputStream);
                outputStream.close();
            });

        }
        server.setupHTTPServer(port, map);
    }


}
