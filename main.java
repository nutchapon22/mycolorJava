import java.sql.ResultSet;

public class main {
    public static void main(String[] args) {
        DBcon db = new DBcon();
        ResultSet rb = db.GetUserData("test", "test");
        try {
            while (rb.next()) {
                System.out.println(rb.getString("userid"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
