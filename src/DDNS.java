import java.util.ArrayList;

/**
 * Created by Baheer.
 */
public class DDNS {

    public ArrayList<DNSRecord> table;

    public DDNS() {
        table = new ArrayList<>(10);
    }


    /**
     * @return True if the record was added, False if the table contain the rec name
     */
    public boolean addDNSRecord(DNSRecord rec) {
        for (DNSRecord d : table) {
            if (d.getName().equalsIgnoreCase(rec.getName())) {
                return false;
            }
        }
        table.add(rec);
        return true;
    }

    //Start from the end of the domain and make it way down.
    public DNSRecord findDNSRecord(String name) {

        DNSRecord temp = null;
        //Keep reusing the same list to reduce the size of this search
        int index = 0;
        int size = table.size();
        int numberOfTry = 0;
        //get the doman in part
        String[] namePart = name.split("\\.");
        int namePartToAdd = namePart.length - 1;
        String find = namePart[namePartToAdd];

        DNSRecord[] list = table.toArray(new DNSRecord[size]);

   //Try to redues the number of check.
        while (size != 1 && numberOfTry < 20 && namePartToAdd > 0) {
            for (int i = 0; i < size; i++) {
                if (list[i].getName().endsWith(find)) {
                    list[index++] = list[i];
                }
            }
            find = namePart[--namePartToAdd] + "." + find;
            size = index;
            index = 0;
        }
        if (size > 1) { //Best match
            for (int i = 0; i < size; i++) {
                if (list[i].getName().equalsIgnoreCase(find)) {
                    list[0] = list[i];
                    break;
                }
            }
        }
        temp = list[0];
        if (temp != null)
            if (temp.getType() != DNSRecord.Type.A && temp.getType() != DNSRecord.Type.R ) // Only type A hold the IP for the domain
                return findDNSRecord(temp.getValue());
            else return temp;
        return null;
    }

}
