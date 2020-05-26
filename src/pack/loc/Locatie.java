package pack.loc;

import pack.entities.vehicule.Vehicul;

import java.io.Serializable;
import java.util.ArrayList;


public class Locatie implements Serializable {

    private String nume;
    private Integer locationNumber = 0;

    private ArrayList<Vehicul> vehicule; // lista cu vehicule ce au aceasta locatie in lista de locatii

    public Locatie(String nume, Integer locationNumber) {
        this.nume = nume;
        this.locationNumber = locationNumber;
        this.vehicule = new ArrayList<>();
    }

    public String getNume() {
        return nume;
    }

    public Integer getLocationNumber() {
        return locationNumber;
    }

    public ArrayList<Vehicul> getVehicule() {
        return vehicule;
    }

    public void addVeh(Vehicul veh) {
        this.vehicule.add(veh);
    }

    @Override
    public String toString() {
        return nume;
    }
}
