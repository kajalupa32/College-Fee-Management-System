import javax.swing.*;
import java.awt.*;

public class DuesPanel extends JPanel {
    JTable table = new JTable();

    public DuesPanel() {
        setLayout(new BorderLayout(5, 5));
        JButton all = new JButton("All Students Fee Status");
        JButton dues = new JButton("Only Pending Dues");
        JPanel top = new JPanel(); top.add(dues); top.add(all);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        dues.addActionListener(e -> load(true));
        all.addActionListener(e -> load(false));
        load(true);
    }

    void load(boolean onlyDue) {
        String sql = "SELECT s.prn, s.name, s.course, s.year_no, f.total_fee, " +
                     "IFNULL(SUM(r.amount),0) AS paid, " +
                     "f.total_fee - IFNULL(SUM(r.amount),0) AS due " +
                     "FROM student s " +
                     "JOIN fee_structure f ON f.course=s.course AND f.year_no=s.year_no " +
                     "LEFT JOIN receipt r ON r.student_id=s.student_id " +
                     "GROUP BY s.student_id, s.prn, s.name, s.course, s.year_no, f.total_fee " +
                     (onlyDue ? "HAVING due > 0 " : "") + "ORDER BY s.name";
        DB.fill(table, sql);
    }
}