import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("College Fee Management System");
        setSize(900, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Students", new StudentPanel());
        tabs.addTab("Fee Structure", new FeeStructurePanel());
        tabs.addTab("Receipts", new ReceiptPanel());
        tabs.addTab("Dues Report", new DuesPanel());
        // refresh the dues report whenever the tab is opened
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedComponent() instanceof DuesPanel d) d.load(true);
        });
        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}