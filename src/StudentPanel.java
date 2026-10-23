import javax.swing.*;
import java.awt.*;

public class StudentPanel extends JPanel {
    JTextField prn = new JTextField(), name = new JTextField(), course = new JTextField(),
               year = new JTextField(), phone = new JTextField(), email = new JTextField();
    JTable table = new JTable();
    int selectedId = -1;

    public StudentPanel() {
        setLayout(new BorderLayout(5, 5));
        JPanel form = new JPanel(new GridLayout(6, 2, 5, 5));
        form.add(new JLabel("PRN")); form.add(prn);
        form.add(new JLabel("Name")); form.add(name);
        form.add(new JLabel("Course (e.g. BCA)")); form.add(course);
        form.add(new JLabel("Year (1/2/3)")); form.add(year);
        form.add(new JLabel("Phone")); form.add(phone);
        form.add(new JLabel("Email")); form.add(email);

        JButton add = new JButton("Add"), upd = new JButton("Update"),
                del = new JButton("Delete"), clr = new JButton("Clear");
        JPanel btns = new JPanel(); btns.add(add); btns.add(upd); btns.add(del); btns.add(clr);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER); top.add(btns, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> {
            try {
                if (DB.run("INSERT INTO student(prn,name,course,year_no,phone,email) VALUES(?,?,?,?,?,?)",
                        prn.getText(), name.getText(), course.getText(),
                        Integer.parseInt(year.getText().trim()), phone.getText(), email.getText())) {
                    load(); clear();
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Year must be a number"); }
        });
        upd.addActionListener(e -> {
            if (selectedId < 0) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }
            try {
                if (DB.run("UPDATE student SET prn=?,name=?,course=?,year_no=?,phone=?,email=? WHERE student_id=?",
                        prn.getText(), name.getText(), course.getText(),
                        Integer.parseInt(year.getText().trim()), phone.getText(), email.getText(), selectedId)) {
                    load(); clear();
                }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Year must be a number"); }
        });
        del.addActionListener(e -> {
            if (selectedId < 0) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }
            if (JOptionPane.showConfirmDialog(this, "Delete this student and their receipts?") == JOptionPane.YES_OPTION) {
                DB.run("DELETE FROM student WHERE student_id=?", selectedId);
                load(); clear();
            }
        });
        clr.addActionListener(e -> clear());

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                selectedId = (int) table.getValueAt(r, 0);
                prn.setText(String.valueOf(table.getValueAt(r, 1)));
                name.setText(String.valueOf(table.getValueAt(r, 2)));
                course.setText(String.valueOf(table.getValueAt(r, 3)));
                year.setText(String.valueOf(table.getValueAt(r, 4)));
                phone.setText(String.valueOf(table.getValueAt(r, 5)));
                email.setText(String.valueOf(table.getValueAt(r, 6)));
            }
        });
        load();
    }

    void load() { DB.fill(table, "SELECT student_id,prn,name,course,year_no,phone,email FROM student"); }

    void clear() {
        selectedId = -1;
        prn.setText(""); name.setText(""); course.setText("");
        year.setText(""); phone.setText(""); email.setText("");
    }
}