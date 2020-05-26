package pack.entities.vehicule;

import pack.entities.Colet;
import pack.interfaces.TransportMarfa;
import pack.loc.Locatie;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class Duba extends Vehicul implements TransportMarfa {

    // vehicul de transportat marfa

    private static Integer nrDube = 0;

    private String numberPlate;
    private String soferCurent;
    private Integer masaMaxima;
    private Integer masaLibera;

    private double constantaLivrare;

    private Set<Locatie> locatii;
    private List<Colet> colete;

    public Duba(String numberPlate, Integer masaMaxima, List<Locatie> locatii) {
        super(nrDube++ + 10000, locatii.get(0));
        this.numberPlate = numberPlate;
        this.soferCurent = null;
        this.masaMaxima = masaMaxima;
        this.masaLibera = masaMaxima;
        this.locatii = new LinkedHashSet<>(locatii);
        this.colete = new ArrayList<>();
        this.constantaLivrare = Math.random() * 10;
        // pot sa primesc si dubluri, memorez doar locatiile unice
    }

    public Duba() {
        this("placeHolder", -1, new ArrayList<>());
    }

    public static Integer getNrDube() {
        return nrDube;
    }

    public String getNumberPlate() {
        return numberPlate;
    }


    public Integer getMasaMaxima() {
        return masaMaxima;
    }

    public Set<Locatie> getLocatii() {
        return locatii;
    }

    public void setMasaMaxima(Integer masaMaxima) {
        this.masaMaxima = masaMaxima;
    }

    public void setLocatii(LinkedHashSet<Locatie> locatii) {
        this.locatii = locatii;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getSoferCurent() {
        return soferCurent;
    }

    public void setSoferCurent(String soferCurent) {
        this.soferCurent = soferCurent;
    }

    public double getConstantaLivrare() {
        return constantaLivrare;
    }

    public void setConstantaLivrare(double constantaLivrare) {
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
        return super.toString() + numberPlate + "," + soferCurent + "," + masaMaxima + "," + masaLibera + "," + constantaLivrare + "," + locatii + "," + colete + '\n';
    }
}

