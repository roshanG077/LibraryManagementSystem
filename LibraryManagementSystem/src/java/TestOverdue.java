import java.sql.*;
import com.finlogic.config.DBConnection;

public class TestOverdue {
    public static void main(String[] args) {
        String sql = "UPDATE issue_books SET return_date = '2026-05-10' WHERE returned = false LIMIT 1";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Success: One book has been marked as overdue (Return date: 2026-05-10).");
            } else {
                System.out.println("No active issues found to update.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
