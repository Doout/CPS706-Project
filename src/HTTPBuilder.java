import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Baheer.
 * This is only a GET HTTP Builder
 */
public class HTTPBuilder {


    Map<String, String> map = new HashMap<>();

    public HTTPBuilder(String htmlVersion) {
        map.put("HTML Version ", htmlVersion);
    }

    public HTTPBuilder() {
        map.put("HTML Version ", "1.1");
    }

    public HTTPBuilder setHTTPcode(int code) {
        map.put("HTTP code", Integer.toString(code));
        return this;
    }

    public HTTPBuilder add(String key, String value) {
        map.put(key, value);
        return this;
    }

    public String getHeader() {
        String header = "";
        Set<String> keys = map.keySet();
        for (String key : keys) {
            header += key + ":" + map.get(key);
        }
        return header;
    }
}
