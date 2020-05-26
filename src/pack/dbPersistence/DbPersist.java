package pack.dbPersistence;

import pack.databaseConnection.DatabaseConnection;
import pack.entities.vehicule.Autobuz;
import pack.entities.vehicule.Duba;
import pack.loc.Locatie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DbPersist {

    private static final String INSERT_STATEMENT = "INSERT INTO dube (nr_id, number_plate, sofer, masa_maxima, locatii, in_service, in_transit, taxa_livr) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM dube WHERE number_plate = ?";
    private static final String UPDATE_STATEMENT = "UPDATE dube SET sofer = ? WHERE number_plate = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM dube WHERE number_plate = ?";

    private static DbPersist instance;

    private DbPersist() {

    }

    public static DbPersist getInstance() {
        if(instance == null) {
            instance = new DbPersist();
            return instance;
        }
        return instance;
    }


    public void insertDb(Duba db) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            prepSt.setString(1, String.valueOf( db.getSerialNumber()));
            prepSt.setString(2, db.getNumberPlate());
            prepSt.setString(3, db.getSoferCurent());
            prepSt.setString(4, String.valueOf(db.getMasaMaxima()));

            Set<Locatie> loc = db.getLocatii();
            StringBuilder temp = new StringBuilder();
            // preiau numele de locatii intr-o lista pt a-l trimite in baza de date
            for (Locatie i : loc) {
                temp.append(i.toString()).append(",");
            }
            prepSt.setString(5, temp.toString());

            prepSt.setString(6, db.getInReparatii().toString());
            prepSt.setString(7, db.getInTransit().toString());
            prepSt.setString(8, String.valueOf(db.getConstantaLivrare()));

            int rowsInserted = prepSt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new duba was inserted successfully!");
            }
            else {
                System.out.println("nu l-am inserat pt ca nush de ce");
            }
        }
        catch (SQLException  e) {
            System.out.println(e.getMessage());
        }
    }

    public Duba findDbWith(String number_plate) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            prepSt.setString(1, String.valueOf(number_plate));

            try(ResultSet result = prepSt.executeQuery()) {
                if(!result.next()) {
                    System.out.println("eroare la result.next()");
                    return null;
                }
                else {
                    System.out.println("found duba cu " + number_plate);
                    int nr_id_sel = Integer.parseInt(result.getString(1));
                    String num_plate_sel = result.getString(2);
                    String sofer_sel = result.getString(3);
                    int masa_max_sel = Integer.parseInt(result.getString(4));
                    String loc_string = result.getString(5);
                    boolean in_rep = Boolean.parseBoolean(result.getString(6));
                    boolean in_transit = Boolean.parseBoolean(result.getString(7));
                    Double taxa_liv_sel = Double.parseDouble(result.getString(8));

                    String[] extractedLocatii = loc_string.split(",");
                    List<Locatie> actualLoc = new ArrayList<>();
                    for(int i = 0; i < extractedLocatii.length; ++i) {
                        actualLoc.add(new Locatie(extractedLocatii[i], i));
                    }

                    Duba db = new Duba(num_plate_sel, masa_max_sel, actualLoc);
                    db.setSoferCurent(sofer_sel);
                    db.setInReparatii(in_rep);
                    db.setInTransit(in_transit);
                    db.setSerialNumber(nr_id_sel);
                    db.setConstantaLivrare(taxa_liv_sel);

                    return db;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public boolean delDb(String num_plate) {
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


    public void updateDb(String num_plate, String sofer_nou) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_STATEMENT)) {
            // primu e soferul al doilea arg este num_plate
            prepSt.setString(1, sofer_nou);
            prepSt.setString(2, num_plate);

            int rowsUpdated = prepSt.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Sofer was updated successfully!");
            }
            else {
                System.out.println("Did not find duba");
            }
        }
        catch (SQLException e) {
            System.out.println("Err in update " + e.getMessage());
        }
    }


}
