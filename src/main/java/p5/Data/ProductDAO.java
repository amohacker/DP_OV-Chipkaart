package p5.Data;

import p5.Domein.OVChipkaart;
import p5.Domein.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {
    boolean save(Product product) throws SQLException;
    boolean update(Product product) throws SQLException;
    boolean delete(Product product) throws SQLException;
    Product findById(int id) throws SQLException;
    List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) throws SQLException;
    List<Product> findAll() throws SQLException;
}
