package pack.entities.vehicule;


import pack.loc.Locatie;
import java.io.Serializable;

public class Vehicul implements Comparable, Serializable {

    private Integer serialNumber; // nr. crt al vehiculului in firma
    private Boolean inReparatii; // true = da, false = nu
    private Boolean inTransit; // true = da...
    private Locatie locatieCurenta; // o locatie din cele valabile
    // sau daca e in tranzit, destinatia urmatoare din lista de locatii


    public Vehicul(Integer serialNumber, Locatie locatieCurenta) {
        this.serialNumber = serialNumber;
        this.locatieCurenta = locatieCurenta;
        this.inReparatii = false;
        this.inTransit = false;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public Boolean getInReparatii() {
        return inReparatii;
    }

    public void setInReparatii(Boolean inReparatii) {
        this.inReparatii = inReparatii;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Boolean getInTransit() {
        return inTransit;
    }

    public void setInTransit(Boolean inTransit) {
        this.inTransit = inTransit;
    }


    public Locatie getLocatieCurenta() {
        return locatieCurenta;
    }

    public void setLocatieCurenta(Locatie locatieCurenta) {
        this.locatieCurenta = locatieCurenta;
    }

    @Override
    public int compareTo(Object o) {
        return this.serialNumber - ((Vehicul)o).getSerialNumber();
    }


    @Override
    public String toString() {
        return serialNumber + "," + inReparatii + "," + inTransit + "," + locatieCurenta + ",";
    }
}
