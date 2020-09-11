package p4;

import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.spi.CalendarDataProvider;

public class ReizigerDAOPsql implements ReizigerDAO {
    Connection conn;

    public ReizigerDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Reiziger reiziger) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO reiziger " +
                "VALUES (" +
                reiziger.getId() + ", '" +
                reiziger.getVoorletters() + "', '" +
                reiziger.getTussenvoegsel() + "', '" +
                reiziger.getAchternaam() + "', '" +
                reiziger.getGeboortedatum() + "');");
        st.close();
        if (reiziger.getAdres() != null) {
            AdresDAOPsql adao = new AdresDAOPsql(conn);
            Adres adres;
            try {
                adres = adao.findByReiziger(reiziger);
                adao.save(reiziger.getAdres());
            } catch (SQLException throwables) {
                adao.update(reiziger.getAdres());
            }
        }
        if (reiziger.getOVKaarten()!=null && reiziger.getOVKaarten().isEmpty() == false) {
            OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
            List<OVChipkaart> ovKaarten = reiziger.getOVKaarten();
            for (OVChipkaart ovkaart: ovKaarten) {
                try {
                    odao.findById(ovkaart.getKaartNummer());
                    odao.save(ovkaart);
                } catch (SQLException throwables) {
                    odao.update(ovkaart);
                }
            }
        }
        return false;
    }

    @Override
    public boolean update(Reiziger reiziger) throws SQLException{
            Statement st = conn.createStatement();
            st.executeUpdate("UPDATE reiziger " +
                    "SET " +
                    "reiziger_id = " + reiziger.getId() +
                    ", voorletters = '" + reiziger.getVoorletters() +
                    "', tussenvoegsel = '" + reiziger.getTussenvoegsel() +
                    "', achternaam = '" + reiziger.getAchternaam() +
                    "', geboortedatum = '" + reiziger.getGeboortedatum() +
                    "' WHERE reiziger_id = " + reiziger.getId() + ";");
            st.close();
            if (reiziger.getAdres() != null) {
                AdresDAOPsql adao = new AdresDAOPsql(conn);
                Adres adres;
                try {
                    adres = adao.findByReiziger(reiziger);
                    adao.save(reiziger.getAdres());
                } catch (SQLException throwables) {
                    adao.update(reiziger.getAdres());
                }
            }
        if (reiziger.getOVKaarten()!=null && reiziger.getOVKaarten().isEmpty() == false) {
            OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
            List<OVChipkaart> ovKaarten = reiziger.getOVKaarten();
            for (OVChipkaart ovkaart: ovKaarten) {
                try {
                    odao.findById(ovkaart.getKaartNummer());
                    odao.save(ovkaart);
                } catch (SQLException throwables) {
                    odao.update(ovkaart);
                }
            }
        }
            return false;
        }

    @Override
    public boolean delete(Reiziger reiziger) throws SQLException {
        if (reiziger.getAdres() != null) {
            AdresDAOPsql adao = new AdresDAOPsql(conn);
            Adres adres;
            try {
                adres = adao.findByReiziger(reiziger);
                adao.delete(reiziger.getAdres());
            } catch (SQLException throwables) {
            }
        }
        if (reiziger.getOVKaarten()!=null && reiziger.getOVKaarten().isEmpty() == false) {
            OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
            List<OVChipkaart> ovKaarten = reiziger.getOVKaarten();
            for (OVChipkaart ovkaart: ovKaarten) {
                try {
                    odao.delete(ovkaart);
                } catch (SQLException throwables) {
                }
            }
        }
            Statement st = conn.createStatement();
            st.executeUpdate("DELETE FROM reiziger WHERE reiziger_id = " + reiziger.getId() + " ;");
            st.close();
        return false;
    }

    @Override
    public Reiziger findById(int id) throws SQLException{
        Reiziger reiziger;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger WHERE reiziger_id = " + id + ";");
            rs.next();
            reiziger = new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
            AdresDAOPsql adao = new AdresDAOPsql(conn);
            Adres adres;
            st.close();
            try {
                adres = adao.findByReiziger(reiziger);
                reiziger.setAdres(adres);
            } catch (SQLException throwables) {
            }
            OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
            List<OVChipkaart> ovKaarten = new ArrayList<>();
            try {
                ovKaarten = odao.findByReiziger(reiziger);
                reiziger.setOVChipKaarten(ovKaarten);
            } catch (SQLException throwables) {
            }
        return reiziger;
    }

    @Override
    public List<Reiziger> findByGbdatum(String datum) throws SQLException{
        List<Reiziger> reizigers = new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger WHERE geboortedatum = '" + datum + "';");
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
                AdresDAOPsql adao = new AdresDAOPsql(conn);
                Adres adres;
                try {
                    adres = adao.findByReiziger(reiziger);
                    reiziger.setAdres(adres);
                } catch (SQLException throwables) {
                }
                OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
                List<OVChipkaart> ovKaarten = new ArrayList<>();
                try {
                    ovKaarten = odao.findByReiziger(reiziger);
                    reiziger.setOVChipKaarten(ovKaarten);
                } catch (SQLException throwables) {
                }
                reizigers.add(reiziger);
            }

            st.close();
            return reizigers;
    }

    @Override
    public List<Reiziger> findAll() throws SQLException{
        List<Reiziger> reizigers = new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM reiziger;");
            while (rs.next()) {
                Reiziger reiziger = new Reiziger(rs.getInt("reiziger_id"), rs.getString("voorletters"), rs.getString("tussenvoegsel"), rs.getString("achternaam"), rs.getDate("geboortedatum"));
                reizigers.add(reiziger);
                AdresDAOPsql adao = new AdresDAOPsql(conn);
                Adres adres;
                try {
                    adres = adao.findByReiziger(reiziger);
                    reiziger.setAdres(adres);
                } catch (SQLException throwables) {
                }
                OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
                List<OVChipkaart> ovKaarten = new ArrayList<>();
                try {
                    ovKaarten = odao.findByReiziger(reiziger);
                    reiziger.setOVChipKaarten(ovKaarten);
                } catch (SQLException throwables) {
                }
            }
            st.close();
            return reizigers;
    }
}