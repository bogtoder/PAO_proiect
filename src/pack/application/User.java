package pack.application;

import pack.entities.vehicule.*;

import pack.loc.Locatie;
import pack.persistence.FileService;

import java.io.IOException;
import java.util.*;


public class User {
    // aici este clasa de servicii ale companiei
    private Scanner cin = new Scanner(System.in);

    private Integer nrTotalLocatii = 1;
    private LinkedList<Locatie> listaLocatii = new LinkedList<>();

    private List<Vehicul> listaVehicule;
    private List<Vehicul> vehiculeMarfa = new LinkedList<>();
    private List<Vehicul> vehiculePersoane = new LinkedList<>();

    private static FileService file_service = null;

    private String VEHICULE_PATH = "src\\pack\\csvFiles\\vehicule.csv";


    public User() throws IOException {
        this.vehiculeMarfa = new LinkedList<>();
        this.vehiculePersoane = new LinkedList<>();

        file_service = FileService.getInstance();
        file_service.changePath(VEHICULE_PATH);
        this.listaVehicule = file_service.copiazaVehicule(VEHICULE_PATH); // copiez ce vehicule am salvate deja

        // reconstruiesc celelalte 2 liste dupa serialNumber, n-are rost sa le memorez separat
        for(Vehicul veh : listaVehicule) {
            if(veh.getSerialNumber() < 5000) {
                vehiculePersoane.add(veh);
            }
            else {
                vehiculeMarfa.add(veh);
            }
        }

        file_service.audit("start_main_service");
        file_service.audit("copiat_vehicule");
    }


    public void addVehicul(int tip) throws IOException {
        if(tip == 1) {
            //este autobuz
            Autobuz atb = addAutobuz();
            listaVehicule.add(atb);
            vehiculePersoane.add(atb);
            file_service.scrieAutobuz(atb);
            file_service.audit("adaug_autobuz");
        }
        else if(tip == 2) {
            // este nava
            Nava nv = addNava();
            listaVehicule.add(nv);
            vehiculePersoane.add(nv);
            file_service.scrieNava(nv);
            file_service.audit("adaug_nava");
        }
        else if(tip == 3) {
            // este avion
            Avion av = addAvion();
            listaVehicule.add(av);
            vehiculeMarfa.add(av);
            file_service.scrieAvion(av);
            file_service.audit("adaug_avion");
        }
        else if(tip == 4) {
            // este duba
            Duba db = addDuba();
            listaVehicule.add(db);
            vehiculeMarfa.add(db);
            file_service.scrieDuba(db);
            file_service.audit("adaug_duba");
        }
    }                               // 1

    public void printVehicule() throws IOException {

        Collections.sort(listaVehicule);

        for(Vehicul veh : listaVehicule) {
            System.out.print(veh.getSerialNumber()+ ": ");
            if(veh instanceof Autobuz) System.out.println(((Autobuz) veh).getNumberPlate());
            if(veh instanceof Nava) System.out.println(((Nava) veh).getNume());
            if(veh instanceof Avion) System.out.println(((Avion) veh).getNume());
            if(veh instanceof Duba) System.out.println(((Duba) veh).getNumberPlate());
        }

        file_service.audit("afisat_vehicule_toate");
        file_service.scrieToateVehiculele(listaVehicule);
    }                                   // 2

    public void printVehiculeMarfa() {

        Collections.sort(vehiculeMarfa);

        System.out.println("Avioane marfa: ");
        int i;
        for(i = 0; i < vehiculeMarfa.size() && vehiculeMarfa.get(i).getSerialNumber() < 10000; ++i) {
            System.out.println(vehiculeMarfa.get(i).getSerialNumber());
        }
        System.out.println("Dube marfa: ");
        for(;i < vehiculeMarfa.size(); ++i) {
            System.out.println(vehiculeMarfa.get(i).getSerialNumber());
        }

        System.out.println();

        file_service.audit("afisat_veh_marfa");

    }                              // 3

    public void printVehiculePersoane() {

        Collections.sort(vehiculePersoane);

        System.out.println("Autobuze: ");
        int i;
        for(i = 0; i < vehiculePersoane.size() && vehiculePersoane.get(i).getSerialNumber() < 5000; ++i) {
            System.out.println(vehiculePersoane.get(i).getSerialNumber());
        }
        System.out.println("Nave: ");
        for(;i < vehiculePersoane.size(); ++i) {
            System.out.println(vehiculePersoane.get(i).getSerialNumber());
        }

        System.out.println();

        file_service.audit("afis_veh_persoane");

    }                           // 4

