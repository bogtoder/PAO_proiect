package pack.persistence;

import pack.entities.Colet;
import pack.entities.vehicule.*;
import pack.loc.Locatie;

import java.io.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FileService {


    // static variable single_instance of type Singleton
    private static FileService file_service_instance = null;

    // variable of type String

    private String VEHICULE_PATH = "src\\pack\\csvFiles\\vehicule.csv";

    private static BufferedReader fin;
    private static BufferedWriter fout;
    private BufferedWriter audit_out;


    private FileService() throws IOException {
        try {
            fin = new BufferedReader(new FileReader(VEHICULE_PATH));
            String AUDIT_PATH = "src\\pack\\csvFiles\\audit.csv";
            File file = new File(AUDIT_PATH);
            audit_out = new BufferedWriter(new FileWriter(AUDIT_PATH));
        }
        catch (FileNotFoundException f) {
            System.out.println("Nu am gasit fisierul, fac eu unu");
            File temp = new File(VEHICULE_PATH);
            this.audit("created " + VEHICULE_PATH);
        }

    }

    public static FileService getInstance() throws IOException {

        if (file_service_instance == null)
            file_service_instance = new FileService();

        return file_service_instance;
    }


    public void changePath(String PATH) {
        this.VEHICULE_PATH = PATH;
    }


    public void scrieAutobuz(Autobuz autobuz) throws IOException {
        fout = new BufferedWriter(new FileWriter(VEHICULE_PATH));
        fout.write(autobuz.toString());
        fout.flush();
        fout.close();
    }

    public void scrieNava(Nava nv) throws IOException {
        fout = new BufferedWriter(new FileWriter(VEHICULE_PATH));
        fout.write(nv.toString());
        fout.flush();
        fout.close();
    }

    public void scrieAvion(Avion av) throws IOException {
        fout = new BufferedWriter(new FileWriter(VEHICULE_PATH));
        fout.write(av.toString());
        fout.flush();
        fout.close();
    }

    public void scrieDuba(Duba db) throws IOException {
        fout = new BufferedWriter(new FileWriter(VEHICULE_PATH));
        fout.write(db.toString());
        fout.flush();
        fout.close();
    }



    public LinkedList<Vehicul> copiazaVehicule(String fileName) throws IOException {

        // din fisierul fin in arraryul trimis

        fin = new BufferedReader(new FileReader(fileName));

        LinkedList<Vehicul> restored = new LinkedList<>();

        String linie;
        while((linie = fin.readLine()) != null) {
            String[] extract = linie.split(",");

            System.out.println("Am obtinut: ");
            for(String str : extract) {
                System.out.print(str + "|");
            }

            int locNb = 0;
            int cod = Integer.parseInt(extract[0].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
            Boolean repar = Boolean.parseBoolean(extract[1].replaceAll("[^a-zA-Z]", "").toLowerCase());
            Boolean trans = Boolean.parseBoolean(extract[2].replaceAll("[^a-zA-Z]", "").toLowerCase());
            //String orasCurent = extract[3];

            if (cod < 2000) {
                // e autobuz
                String numPlate = extract[4].replaceAll("[^a-zA-Z]", "").toLowerCase();
                String sofCurent = extract[5].replaceAll("[^a-zA-Z]", "").toLowerCase();
                int nrLocuri = Integer.parseInt(extract[6].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());

                ArrayList<Locatie> locuri = new ArrayList<>();
                for(int i = 7; i < extract.length; ++i) {
                    if(extract[i].length() > 1) {
                        String prelucrat = extract[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
                        Locatie loc = new Locatie(prelucrat, locNb++);
                        locuri.add(loc);
                    }
                }

                Autobuz atb = new Autobuz(numPlate, nrLocuri, locuri);
                atb.setSoferCurent(sofCurent);
                atb.setInReparatii(repar);
                atb.setInTransit(trans);
                atb.setSerialNumber(cod);
                atb.setLocatieCurenta(locuri.get(0));

                restored.add(atb);
            }
            else if (cod < 5000) {
                // e nava
                String numPlate = extract[4].replaceAll("[^a-zA-Z]", "").toLowerCase();
                String sofCurent = extract[5].replaceAll("[^a-zA-Z]", "").toLowerCase();
                int nrLocuri = Integer.parseInt(extract[6].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());

                ArrayList<Locatie> locuri = new ArrayList<>();
                for(int i = 7; i < extract.length; ++i) {
                    if(extract[i].length() > 1) {
                        String prelucrat = extract[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
                        Locatie loc = new Locatie(prelucrat, locNb++);
                        locuri.add(loc);
                    }
                }

                Nava nv = new Nava(numPlate, nrLocuri, locuri);
                nv.setCapitan(sofCurent);
                nv.setInReparatii(repar);
                nv.setInTransit(trans);
                nv.setSerialNumber(cod);
                nv.setLocatieCurenta(locuri.get(0));

                restored.add(nv);
            }
            else if (cod < 10000) {
                // e avion
                String numPlate = extract[4].replaceAll("[^a-zA-Z]", "").toLowerCase();
                String pilot = extract[5].replaceAll("[^a-zA-Z]", "").toLowerCase();
                String copilot = extract[6].replaceAll("[^a-zA-Z]", "").toLowerCase();
                //int masaLibera = Integer.parseInt(extract[8].replaceAll("[^a-zA-Z]", "").toLowerCase());
                int masaMax = Integer.parseInt(extract[8].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                Double cons = Double.parseDouble(extract[9].replaceAll("[^a-zA-Z0-9.]", "").toLowerCase());

                ArrayList<Locatie> locuri = new ArrayList<>();
                int i = 10;
                for(; i < extract.length; ++i) {
                    if(extract[i].contains("[")  && i > 8) {
                        break;
                        // am ajuns la colete
                    }
                    if(extract[i].length() > 1) {
                        String prelucrat = extract[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
                        Locatie loc = new Locatie(prelucrat, locNb++);
                        locuri.add(loc);
                    }
                }

                Avion av = new Avion(numPlate, masaMax, locuri);
                av.setPilot(pilot);
                av.setCopilot(copilot);
                av.setInReparatii(repar);
                av.setInTransit(trans);
                av.setSerialNumber(cod);
                av.setConstantaLivrare(cons);
                av.setLocatieCurenta(locuri.get(0));

                for(; i < extract.length;) {
                    int coletNumberPrelucrat = -1;
                    int coletMasaPrelucrat;
                    if(extract[i].length() > 1) {
                        coletNumberPrelucrat = Integer.parseInt(extract[i].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                        i++;
                    }
                    if(extract[i].length() > 1) {
                        coletMasaPrelucrat = Integer.parseInt(extract[i].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                        i++;
                        Colet col = new Colet(coletNumberPrelucrat, coletMasaPrelucrat);
                        av.loadItem(col);
                    }
                }

                restored.add(av);
            }
            else {
                // e avion
                String numPlate = extract[4].replaceAll("[^a-zA-Z]", "").toLowerCase();
                String sofer = extract[5].replaceAll("[^a-zA-Z]", "").toLowerCase();
                //int masaLibera = Integer.parseInt(extract[8].replaceAll("[^a-zA-Z]", "").toLowerCase());
                int masaMax = Integer.parseInt(extract[6].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                Double conLiv = Double.parseDouble(extract[8].replaceAll("[^a-zA-Z0-9.]", "").toLowerCase());

                ArrayList<Locatie> locuri = new ArrayList<>();
                int i = 9;
                for(; i < extract.length; ++i) {
                    if(extract[i].contains("[")  && i > 8) {
                        break;
                        // am ajuns la colete
                    }
                    if(extract[i].length() > 1) {
                        String prelucrat = extract[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
                        Locatie loc = new Locatie(prelucrat, locNb++);
                        locuri.add(loc);
                    }
                }

                Duba db = new Duba(numPlate, masaMax, locuri);
                db.setSoferCurent(sofer);
                db.setInReparatii(repar);
                db.setInTransit(trans);
                db.setSerialNumber(cod);
                db.setConstantaLivrare(conLiv);
                db.setLocatieCurenta(locuri.get(0));

                for(; i < extract.length;) {
                    int coletNumberPrelucrat = -1;
                    int coletMasaPrelucrat;
                    if(extract[i].length() > 1) {
                        coletNumberPrelucrat = Integer.parseInt(extract[i].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                        i++;
                    }
                    if(extract[i].length() > 1) {
                        coletMasaPrelucrat = Integer.parseInt(extract[i].replaceAll("[^a-zA-Z0-9]", "").toLowerCase());
                        i++;
                        Colet col = new Colet(coletNumberPrelucrat, coletMasaPrelucrat);
                        db.loadItem(col);
                    }
                }

                restored.add(db);
            }
        }
        return restored;
    }


    public void audit(String action) {
        Date d = new Date();
        long time = d.getTime();
        Timestamp ts = new Timestamp(time);
        try {
            audit_out.write(action + " " + ts.toString() + "\n");
            audit_out.flush();
            //audit_out.close();
        }
        catch (IOException e) {
            System.out.println("N-am putut creea audit.csv");
        }
        catch (NullPointerException e) {
            System.out.println("Null");
        }
    }

    public void clearFisier(String PATH) {
        try {
            FileWriter fw = new FileWriter(PATH);
            fw.close();
        }
        catch (IOException e) {
        }
    }

    public void scrieToateVehiculele(List<Vehicul> lista) throws IOException {
        this.audit("scris_vehicule_toate");

        for(Vehicul veh : lista) {
            if(veh.getSerialNumber() < 2000) {
                scrieAutobuz((Autobuz)veh);
            }
            else if (veh.getSerialNumber() < 5000) {
                scrieNava((Nava)veh);
            }
            else if (veh.getSerialNumber() < 10000) {
                scrieAvion((Avion)veh);
            }
            else {
                scrieDuba((Duba)veh);
            }
        }
    }

}

