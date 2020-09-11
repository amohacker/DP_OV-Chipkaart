package p4;

import java.sql.SQLException;
import java.util.List;

public interface OVChipkaartDAO {
    public boolean save(OVChipkaart ovKaart) throws SQLException;
    public boolean update(OVChipkaart ovKaart) throws SQLException;
    public boolean delete(OVChipkaart ovKaart) throws SQLException;
    public OVChipkaart findById(int id) throws SQLException;
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) throws SQLException;
    public List<OVChipkaart> findAll() throws SQLException;
}
