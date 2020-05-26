package pack.persistence;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

public class AuditService {

    private static AuditService instance;
    private BufferedWriter audit_out;

    private AuditService() {
        try {
            String AUDIT_PATH = "src\\pack\\csvFiles\\audit.csv";
            audit_out = new BufferedWriter(new FileWriter(AUDIT_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static AuditService getInstance() {
        if(instance == null) {
            instance = new AuditService();
            return instance;
        }
        return instance;
    }

    public void audit(String action) {
        Date d = new Date();
        long time = d.getTime();
        Timestamp ts = new Timestamp(time);
        try {
            audit_out.write(action + " " + ts.toString() + "\n");
            audit_out.flush();
            //audit_out.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        catch (NullPointerException e) {
            System.out.println("Null");
        }
    }

}
