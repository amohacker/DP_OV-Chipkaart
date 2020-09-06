package p2;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.*;

public class ReizigerDAOPsql implements ReizigerDAO{
    Connection conn;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            Statement st = conn.createStatement();
            st.executeQuery("INSERT INTO reiziger " +
                "VALUES (" +
                reiziger.getId()+", '" +
                reiziger.getVoorletters() + "', '" +
                reiziger.getTussenvoegsel() + "', '"+
                reiziger.getAchternaam() + "', '" +
                reiziger.getGeboortedatum() + "');");
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Reiziger reiziger) {
        try {
            Statement st = conn.createStatement();
            st.executeQuery("UPDATE reiziger " +
                    "SET " +
                    "reiziger_id = " + reiziger.getId() +
                    ", voorletters = '" + reiziger.getVoorletters() +
                    "', tussenvoegsel = '" + reiziger.getTussenvoegsel() +
                    "', achternaam = '" + reiziger.getAchternaam() +
                    "', geboortedatum = '" + reiziger.getGeboortedatum() +
                    "' WHERE reiziger_id = " + reiziger.getId() + ";");
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            Statement st = conn.createStatement();
            st.executeQuery("DELETE FROM reiziger WHERE reiziger_id = " + reiziger.getId() + " ;");
        } catch (SQLException throwables) {
            return false;
        }
        return true;
    }

    @Override
    public Reiziger findById(int id) {
        Reiziger reiziger;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger WHERE reiziger_id = " + id + ";");
            rs.next();
            reiziger = new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "';");
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
                reizigers.add(reiziger);
            }
            return reizigers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger;");
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
                reizigers.add(reiziger);
            }
            return reizigers;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }
}