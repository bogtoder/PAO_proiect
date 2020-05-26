package pack.dbPersistence;

import pack.databaseConnection.DatabaseConnection;
import pack.entities.Colet;
import pack.entities.vehicule.Autobuz;
import pack.entities.vehicule.Avion;
import pack.loc.Locatie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AvPersist {

    private static final String INSERT_STATEMENT = "INSERT INTO avioane (nr_id, nume, pilot, copilot, masa_max, locatii, taxa_liv, colete, in_service, in_transit) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM avioane WHERE nume = ?";
    private static final String UPDATE_STATEMENT = "UPDATE avioane SET pilot = ? WHERE nume = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM avioane WHERE nume = ?";

    private static AvPersist instance;

    private AvPersist() {
    }

    public static AvPersist getInstance() {
        if(instance == null) {
            instance = new AvPersist();
            return instance;
        }
        else {
            return instance;
        }
    }

    public void insertAv(Avion av) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            prepSt.setString(1, String.valueOf(av.getSerialNumber()));
            prepSt.setString(2, av.getNume());
            prepSt.setString(3, av.getPilot());
            prepSt.setString(4, av.getCopilot());
            prepSt.setString(5, String.valueOf(av.getMasaMaxima()));

            Set<Locatie> loc = av.getLocatii();
            StringBuilder temp = new StringBuilder();
            // preiau numele de locatii intr-o lista pt a-l trimite in baza de date
            for (Locatie i : loc) {
                temp.append(i.toString()).append(",");
            }
            prepSt.setString(6, temp.toString());

            prepSt.setString(7, String.valueOf(av.getConstantaLivrare()));
            prepSt.setString(8, av.printColete());
            prepSt.setString(9, av.getInReparatii().toString());
            prepSt.setString(10, av.getInTransit().toString());
            // System.out.println("are men");
            int rowsInserted = prepSt.executeUpdate();
            // System.out.println("nush ce are men");
            if (rowsInserted > 0) {
                System.out.println("A new avion was inserted successfully!");
            }
            else {
                System.out.println("nu l-am inserat pt ca nush de ce");
            }
        }
        catch (SQLException  e) {
            System.out.println(e.getMessage());
        }

    }

    public Avion findAvWith(String nume) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            prepSt.setString(1, String.valueOf(nume));

            try(ResultSet result = prepSt.executeQuery()) {
                if(!result.next()) {
                    System.out.println("eroare la result.next()");
                    return null;
                }
                else {
                    System.out.println("found avion cu nume " + nume);
                    int nr_id_sel = Integer.parseInt(result.getString(1));
                    String nume_sel = result.getString(2);
                    String pil_sel = result.getString(3);
                    String cop_sel = result.getString(4);
                    int masa_max_sel = Integer.parseInt(result.getString(5));
                    String loc_string = result.getString(6);
                    Double taxa_sel = Double.parseDouble(result.getString(7));
                    String colete_string = result.getString(8);
                    boolean in_rep = Boolean.parseBoolean(result.getString(9));
                    boolean in_transit = Boolean.parseBoolean(result.getString(10));

                    String[] extractedLocatii = loc_string.split(",");
                    List<Locatie> actualLoc = new ArrayList<>();
                    for(int i = 0; i < extractedLocatii.length; ++i) {
                        actualLoc.add(new Locatie(extractedLocatii[i], i));
                    }

                    Avion av = new Avion(nume_sel, masa_max_sel, actualLoc);
                    av.setPilot(pil_sel);
                    av.setCopilot(cop_sel);
                    av.setInReparatii(in_rep);
                    av.setInTransit(in_transit);
                    av.setSerialNumber(nr_id_sel);

                    String[] extractedColete = colete_string.split(",");
                    // List<Colet> acutalColete = new ArrayList<>();
                    for(int i = 0; i < extractedColete.length; i += 2) {
                        // nr colet, masa_colet, de asta iau cate 2
                        int nr_col = Integer.parseInt(extractedColete[i]);
                        int masa_col = Integer.parseInt(extractedColete[i + 1]);

                        Colet c = new Colet(nr_col, masa_col);
                        av.loadItem(c);
                    }

                    return av;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public boolean delAv(String name) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(DELETE_STATEMENT)) {
            prepSt.setString(1, name);

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


    public void updateAv(String name, String pilot_nou) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_STATEMENT)) {
            // primu e soferul al doilea arg este num_plate
            prepSt.setString(1, pilot_nou);
            prepSt.setString(2, name);

            int rowsUpdated = prepSt.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Pilot was updated successfully!");
            }
            else {
                System.out.println("Did not find avion");
            }
        }
        catch (SQLException e) {
            System.out.println("Err in update " + e.getMessage());
        }
    }


}
