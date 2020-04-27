package pack.entities.vehicule;

import pack.interfaces.TransportPersoane;
import pack.loc.Locatie;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class Autobuz extends Vehicul implements TransportPersoane {

    private static Integer nrAutobuze = 0;

    private String numberPlate;
    private String soferCurent;
    private Integer nrLocuri;

    private Set<Locatie> locatii;

    public Autobuz(String numberPlate, Integer nrLocuri, List<Locatie> locatii) {
        super(nrAutobuze++, locatii.get(0));
        this.numberPlate = numberPlate;
        this.soferCurent = null;
        this.nrLocuri = nrLocuri;
        this.locatii = new LinkedHashSet<>(locatii);
        // pot sa primesc si dubluri, memorez doar locatiile unice
    }

    public Autobuz() {
        this("placeHolder", -1, new ArrayList<>());
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numPlate) {
        if (this.numberPlate.equals("placeHolder")) {
            this.numberPlate = numberPlate;
        }
        else {
            System.out.println("Are deja numar, nu poate fi modificat");
        }
    }

    public String getSoferCurent() {
        return soferCurent;
    }

    public void setSoferCurent(String soferCurent) {
        this.soferCurent = soferCurent;
    }

    public static Integer getNrAutobuze() {
        return nrAutobuze;
    }

    public Integer getNrLocuri() {
        return nrLocuri;
    }

    public Set<Locatie> getLocatii() {
        return locatii;
    }

    @Override
    public void printLocuri() {
        System.out.println("Masina " + super.getSerialNumber() + " are " + nrLocuri + " locuri");
    }

    @Override
    public void printLocatiiOprire() {
        System.out.println("Masina " + super.getSerialNumber() + " opreste in: " + locatii);
    }

    @Override
    public String toString() {
        return super.toString() + (numberPlate + "," + soferCurent + "," + nrLocuri + "," + locatii + "\n").replaceAll("[\\u005b\\u005d]", "");
    }
}
