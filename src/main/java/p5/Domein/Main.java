package p5.Domein;

import p5.Data.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static Connection connection;

    public static void main(String[] args) {
        getConnection();
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        try {
            testReizigerDAO(rdao);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        AdresDAOPsql adao = new AdresDAOPsql(connection);
        try {
            testAdresDAO(adao);
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection);
        testOVChipkaaartDAO(odao);
        ProductDAOPsql pdao = new ProductDAOPsql(connection);
        testProductDAO(pdao);

        closeConnection();
    }

    public static void getConnection() {

        {
            try {
                connection = DriverManager.getConnection("jdbc:postgresql:DP_OV-Chipkaart", "postgres", "tijmendb");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * P2. Reiziger DAO: persistentie van een klasse
     *
     * Deze methode test de CRUD-functionaliteit van de Reiziger DAO
     *
     * @throws SQLException
     */
    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.println();

//        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        try {
            rdao.save(sietske);
        } catch (SQLException throwables) {
            System.out.println("Kon reiziger niet opslaan");
        }
        try {
            reizigers = rdao.findAll();
        } catch (SQLException throwables) {
            System.out.println("Kon lijst met reizigers niet ophalen");
        }
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //update en read reiziger
        System.out.println("\n[Test] Eerst:\n" + rdao.findById(sietske.getId()));
        sietske.setAchternaam("Bakker");
        rdao.update(sietske);
        System.out.println("\nNa ReizigerDAOPsql.update(): \n" + rdao.findById(sietske.getId()) + "\n");

        //findbygbdatum
        System.out.println("[Test] zoeken na alle reizigers met de geboortedatum "+sietske.getGeboortedatum()+":\n");
        System.out.println(rdao.findByGbdatum(sietske.getGeboortedatum().toString()));

        //Delete reiziger
        System.out.print("\n [Test] Eerst " + rdao.findAll().size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        System.out.print(rdao.findAll().size() + " reizigers.\n");

        //Adres tests
        Adres kruisstraat = new Adres(3, "1423AD","12", "Kruisstraat", "Beverdijk",3);
        Date date1 = new Date(1999, 01, 1);
        Reiziger naark = new Reiziger(3, "F", "", "Naark", date1, kruisstraat);
        System.out.println(naark);
        try {
            rdao.save(naark);
        } catch (SQLException throwables) {
            System.out.println("kon reiziger niet opslaan");
        }
        System.out.println("Save reiziger met adres "+rdao.findById(naark.getId()));
        int adresid = naark.getAdres().getId();
        naark.getAdres().setHuisnummer("199");
        try {
            rdao.update(naark);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Update reiziger met adres "+rdao.findById(naark.getId()));
//        rdao.delete(naark);
    }

    private static void testAdresDAO(AdresDAO adao){
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle reizigers op uit de database
        List<Adres> adressen = null;
        try {
            adressen = adao.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("[Test] AdresDAO.findAll() geeft de volgende reizigers:");
        for (Adres r : adressen) {
            System.out.println(r);
        }
        System.out.println();

//        // Maak een nieuw adres aan en persisteer deze in de database
        Adres bakerst = new Adres(2,"2143PP","21B","Bakerstreet","London", 2);
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);
        Date date = new Date(6-1-1854);
        Reiziger cumberbatch = new Reiziger(2, "S","","Holmes", date);
        try {
            rdao.save(cumberbatch);
        } catch (SQLException throwables) {
            System.out.println("Kon reiziger niet opslaan");
        }
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        try {
            adao.save(bakerst);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            adressen = adao.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(adressen.size() + " adressen\n");

        try {
            rdao.save(cumberbatch);
        } catch (SQLException throwables) {
            System.out.println("Kon reiziger niet opslaan");
        }
        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //update en read adres
        try {
            System.out.println("\n[Test] Eerst:\n" + adao.findById(bakerst.getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        bakerst.setPostcode("1234OK");
        try {
            adao.update(bakerst);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            System.out.println("\nNa AdresDAOPsql.update(): \n" + adao.findById(bakerst.getId()) + "\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //findbyreiziger
        System.out.print("[Test] zoeken naar het adres dat hoort bij deze reiziger "+cumberbatch.toString()+":\n");
        try {
            cumberbatch.setAdres(adao.findByReiziger(cumberbatch));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(cumberbatch.toString());
//        Delete adres
        try {
            System.out.print("\n [Test] Eerst " + adao.findAll().size() + " adressen, na AdresDAO.delete() ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            adao.delete(bakerst);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            System.out.print(adao.findAll().size() + " adressen.\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void testOVChipkaaartDAO(OVChipkaartDAO odao) {

        System.out.println("\n---------- Test OVChipkaartDAO -------------");

        // Haal alle OVchipkaarten op uit de database
        List<OVChipkaart> ovkaarten = null;
        try {
            ovkaarten = odao.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("[Test] OVChipkaartDAO.findAll() geeft de volgende OVChipkaarten:");
        for (OVChipkaart r : ovkaarten) {
            System.out.println(r);
        }
        System.out.println();
        // Sla een OVChipkaart op in de database
        Date date = new Date(125, 05, 22);
        OVChipkaart batchov = new OVChipkaart(2, date, 2, 120, 2);
        System.out.print("[Test] Eerst " + ovkaarten.size() + " OVChipkaarten, na OVChipkaartDAO.save() ");
        try {
            odao.save(batchov);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            ovkaarten = odao.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println(ovkaarten.size() + " OVChipkaarten\n");

        //update en read OVChipkaart
        try {
            System.out.println("[Test] Eerst:\n" + odao.findById(batchov.getKaartNummer()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        batchov.setSaldo(100);
        try {
            odao.update(batchov);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            System.out.println("\nNa OVChipkaartDAO.update(): \n" + odao.findById(batchov.getKaartNummer()) + "\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ReizigerDAOPsql rdao = new ReizigerDAOPsql(connection);

        Reiziger cumberbatch = null;
        try {
            cumberbatch = rdao.findById(2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //findbyReiziger
        System.out.print("[Test] zoeken naar de OVChipkaart dat hoort bij deze reiziger "+cumberbatch.toString()+":\n");
        try {
            System.out.println(odao.findByReiziger(cumberbatch));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        batchov.setKlasse(1);
        cumberbatch.addOVChipKaart(batchov);
        try {
            rdao.update(cumberbatch);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("\nUpdaten van OVChipkaarten opgeslagen in reiziger object :");
        try {
            System.out.println(rdao.findById(cumberbatch.getId()).getOVKaarten());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        //        Delete OVChipkaart
        try {
            System.out.print("\n[Test] Eerst " + odao.findAll().size() + " OVChipkaarten, na OVChipkaartDAO.delete() ");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            rdao.delete(cumberbatch);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            System.out.print(odao.findAll().size() + " adressen.\n");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void testProductDAO(ProductDAO pdao) {
        System.out.println("\n---------- Test ProductDAO -------------");
        List<Product> producten = new ArrayList<>();
        try {
            producten = pdao.findAll();
        System.out.println("[Test] ProductDAO.findAll() geeft de volgende producten:");
        for (Product r : producten) {
            System.out.println(r);
        }
        System.out.println();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Product product = new Product(7, "Fast", "Only the fastest of trains", 10);

        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.save() ");
        try {
            pdao.save(product);
        } catch (SQLException throwables) {
            System.out.println("Kon product niet opslaan");
        }
        try {
            producten = pdao.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Kon lijst met producten niet ophalen");
        }
        System.out.println(producten.size() + " producten\n");

        product.setBeschrijving("Alleen de snelste treinen");
        try {
            System.out.print("[Test] Eerst " + pdao.findById(product.getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            pdao.update(product);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            System.out.println(" dan " + pdao.findById(product.getId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.print("[Test] Eerst " + producten.size() + " producten, na ProductDAO.delete() ");
        try {
            pdao.delete(product);
        } catch (SQLException throwables) {
            System.out.println("Kon product niet verwijderen");
        }
        try {
            producten = pdao.findAll();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println("Kon lijst met producten niet ophalen");
        }
        System.out.println(producten.size() + " producten\n");
        Date date = new Date(122, 11, 03);
        OVChipkaart ov = new OVChipkaart(16, date, 1, 100, 5);
        ov.addProduct(product);
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(connection);
        System.out.println(ov.getProducten());
        System.out.println(product.getOvChipkaarten());
        try {
            odao.save(ov);
        } catch (SQLException throwables) {
            System.out.println("kon ov niet opslaan");
        }
        try {
            pdao.save(product);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        OVChipkaart ov2 = null;
        try {
            ov2 = odao.findById(18326);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        ov2.addProduct(product);
        try {
            pdao.update(product);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            System.out.println(pdao.findByOVChipkaart(ov2));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            System.out.println(odao.findById(18326).getProducten());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        try {
            System.out.println("Findall producten: ");
            List<Product> productenlijst = pdao.findAll();
            for (Product p: productenlijst) {
                System.out.println(p + " " + p.getOvChipkaarten());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
