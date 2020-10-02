package p5.Data;

import p5.Data.OVChipkaartDAOPsql;
import p5.Data.ProductDAO;
import p5.Domein.OVChipkaart;
import p5.Domein.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection conn;

    public ProductDAOPsql(Connection conn){
        this.conn = conn;
    }

    @Override
    public boolean save(Product product) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("INSERT INTO product " +
                "VALUES (" +
                product.getId() + ", '" +
                product.getNaam() + "', '" +
                product.getBeschrijving() + "', '" +
                product.getPrijs() + "');");
        OVChipkaartDAOPsql odao = new OVChipkaartDAOPsql(conn);
        st.executeUpdate("DELETE FROM ov_chipkaart_product where product_nummer = " + product.getId() + " ;");
        if (product.getOvChipkaarten() != null) {
            for (int ov : product.getOvChipkaarten()) {
                try {
                    st.executeUpdate("INSERT INTO ov_chipkaart_product VALUES (" +
                            ov + ", " + product.getId() + ")");
                } catch (SQLException throwables) {
                }
            }
        }
        st.close();
        return false;
    }

    @Override
    public boolean update(Product product) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("UPDATE product " +
                "SET " +
                "product_nummer = " + product.getId() +
                ", naam = '" + product.getNaam() +
                "', beschrijving = '" + product.getBeschrijving() +
                "', prijs = '" + product.getPrijs() +
                "' WHERE product_nummer = " + product.getId() + ";");
        st.executeUpdate("DELETE FROM ov_chipkaart_product where product_nummer = " + product.getId() + " ;");
        if (product.getOvChipkaarten() != null) {
            for (int ov : product.getOvChipkaarten()) {
                try {
                    st.executeUpdate("INSERT INTO ov_chipkaart_product VALUES (" +
                            ov + ", " + product.getId() + ")");
                } catch (SQLException throwables) {
                }
            }
        }

        st.close();
        return false;
    }

    @Override
    public boolean delete(Product product) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate("DELETE FROM ov_chipkaart_product where product_nummer = " + product.getId() + " ;");
        st.executeUpdate("DELETE FROM product WHERE product_nummer = " + product.getId() + " ;");
        st.close();
        return false;
    }

    @Override
    public Product findById(int id) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM product WHERE product_nummer = " + id + ";");
        rs.next();
        Product product = new Product(rs.getInt("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getInt("prijs"));
        st.close();
        return product;
    }

    @Override
    public List<Product>  findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException {
        List<Product> producten = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT product.* FROM product INNER JOIN ov_chipkaart_product ON ov_chipkaart_product.product_nummer=product.product_nummer WHERE kaart_nummer="+ ovChipkaart.getKaartNummer() +";");
        while (rs.next()) {
            Product product = new Product(rs.getInt("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getInt("prijs"));
            producten.add(product);
        }
        st.close();
        return producten;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> producten = new ArrayList<>();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM product;");
        while (rs.next()) {
            Product product = new Product(rs.getInt("product_nummer"), rs.getString("naam"), rs.getString("beschrijving"), rs.getInt("prijs"));
            Statement st2 = conn.createStatement();
            ResultSet rs2 = st2.executeQuery("SELECT kaart_nummer FROM ov_chipkaart_product WHERE product_nummer="+product.getId()+";");
            while (rs2.next()) {
                product.addovChipkaart(rs2.getInt("kaart_nummer"));
            }
            producten.add(product);
        }
        st.close();
        return producten;
    }
}
