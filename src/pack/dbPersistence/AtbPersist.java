package pack.dbPersistence;

import pack.databaseConnection.DatabaseConnection;
import pack.entities.vehicule.Autobuz;
import pack.loc.Locatie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AtbPersist {

    private static final String INSERT_STATEMENT = "INSERT INTO autobuze (nr_id, number_plate, sofer, nr_locuri, locatii, in_service, in_transit) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM autobuze WHERE number_plate = ?";
    private static final String UPDATE_STATEMENT = "UPDATE autobuze SET sofer = ? WHERE number_plate = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM autobuze WHERE number_plate = ?";
    private static final String ALL_STATEMENT = "SELECT * FROM autobuze;";

    private static AtbPersist instance;

    private AtbPersist() {

    }

    public static AtbPersist getInstance() {
        if(instance == null) {
            instance = new AtbPersist();
            return instance;
        }
        return instance;
    }


    private Autobuz generateAtb(ResultSet result) throws SQLException {
        int nr_id_sel = Integer.parseInt(result.getString(1));
        String num_plate_sel = result.getString(2);
        String sofer_sel = result.getString(3);
        int locuri_sel = Integer.parseInt(result.getString(4));
        String loc_string = result.getString(5);
        boolean in_rep = Boolean.parseBoolean(result.getString(6));
        boolean in_transit = Boolean.parseBoolean(result.getString(7));

        String[] extractedLocatii = loc_string.split(",");
        List<Locatie> actualLoc = new ArrayList<>();
        for(int i = 0; i < extractedLocatii.length; ++i) {
            actualLoc.add(new Locatie(extractedLocatii[i], i));
        }
        Autobuz atb = new Autobuz(num_plate_sel, locuri_sel, actualLoc);
        atb.setSoferCurent(sofer_sel);
        atb.setInReparatii(in_rep);
        atb.setInTransit(in_transit);
        atb.setSerialNumber(nr_id_sel);

        return atb;
    }


    public ArrayList<Autobuz> getAllAtb() {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(ALL_STATEMENT)) {
            try(ResultSet result = prepSt.executeQuery()) {
                if (!result.next()) {
                    System.out.println("eroare la result.next()");
                    return null;
                } else {
                    ArrayList<Autobuz> lista = new ArrayList<>();
                    while (result.next()) {
                        lista.add(generateAtb(result));
                    }
                    return lista;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }



    public void insertAtb(Autobuz atb) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            prepSt.setString(1, String.valueOf( atb.getSerialNumber()));
            prepSt.setString(2, atb.getNumberPlate());
            prepSt.setString(3, atb.getSoferCurent());
            prepSt.setString(4, String.valueOf(atb.getNrLocuri()));

            Set<Locatie> loc = atb.getLocatii();
            StringBuilder temp = new StringBuilder();
            // preiau numele de locatii intr-o lista pt a-l trimite in baza de date
            for (Locatie i : loc) {
                temp.append(i.toString()).append(",");
            }
            prepSt.setString(5, temp.toString());

            prepSt.setString(6, atb.getInReparatii().toString());
            prepSt.setString(7, atb.getInTransit().toString());
            //System.out.println("are men");
            int rowsInserted = prepSt.executeUpdate();
            //System.out.println("nush ce are men");
            if (rowsInserted > 0) {
                System.out.println("A new autobuz was inserted successfully!");
            }
            else {
                System.out.println("nu l-am inserat pt ca nush de ce");
            }
        }
        catch (SQLException  e) {
            System.out.println(e.getMessage());
        }
    }

    public Autobuz findAtbWith(String number_plate) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            prepSt.setString(1, String.valueOf(number_plate));

            try(ResultSet result = prepSt.executeQuery()) {
                if(!result.next()) {
                    System.out.println("eroare la result.next()");
                    return null;
                }
                else {
                    return generateAtb(result);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public boolean delAtb(String num_plate) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_STATEMENT)) {
            prepSt.setString(1, num_plate);

            int rowsDel = prepSt.executeUpdate();
            if(rowsDel > 0) {
                System.out.println("am sters cu succes");
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println("Eroare SQL la stergere");
            return false;
        }

        System.out.println("Eroare la stergere, poate nu l-am gasit");
        return false;
    }


    public void updateAtb(String num_plate, String sofer_nou) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_STATEMENT)) {
            // primu e soferul al doilea arg este num_plate
            prepSt.setString(1, sofer_nou);
            prepSt.setString(2, num_plate);

            int rowsUpdated = prepSt.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Sofer was updated successfully!");
            }
            else {
                System.out.println("Did not find autobuz");
            }
        }
        catch (SQLException e) {
            System.out.println("Err in update " + e.getMessage());
        }
    }

}
