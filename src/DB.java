import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class DB {
    static final String URL  = "jdbc:mysql://localhost:3306/college_fee";
    static final String USER = "root";
    static final String PASS = "root";

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // run INSERT / UPDATE / DELETE
    public static boolean run(String sql, Object... p) {
        try (Connection c = get(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < p.length; i++) ps.setObject(i + 1, p[i]);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            return false;
        }
    }

    // run SELECT and show result in a JTable
    public static void fill(JTable t, String sql, Object... p) {
        try (Connection c = get(); PreparedStatement ps = c.prepareStatement(sql)) {
            for (int i = 0; i < p.length; i++) ps.setObject(i + 1, p[i]);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            int n = md.getColumnCount();
            DefaultTableModel m = new DefaultTableModel() {
                public boolean isCellEditable(int r, int c) { return false; }
            };
            for (int i = 1; i <= n; i++) m.addColumn(md.getColumnLabel(i));
            while (rs.next()) {
                Object[] row = new Object[n];
                for (int i = 0; i < n; i++) row[i] = rs.getObject(i + 1);
                m.addRow(row);
            }
            t.setModel(m);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}