    public void delVehicul(Integer serialNumber) throws IOException {

        int i = 0, ok = 0;
        while(i < listaVehicule.size()) {

            if(listaVehicule.get(i).getSerialNumber() == serialNumber) {
                listaVehicule.remove(i);
                ok = 1;
                System.out.println("Sters cu succes");
                break;
            }
            else i++;
        }
        if(ok == 0) {
            System.out.println("Nu am gasit serial " + serialNumber);
        }

        file_service.clearFisier(VEHICULE_PATH);
        file_service.scrieToateVehiculele(listaVehicule);

        file_service.audit("sters_vehicul");

    }                  // 5

    public void addLocatie(String nume) {
        returnLocationWithName(nume); // mai mult o redefinire


        file_service.audit("adaugat_locatie");

    }                           // 6

    public void printLocatii() {
        System.out.println("Avem " + listaLocatii.size() + " locatii: ");
        for(Locatie loc : listaLocatii) {
            System.out.println(loc.getLocationNumber() + ": " + loc.getNume());
            System.out.println();
        }

        file_service.audit("adaugat_locatii");
    }                                    // 7

    public void delLocatie(String nume) {

        for(int i = 0; i < listaLocatii.size();) {
            if(listaLocatii.get(i).getNume().equals(nume)) {
                listaLocatii.remove(i);
                break;
            }
            else i++;
        }

        file_service.audit("sters_locatie");
    }                           // 8


    // random functions

    private Autobuz addAutobuz() {
        System.out.print("Number plate: ");
        String numberPlate = cin.nextLine();
        cin.reset();

        System.out.print("Nr locuri: ");
        Integer locuri = cin.nextInt();
        cin.reset();

        List<Locatie> locatii = readMeSomeLocatii();

        Autobuz autb = new Autobuz(numberPlate, locuri, locatii);

        for(Locatie loc : locatii) {
            loc.addVeh(autb);
        }

        System.out.println("Am introdus: " + numberPlate + ", serial num.  " + autb.getSerialNumber());

        return autb;
    }

    private Nava addNava() {
        System.out.print("Nume: ");
        String numberPlate = cin.nextLine();
        cin.reset();

        System.out.print("Nr locuri: ");
        Integer locuri = cin.nextInt();
        cin.reset();

        List<Locatie> locatii = readMeSomeLocatii();

        Nava nv = new Nava(numberPlate, locuri, locatii);

        for(Locatie loc : locatii) {
            loc.addVeh(nv);
        }

        System.out.print("Am introdus: " + numberPlate + ", serial num.  " + nv.getSerialNumber() );

        return nv;
    }

    private Avion addAvion() {
        System.out.print("Nume: ");
        String numberPlate = cin.nextLine();
        cin.reset();

        System.out.print("Masa acceptata: ");
        Integer locuri = cin.nextInt();
        cin.reset();

        List<Locatie> locatii = readMeSomeLocatii();

        Avion av = new Avion(numberPlate, locuri, locatii);

        for(Locatie loc : locatii) {
            loc.addVeh(av);
        }

        System.out.print("Am introdus: " + numberPlate + ", serial num.  " + av.getSerialNumber());

        return av;
    }

    private Duba addDuba() {
        System.out.print("Number Plate: ");
        String numberPlate = cin.nextLine();
        cin.reset();

        System.out.print("Masa acceptata: ");
        Integer locuri = cin.nextInt();
        cin.reset();

        List<Locatie> locatii = readMeSomeLocatii();

        Duba db = new Duba(numberPlate, locuri, locatii);

        for(Locatie loc : locatii) {
            loc.addVeh(db);
        }

        System.out.print("Am introdus: " + numberPlate + ", serial num.  " + db.getSerialNumber());

        return db;
    }

    private List<Locatie> readMeSomeLocatii() {
        int numberOf;
        System.out.println("Cate orase :");
        numberOf = cin.nextInt();

        if(numberOf == 0) return null;

        List<Locatie> locatii = new ArrayList<>();

        String oras;
        oras = cin.nextLine();

        for(int i = 0; i < numberOf; ++i) {
            oras = cin.nextLine().toLowerCase();
            locatii.add(returnLocationWithName(oras));
        }
        return locatii;
    }

    private Locatie returnLocationWithName(String nume) {
        for(Locatie loc : listaLocatii) {
            if(loc.getNume().equals(nume)) {
                return loc;
            }
        }
        // System.out.println("Nu am gasit, adaug noua");
        Locatie newLoc = new Locatie(nume, nrTotalLocatii++); // creez inca o locatie doar daca este nevoie
        listaLocatii.add(newLoc);
        return newLoc;
    }

}
