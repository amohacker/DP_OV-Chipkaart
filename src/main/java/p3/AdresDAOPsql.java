package p3;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class AdresDAOPsql implements AdresDAO {
    private Connection conn;
    private AdresDAO adao;

    public AdresDAOPsql(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean save(Adres adres) throws SQLException {
            Statement st = conn.createStatement();
            st.executeQuery("INSERT INTO adres " +
                    "VALUES (" +
                    adres.getId() + ", '" +
                    adres.getPostcode() + "', '" +
                    adres.getHuisnummer() + "', '" +
                    adres.getStraat() + "', '" +
                    adres.getWoonplaats() + "', '" +
                    adres.getReiziger_id() + "');");
            st.close();
        return true;
    }

    @Override
    public boolean update(Adres adres) throws SQLException {
            Statement st = conn.createStatement();
            st.executeQuery("UPDATE adres " +
                    "SET " +
                    "adres_id = " + adres.getId() +
                    ", postcode = '" + adres.getPostcode() +
                    "', huisnummer = '" + adres.getHuisnummer() +
                    "', straat = '" + adres.getStraat() +
                    "', woonplaats = '" + adres.getWoonplaats() +
                    "', reiziger_id = '" + adres.getReiziger_id() +
                    "' WHERE adres_id = " + adres.getId() + ";");
            st.close();
        return true;
    }

    @Override
    public boolean delete(Adres adres) throws SQLException {
            Statement st = conn.createStatement();
            st.executeQuery("DELETE FROM adres WHERE adres_id = " + adres.getId() + " ;");
            st.close();
        return true;
    }

    @Override
    public Adres findById(int id) throws SQLException {
        Adres adres;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM adres WHERE adres_id = " + id + ";");
            rs.next();
            adres = new Adres(rs.getInt("adres_id"), rs.getString("postcode"), rs.getString("huisnummer"), rs.getString("straat"), rs.getString("woonplaats"), rs.getInt("reiziger_id"));
            st.close();
            return adres;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) throws SQLException {
        Adres adres;
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM adres WHERE reiziger_id = " + reiziger.getId() + ";");
            rs.next();
            adres = new Adres(
                    rs.getInt("adres_id"), rs.getString("postcode"),
                    rs.getString("huisnummer"), rs.getString("straat"),
                    rs.getString("woonplaats"), rs.getInt("reiziger_id")
            );
            st.close();
        return adres;
    }

    @Override
    public List<Adres> findAll() throws SQLException{
        List<Adres> adressen = new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM adres;");
            while (rs.next()) {
                Adres adres = new Adres(
                        rs.getInt("adres_id"), rs.getString("postcode"),
                        rs.getString("huisnummer"), rs.getString("straat"),
                        rs.getString("woonplaats"), rs.getInt("reiziger_id")
                );
                adressen.add(adres);
            }
            st.close();
            return adressen;
    }
}
