package pack.entities.vehicule;


import pack.interfaces.TransportPersoane;
import pack.loc.Locatie;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class Nava extends Vehicul implements TransportPersoane {

    private static Integer nrNave = 0;

    private String nume;

    private String capitan;

    private Integer nrLocuri;

    private Set<Locatie> locatii;

    public Nava(String nume, Integer nrLocuri, List<Locatie> locatii) {
        super(nrNave++ + 2000, locatii.get(0));
        this.nume = nume;
        this.capitan = null;
        this.nrLocuri = nrLocuri;
        this.locatii = new LinkedHashSet<>(locatii);
        // pot sa primesc si dubluri, memorez doar locatiile unice
    }

    public Nava() {
        this("placeHolder", -1, new ArrayList<>());
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setCapitan(String capitan) {
        this.capitan = capitan;
    }

    public void setNrLocuri(Integer nrLocuri) {
        this.nrLocuri = nrLocuri;
    }

    @Override
    public void printLocuri() {
        System.out.println("Nava " + super.getSerialNumber() + " are " + nrLocuri + " locuri");
    }

    @Override
    public void printLocatiiOprire() {
        System.out.println("Nava " + super.getSerialNumber() + " opreste in : " + locatii);
    }

    @Override
    public String toString() {
        return super.toString() + nume + "," + capitan + "," + nrLocuri + "," + locatii + "\n";
    }
}
