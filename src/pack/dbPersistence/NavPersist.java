package pack.dbPersistence;

import pack.databaseConnection.DatabaseConnection;
import pack.entities.vehicule.Autobuz;
import pack.entities.vehicule.Nava;
import pack.loc.Locatie;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NavPersist {

    private static final String INSERT_STATEMENT = "INSERT INTO nave (nr_id, nume, capitan, nr_locuri, locatii, in_service, in_transit) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_STATEMENT = "SELECT * FROM nave WHERE number_plate = ?";
    private static final String UPDATE_STATEMENT = "UPDATE nave SET capitan = ? WHERE nume = ?";
    private static final String DELETE_STATEMENT = "DELETE FROM nave WHERE nume = ?";

    private static NavPersist instance;

    private NavPersist() {
    }

    public static NavPersist getInstance() {
        if(instance == null) {
            instance = new NavPersist();
            return instance;
        }
        else {
            return instance;
        }
    }

    public void insertNav(Nava nav) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(INSERT_STATEMENT)) {
            prepSt.setString(1, String.valueOf(nav.getSerialNumber()));
            prepSt.setString(2, nav.getNume());
            prepSt.setString(3, nav.getCapitan());
            prepSt.setString(4, String.valueOf(nav.getNrLocuri()));

            Set<Locatie> loc = nav.getLocatii();
            StringBuilder temp = new StringBuilder();
            // preiau numele de locatii intr-o lista pt a-l trimite in baza de date
            for (Locatie i : loc) {
                temp.append(i.toString()).append(",");
            }
            prepSt.setString(5, temp.toString());

            prepSt.setString(6, nav.getInReparatii().toString());
            prepSt.setString(7, nav.getInTransit().toString());

            int rowsInserted = prepSt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new nava was inserted successfully!");
            }
            else {
                System.out.println("nu l-am inserat pt ca nush de ce");
            }
        }
        catch (SQLException  e) {
            System.out.println(e.getMessage());
        }

    }

    public Nava findNavWith(String name) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(SELECT_STATEMENT)) {
            prepSt.setString(1, String.valueOf(name));

            try(ResultSet result = prepSt.executeQuery()) {
                if(!result.next()) {
                    System.out.println("eroare la result.next()");
                    return null;
                }
                else {
                    System.out.println("found nava cu nume " + name);
                    int nr_id_sel = Integer.parseInt(result.getString(1));
                    String name_sel = result.getString(2);
                    String capitan_sel = result.getString(3);
                    int locuri_sel = Integer.parseInt(result.getString(4));
                    String loc_string = result.getString(5);
                    boolean in_rep = Boolean.parseBoolean(result.getString(6));
                    boolean in_transit = Boolean.parseBoolean(result.getString(7));

                    String[] extractedLocatii = loc_string.split(",");
                    List<Locatie> actualLoc = new ArrayList<>();
                    for(int i = 0; i < extractedLocatii.length; ++i) {
                        actualLoc.add(new Locatie(extractedLocatii[i], i));
                    }

                    Nava nav = new Nava(name_sel, locuri_sel, actualLoc);
                    nav.setCapitan(capitan_sel);
                    nav.setInReparatii(in_rep);
                    nav.setInTransit(in_transit);
                    nav.setSerialNumber(nr_id_sel);

                    return nav;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public boolean delNav(String name) {
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


    public void updateNav(String name, String capitan_nou) {
        try(PreparedStatement prepSt = DatabaseConnection.getInstance().getConnection().prepareStatement(UPDATE_STATEMENT)) {
            prepSt.setString(1, capitan_nou);
            prepSt.setString(2, name);

            int rowsUpdated = prepSt.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Capitan was updated successfully!");
            }
            else {
                System.out.println("Did not find nava");
            }
        }
        catch (SQLException e) {
            System.out.println("Err in update " + e.getMessage());
        }
    }


}
