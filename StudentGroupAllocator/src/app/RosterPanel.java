package app;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RosterPanel extends JPanel {

    private final MainFrame mainFrame;
    private final StudentTableModel tableModel = new StudentTableModel();
    private final JTextField excludedField = new JTextField("7,33", 12);
    private final JLabel countLabel = new JLabel("No roster loaded yet.");
    private List<Student> fullRoster = new ArrayList<>();

    public RosterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        JButton loadButton = new JButton("Load 1-53 Roster");
        loadButton.addActionListener(e -> loadFullRoster());

        JButton applyButton = new JButton("Apply Exclusions");
        applyButton.addActionListener(e -> applyExclusions());

        header.add(new JLabel("<html><b>Step 1: Load Roster</b></html>"));
        header.add(loadButton);
        header.add(Box.createHorizontalStrut(20));
        header.add(new JLabel("Exclude Roll(s):"));
        header.add(excludedField);
        header.add(applyButton);
        return header;
    }

    private JScrollPane buildTable() {
        JTable table = new JTable(tableModel);
        table.setRowHeight(26);
        table.getColumnModel().getColumn(2).setCellRenderer(new GenderCellComponent());
        table.getColumnModel().getColumn(2).setCellEditor(new GenderCellComponent());
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(220);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        JPanel wrapper = new JPanel(new BorderLayout(5, 5));
        wrapper.add(new JLabel("Active Students"), BorderLayout.NORTH);
        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);
        wrapper.add(countLabel, BorderLayout.SOUTH);

        JPanel outer = new JPanel(new BorderLayout());
        outer.add(wrapper, BorderLayout.CENTER);
        return new JScrollPane(outer);
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton nextButton = new JButton("Next Step");
        nextButton.addActionListener(e -> goNext());
        footer.add(nextButton);
        return footer;
    }
    private static final String[] STUDENT_NAMES = {
            "Mst Laboni Khatun",       // Roll 1  (ID 1810)
            "Puja Bhokto",             // Roll 2  (ID 1811)
            "Mst. Umme Habiba",        // Roll 3  (ID 1812)
            "Sujana Aktery",           // Roll 4  (ID 1813)
            "Mim Akter",               // Roll 5  (ID 1814)
            "Urmee Akter",             // Roll 6  (ID 1815)
            "Akhi Muni",                // Roll 7  (ID 1816)
            "Nusrat Jahan Mysha",      // Roll 8  (ID 1817)
            "Mahbuba Zahan Nupur",     // Roll 9  (ID 1818)
            "Raisa Binte Nazrul",      // Roll 10  (ID 1819)
            "Fahmida Fayeza Arin",     // Roll 11 (ID 1820)
            "Diya Rani Karmakar",      // Roll 12 (ID 1821)
            "Sanzida Rahman",          // Roll 13 (ID 1822)
            "Sanjida Islam",           // Roll 14 (ID 1823)
            "Tania Islam",             // Roll 15 (ID 1824)
            "Raisa Haider Risu",       // Roll 16 (ID 1825)
            "Dipshika Chakma",         // Roll 17 (ID 1826)
            "Runa Akter",              // Roll 18 (ID 1827)
            "Pritylata Paul",          // Roll 19 (ID 1828)
            "Mir Fariha Alam",         // Roll 20 (ID 1829)
            "Shahida Pervin Sadia",    // Roll 21 (ID 1830)
            "Nanjiba Nawar",           // Roll 22 (ID 1831)
            "Mosammed Shinha",         // Roll 23 (ID 1832)
            "Refaya Tul Islam",        // Roll 24 (ID 1833)
            "Farhana Jannat Nadiha",   // Roll 25 (ID 1834)
            "Noushin Sultana",         // Roll 26 (ID 1835)
            "Abir Howlader",           // Roll 27 (ID 1836)
            "Ahsan Kabir",             // Roll 28 (ID 1837)
            "Md Ashraful Rahman",      // Roll 29 (ID 1838)
            "Mahamudul Hassan Antor",  // Roll 30 (ID 1839)
            "Nahian Hossain Arman",    // Roll 31 (ID 1840)
            "Tapos Paul",              // Roll 32 (ID 1841)
            "Sahaf" ,                  // Roll 33 (ID 1842)
            "Yasin Hasan",             // Roll 34 (ID 1843)
            "Adnan Abir Sajib",        // Roll 35 (ID 1844)
            "Rakib Hossain",           // Roll 36 (ID 1845)
            "MD Sohan Kha",            // Roll 37 (ID 1846)
            "Moshiur Rahman Tanim",    // Roll 38 (ID 1847)
            "Ashfaq Hossain Shifat",   // Roll 39 (ID 1848)
            "Siam",                    // Roll 40 (ID 1849)
            "MD. Azizul Haque",        // Roll 41 (ID 1850)
            "Junayet Uddin Nasir",     // Roll 42 (ID 1851)
            "Newaj Sharif",            // Roll 43 (ID 1852)
            "Shahoriar Hossain",       // Roll 44 (ID 1853)
            "Abir Hasan Amit",         // Roll 45 (ID 1854)
            "Fahim Muntasir",          // Roll 46 (ID 1855)
            "Md. Sumon",               // Roll 47 (ID 1856)
            "Khairul Islam",           // Roll 48 (ID 1857)
            "Shohan Shaikh",           // Roll 49 (ID 1858)
            "Asif Islam",              // Roll 50 (ID 1859)
            "Rahul Kumar Sarkar",      // Roll 51 (ID 1860)
            "Tamim Hasan",             // Roll 52 (ID 1861)
            "Muntasir Ahmad"           // Roll 53 (ID 1862)
    };

    private void loadFullRoster() {
        fullRoster = new ArrayList<>();
        // Roll 1-26 = Female, Roll 27-51 = Male .
        for (int roll = 1; roll <= 53; roll++) {
            Gender g = (roll <= 26) ? Gender.FEMALE : Gender.MALE;
            fullRoster.add(new Student(roll, STUDENT_NAMES[roll - 1], g));
        }
        applyExclusions();
    }

    private Set<Integer> parseExcludedRolls() {
        Set<Integer> excluded = new HashSet<>();
        String text = excludedField.getText().trim();
        if (text.isEmpty()) {
            return excluded;
        }
        for (String part : text.split(",")) {
            part = part.trim();
            if (part.isEmpty()) {
                continue;
            }
            try {
                excluded.add(Integer.parseInt(part));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "\"" + part + "\" is not a valid roll number and was ignored.",
                        "Invalid Exclusion", JOptionPane.WARNING_MESSAGE);
            }
        }
        return excluded;
    }

    private void applyExclusions() {
        if (fullRoster.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Load the roster first using \"Load 1-53 Roster\".",
                    "No Roster Loaded", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        Set<Integer> excluded = parseExcludedRolls();
        List<Student> active = new ArrayList<>();
        for (Student s : fullRoster) {
            s.setActive(!excluded.contains(s.getRollNumber()));
            if (s.isActive()) {
                active.add(s);
            }
        }
        tableModel.setStudents(active);
        countLabel.setText(active.size() + " active student(s) out of " + fullRoster.size()
                + " (excluded: " + (excluded.isEmpty() ? "none" : joinSorted(excluded)) + ")");
    }

    private String joinSorted(Set<Integer> values) {
        List<Integer> sorted = new ArrayList<>(values);
        java.util.Collections.sort(sorted);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sorted.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(sorted.get(i));
        }
        return sb.toString();
    }

    private void goNext() {
        applyExclusions();
        if (tableModel.getStudents().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "There are no active students to allocate. Load the roster first.",
                    "Empty Roster", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mainFrame.setActiveRoster(new ArrayList<>(tableModel.getStudents()));
        mainFrame.showCard("allocate");
    }
}
