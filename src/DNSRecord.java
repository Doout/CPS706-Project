/**
 * Created by Baheer.
 */
public class DNSRecord {

    private Type type;
    private String name, value;

    public DNSRecord(String name, String value, Type type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public Type getType() {
        return type;
    }


    enum Type {
        A(0),
        CName(1),
        NS(2),
        MX(3),
        R(4);

        int type;

        Type(int type) {
            this.type = type;
        }
    }
}
