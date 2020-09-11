package p4;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO{
    private Connection conn;

    public OVChipkaartDAOPsql(Connection conn) { this.conn = conn; }

    @Override
    public boolean save(OVChipkaart ovKaart) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO ov_chipkaart " +
                "VALUES (" +
                ovKaart.getKaartNummer() + ", '" +
                ovKaart.getGeldigTot() + "', '" +
                ovKaart.getKlasse() + "', '" +
                ovKaart.getSaldo() + "', '" +
                ovKaart.getReizigerId() + "');");
        st.close();
        return true;
    }

    @Override
    public boolean update(OVChipkaart ovKaart) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("UPDATE ov_chipkaart " +
                "SET " +
                "kaart_nummer = " + ovKaart.getKaartNummer() +
                ", geldig_tot = '" + ovKaart.getGeldigTot() +
                "', klasse = " + ovKaart.getKlasse() +
                ", saldo = " + ovKaart.getSaldo() +
                ", reiziger_id = " + ovKaart.getReizigerId() +
                " WHERE kaart_nummer = " + ovKaart.getKaartNummer() + ";");
        st.close();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovKaart) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("DELETE FROM ov_chipkaart WHERE kaart_nummer = " + ovKaart.getKaartNummer() + " ;");
        st.close();
        return true;
    }

    @Override
    public OVChipkaart findById(int id) throws SQLException {
        OVChipkaart ovKaart;
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ov_chipkaart WHERE kaart_nummer = " + id + ";");
        rs.next();
        ovKaart = new OVChipkaart(rs.getInt("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getInt("saldo"), rs.getInt("reiziger_id"));
        st.close();
        return ovKaart;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> ovKaarten = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId() + ";");
        while (rs.next()) {
            OVChipkaart ovKaart = new OVChipkaart(rs.getInt("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getInt("saldo"), rs.getInt("reiziger_id"));
            ovKaarten.add(ovKaart);
        }
        st.close();
        return ovKaarten;
    }

    @Override
    public List<OVChipkaart> findAll() throws SQLException {
        List<OVChipkaart> ovKaarten = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ov_chipkaart;");
        while (rs.next()) {
            OVChipkaart ovKaart = new OVChipkaart(rs.getInt("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getInt("saldo"), rs.getInt("reiziger_id"));
            ovKaarten.add(ovKaart);
        }
        st.close();
        return ovKaarten;
    }
}
