package p4;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //update en read reiziger
        System.out.println("\n[Test] Eerst:\n" + rdao.findById(sietske.id));
        sietske.setAchternaam("Bakker");
        rdao.update(sietske);
        System.out.println("\nNa ReizigerDAOPsql.update(): \n" + rdao.findById(sietske.id) + "\n");

        //findbygbdatum
        System.out.println("[Test] zoeken na alle reizigers met de geboortedatum "+sietske.getGeboortedatum()+":\n");
        System.out.println(rdao.findByGbdatum(sietske.getGeboortedatum().toString()));

        //Delete reiziger
        System.out.print("\n [Test] Eerst " + rdao.findAll().size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        System.out.print(rdao.findAll().size() + " reizigers.\n");
    }

    private static void testAdresDAO(AdresDAO adao){
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle reizigers op uit de database
        List<Adres> adressen = adao.findAll();
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
        rdao.save(cumberbatch);
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na AdresDAO.save() ");
        adao.save(bakerst);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        rdao.save(cumberbatch);
        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        //update en read adres
        System.out.println("\n[Test] Eerst:\n" + adao.findById(bakerst.getId()));
        bakerst.setPostcode("1234OK");
        adao.update(bakerst);
        System.out.println("\nNa AdresDAOPsql.update(): \n" + adao.findById(bakerst.getId()) + "\n");

        //findbyreiziger
        System.out.print("[Test] zoeken naar het adres dat hoort bij deze reiziger "+cumberbatch.toString()+":\n");
        cumberbatch.setAdres(adao.findByReiziger(cumberbatch));
        System.out.println(cumberbatch.toString());
//        Delete adres
        System.out.print("\n [Test] Eerst " + adao.findAll().size() + " reizigers, na ReizigerDAO.delete() ");
        adao.delete(bakerst);
        System.out.print(adao.findAll().size() + " reizigers.\n");
    }
}
