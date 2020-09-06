package p1;
import java.sql.*;


public class Main {
    public static void main(String[] args) {
        try {
            Connection db = DriverManager.getConnection("jdbc:postgresql:DP_OV-Chipkaart", "postgres", "tijmendb");
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM rijzigers");
            int i = 1;
            while (rs.next()) {
                System.out.println(rs.getString(i));
                i += 1;
            }
            rs.close();
            st.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
