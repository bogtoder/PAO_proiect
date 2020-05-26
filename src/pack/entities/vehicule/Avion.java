package pack.entities.vehicule;


import pack.entities.Colet;
import pack.interfaces.TransportMarfa;
import pack.loc.Locatie;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Avion extends Vehicul implements TransportMarfa {

    // pentru marfa momentan

    private static Integer nrAv = 0;

    private String nume;

    private String pilot, copilot;

    private Integer masaLibera;
    private Integer masaMaxima;

    private Double constantaLivrare;

    private Set<Locatie> locatii;

    private List<Colet> colete;

    public Avion(String nume, Integer masaMaxima, List<Locatie> locatii) {
        super(nrAv++ + 5000, locatii.get(0));
        this.nume = nume;
        this.pilot = null;
        this.copilot = null;
        this.masaMaxima = masaMaxima;
        this.masaLibera = masaMaxima;
        this.locatii = new LinkedHashSet<>(locatii);
        this.colete = new ArrayList<>();
        this.constantaLivrare = Math.random() * 100;
        // pot sa primesc si dubluri, memorez doar locatiile unice
    }

    public Avion() {
        this("placeHolder", -1, new ArrayList<>());
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }


    public Integer getMasaMaxima() {
        return masaMaxima;
    }

    public void setMasaMaxima(Integer masaMaxima) {
        this.masaMaxima = masaMaxima;
    }

    public Integer getMasaLibera() {
        return masaLibera;
    }

    public Set<Locatie> getLocatii() {
        return locatii;
    }

    public void setLocatii(LinkedHashSet<Locatie> locatii) {
        this.locatii = locatii;
    }

    public String getPilot() {
        return pilot;
    }

    public void setPilot(String pilot) {
        this.pilot = pilot;
    }

    public String getCopilot() {
        return copilot;
    }

    public void setCopilot(String copilot) {
        this.copilot = copilot;
    }

    public Double getConstantaLivrare() {
        return constantaLivrare;
    }

    public void setConstantaLivrare(Double constantaLivrare) {
        this.constantaLivrare = constantaLivrare;
    }

    @Override
    public void loadItem(Colet item) {
        if(item.getMasa() > this.masaLibera) {
            System.out.println("Nu incape");
        }
        else {
            this.masaLibera -= item.getMasa();
            colete.add(item);
        }
    }


    @Override
    public Double pretTransport(Colet item) {
        return this.constantaLivrare * item.getMasa();
    }

    @Override
    public String printColete() {
        //System.out.println(this.nume + " are: ");
        StringBuilder temp = null;
        for(Colet i : colete) {
            System.out.print(i.getNumber() + "," + i.getMasa() + ",");
            temp.append(i.getNumber()).append(",").append(i.getMasa()).append(",");
        }
        //System.out.println();
        if(temp != null) return temp.toString();
        return null;
    }

    @Override
    public String toString() {
        return super.toString() + nume + "," + pilot + "," + copilot + "," + masaLibera + "," + masaMaxima + "," + constantaLivrare + "," + locatii + "," + colete + "\n";
    }
}
