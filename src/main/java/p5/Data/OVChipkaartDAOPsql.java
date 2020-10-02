package p5.Data;

import p5.Domein.OVChipkaart;
import p5.Domein.Product;
import p5.Domein.Reiziger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
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
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
        if (ovKaart.getProducten() != null) {
            for (Product p : ovKaart.getProducten()) {
                try {
                    st.executeUpdate("INSERT INTO ov_chipkaart_product VALUES (" +
                            ovKaart.getKaartNummer() + ", " + p.getId() + ")");
                } catch (SQLException throwables) {
                }
            }
        }
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

        st.executeUpdate("DELETE FROM ov_chipkaart_product where kaart_nummer = " + ovKaart.getKaartNummer() + " ;");
        if (ovKaart.getProducten() != null) {
            for (Product p : ovKaart.getProducten()) {
                try {
                    st.executeUpdate("INSERT INTO ov_chipkaart_product VALUES (" +
                            ovKaart.getKaartNummer() + ", " + p.getId() + ")");
                } catch (SQLException throwables) {
                }
            }
        }

        st.close();
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ovKaart) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("DELETE FROM ov_chipkaart_product where kaart_nummer = " + ovKaart.getKaartNummer() + " ;");
        st.executeUpdate("DELETE FROM ov_chipkaart WHERE kaart_nummer = " + ovKaart.getKaartNummer() + " ;");
        st.close();
        return true;
    }

    @Override
    public OVChipkaart findById(int id) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ov_chipkaart WHERE kaart_nummer = " + id + " ;");
        rs.next();
        OVChipkaart ovKaart = new OVChipkaart(rs.getInt("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getInt("saldo"), rs.getInt("reiziger_id"));
        st.close();
        ProductDAOPsql pdao = new ProductDAOPsql(conn);
        ovKaart.setProducten(pdao.findByOVChipkaart(ovKaart));
        return ovKaart;
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException {
        List<OVChipkaart> ovKaarten = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM ov_chipkaart WHERE reiziger_id = " + reiziger.getId() + ";");
        while (rs.next()) {
            OVChipkaart ovKaart = new OVChipkaart(rs.getInt("kaart_nummer"), rs.getDate("geldig_tot"), rs.getInt("klasse"), rs.getInt("saldo"), rs.getInt("reiziger_id"));
            ProductDAOPsql pdao = new ProductDAOPsql(conn);
            ovKaart.setProducten(pdao.findByOVChipkaart(ovKaart));
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
            ProductDAOPsql pdao = new ProductDAOPsql(conn);
            ovKaart.setProducten(pdao.findByOVChipkaart(ovKaart));
            ovKaarten.add(ovKaart);
        }
        st.close();
        return ovKaarten;
    }
}
