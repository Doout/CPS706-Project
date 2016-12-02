package com.cps706;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Baheer.
 */
public class Settings {
    /**
     * the settings for this program but the name of the video
     */
    public static HashMap<String, String> SETTINGSMAP;
    /**
     * The name of the video
     */
    public static ArrayList<String> VIDEOFILE;

    /**Load the settings from the file**/
    static {
        SETTINGSMAP = new HashMap<>();
        try {
            BufferedReader in = new BufferedReader(new FileReader(new File("settings.txt")));
            SETTINGSMAP.put("port", in.readLine().split(":")[1].trim());
            SETTINGSMAP.put("herDNS", in.readLine().split(":")[1].trim());
            SETTINGSMAP.put("herCDN", in.readLine().split(":")[1].trim());
            SETTINGSMAP.put("hisDNS", in.readLine().split(":")[1].trim());
            in.readLine();//skip some comments
            VIDEOFILE = new ArrayList<>();
            String fileName;
            while ((fileName = in.readLine()) != null) {
                VIDEOFILE.add(fileName.trim());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
