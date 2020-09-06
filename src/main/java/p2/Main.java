package p2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
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
}
