package pack.application;

import java.io.IOException;
import java.util.*;

public class Main {

    // bun deci bafta cu proiectu

    public static void main(String[] args) throws IOException {

        System.out.println();

        User app = new User();
        Scanner cin = new Scanner(System.in);

        System.out.println("Meniu aplicatie: ");
        Integer opt = -1;
        while(opt != 0) {

            printOptiuni();
            opt = cin.nextInt();

            switch (opt) {
                case 0: continue;

                case 1: {
                    printOptAddVehicul();
                    int tip = cin.nextInt();
                    if(tip >= 1 && tip <= 4) app.addVehicul(tip);
                    else System.out.println("Gresit, am iesit");
                    break;
                }

                case 2: {
                    System.out.println("1. toate");
                    System.out.println("2. cele de marfa");
                    System.out.println("3. cele de persoane");
                    int temp = cin.nextInt();

                    switch (temp) {
                        case 1: {
                            app.printVehicule();
                        }
                        case 2: {
                            app.printVehiculeMarfa();
                        }
                        case 3: {
                            app.printVehiculePersoane();
                        }
                        default: {
                            System.out.println("Optiune gresita");
                        }
                    }

                    break;
                }

                case 3: {
                    Integer serial = cin.nextInt();
                    app.delVehicul(serial);
                    break;
                }

                case 4: {
                    app.printLocatii();
                    break;
                }

                case 5: {
                    System.out.print("Numele orasului: ");
                    String tempName = cin.next();
                    app.addLocatie(tempName);
                    break;
                }

                case 6: {
                    System.out.print("Numele orasului: ");
                    String tempName = cin.next();
                    app.delLocatie(tempName);
                    break;
                }

                default: {
                    System.out.println("Optiune gresita");
                    break;
                }
            }
        }

    }


    public static void printOptiuni() {
        System.out.println("0. Exit");
        System.out.println("1. Adauga vehicul");
        System.out.println("2. Print vehicule");
        System.out.println("3. Sterge vehicul (cu serial nr.)");
        System.out.println("4. Print locatii");
        System.out.println("5. Adauga locatie");
        System.out.println("6. Sterge locatie (cu nume)");
        System.out.println("7. Add sofer");
        System.out.println("8. Print soferi");
        System.out.println("9. Sterge sofer");
        System.out.println("10. Imparte soferi la vehicule");
    }


    public static void printOptAddVehicul() {
        System.out.println("1. autobuz");
        System.out.println("2. nava");
        System.out.println("3. avion");
        System.out.println("4. duba");
    }

}
