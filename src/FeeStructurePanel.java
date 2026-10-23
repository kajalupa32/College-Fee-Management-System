import javax.swing.*;
import java.awt.*;

public class FeeStructurePanel extends JPanel {
    JTextField course = new JTextField(), year = new JTextField(), fee = new JTextField();
    JTable table = new JTable();
    int selectedId = -1;

    public FeeStructurePanel() {
        setLayout(new BorderLayout(5, 5));
        JPanel form = new JPanel(new GridLayout(3, 2, 5, 5));
        form.add(new JLabel("Course")); form.add(course);
        form.add(new JLabel("Year")); form.add(year);
        form.add(new JLabel("Total Fee")); form.add(fee);

        JButton add = new JButton("Add"), upd = new JButton("Update"),
                del = new JButton("Delete"), clr = new JButton("Clear");
        JPanel btns = new JPanel(); btns.add(add); btns.add(upd); btns.add(del); btns.add(clr);

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER); top.add(btns, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        add.addActionListener(e -> {
            try {
                if (DB.run("INSERT INTO fee_structure(course,year_no,total_fee) VALUES(?,?,?)",
                        course.getText(), Integer.parseInt(year.getText().trim()),
                        Double.parseDouble(fee.getText().trim()))) { load(); clear(); }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Enter valid numbers"); }
        });
        upd.addActionListener(e -> {
            if (selectedId < 0) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }
            try {
                if (DB.run("UPDATE fee_structure SET course=?,year_no=?,total_fee=? WHERE fee_id=?",
                        course.getText(), Integer.parseInt(year.getText().trim()),
                        Double.parseDouble(fee.getText().trim()), selectedId)) { load(); clear(); }
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Enter valid numbers"); }
        });
        del.addActionListener(e -> {
            if (selectedId < 0) { JOptionPane.showMessageDialog(this, "Select a row first"); return; }
            DB.run("DELETE FROM fee_structure WHERE fee_id=?", selectedId);
            load(); clear();
        });
        clr.addActionListener(e -> clear());

        table.getSelectionModel().addListSelectionListener(e -> {
            int r = table.getSelectedRow();
            if (r >= 0) {
                selectedId = (int) table.getValueAt(r, 0);
                course.setText(String.valueOf(table.getValueAt(r, 1)));
                year.setText(String.valueOf(table.getValueAt(r, 2)));
                fee.setText(String.valueOf(table.getValueAt(r, 3)));
            }
        });
        load();
    }

    void load() { DB.fill(table, "SELECT fee_id,course,year_no,total_fee FROM fee_structure"); }

    void clear() { selectedId = -1; course.setText(""); year.setText(""); fee.setText(""); }
}