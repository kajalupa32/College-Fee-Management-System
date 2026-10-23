import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;

public class ReceiptPanel extends JPanel {
    JTextField prn = new JTextField(), amount = new JTextField();
    JComboBox<String> mode = new JComboBox<>(new String[]{"Cash", "UPI", "Cheque", "Card"});
    JTable table = new JTable();

    public ReceiptPanel() {
        setLayout(new BorderLayout(5, 5));
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Student PRN")); form.add(prn);
        form.add(new JLabel("Amount Paid")); form.add(amount);
        form.add(new JLabel("Payment Mode")); form.add(mode);

        JButton pay = new JButton("Generate Receipt");
        JButton refresh = new JButton("Refresh");
        JPanel btns = new JPanel(); btns.add(pay); btns.add(refresh);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER); top.add(btns, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        pay.addActionListener(e -> save());
        refresh.addActionListener(e -> load());
        load();
    }

    void save() {
        double amt;
        try { amt = Double.parseDouble(amount.getText().trim()); }
        catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Enter valid amount"); return; }
        if (amt <= 0) { JOptionPane.showMessageDialog(this, "Amount must be > 0"); return; }

        try (Connection c = DB.get()) {
            PreparedStatement q = c.prepareStatement("SELECT student_id,name FROM student WHERE prn=?");
            q.setString(1, prn.getText().trim());
            ResultSet rs = q.executeQuery();
            if (!rs.next()) { JOptionPane.showMessageDialog(this, "No student with this PRN"); return; }
            int sid = rs.getInt(1); String sname = rs.getString(2);

            PreparedStatement ins = c.prepareStatement(
                "INSERT INTO receipt(student_id,receipt_date,amount,mode) VALUES(?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS);
            ins.setInt(1, sid);
            ins.setDate(2, Date.valueOf(LocalDate.now()));
            ins.setDouble(3, amt);
            ins.setString(4, mode.getSelectedItem().toString());
            ins.executeUpdate();
            ResultSet k = ins.getGeneratedKeys(); k.next();

            JOptionPane.showMessageDialog(this,
                "===== FEE RECEIPT =====\nReceipt No: " + k.getInt(1) +
                "\nDate: " + LocalDate.now() + "\nStudent: " + sname + " (" + prn.getText() + ")" +
                "\nAmount: Rs. " + amt + "\nMode: " + mode.getSelectedItem());
            prn.setText(""); amount.setText(""); load();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    void load() {
        DB.fill(table, "SELECT r.receipt_id, s.prn, s.name, r.receipt_date, r.amount, r.mode " +
                       "FROM receipt r JOIN student s ON r.student_id=s.student_id ORDER BY r.receipt_id DESC");
    }
}