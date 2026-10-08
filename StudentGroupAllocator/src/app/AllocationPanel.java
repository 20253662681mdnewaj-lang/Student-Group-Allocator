package app;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.table.DefaultTableCellRenderer;

public class AllocationPanel extends JPanel {

    private final MainFrame mainFrame;
    private final GroupTableModel groupTableModel = new GroupTableModel();
    private final JComboBox<String> strategyCombo;
    private final JComboBox<Integer> groupSizeCombo = new JComboBox<>(new Integer[]{2, 3});
    private final JLabel infoLabel = new JLabel(" ");

    private final Map<String, GroupingStrategy> strategies = new LinkedHashMap<>();

    public AllocationPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        registerStrategy(new MaleFemaleBalancedStrategy());
        registerStrategy(new InterleavedStrategy());
        registerStrategy(new SequentialStrategy());
        registerStrategy(new EvenOddStrategy());

        strategyCombo = new JComboBox<>(strategies.keySet().toArray(new String[0]));

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private void registerStrategy(GroupingStrategy strategy) {
        strategies.put(strategy.getName(), strategy);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        header.add(new JLabel("<html><b>Step 2: Allocation Strategy</b></html>"));
        header.add(new JLabel("Strategy:"));
        header.add(strategyCombo);
        header.add(new JLabel("Group Size:"));
        header.add(groupSizeCombo);

        JButton createButton = new JButton("Create Groups");
        createButton.addActionListener(e -> createGroups());
        header.add(createButton);
        return header;
    }

        private JScrollPane buildTable() {
   JTable table = new GroupedTable(groupTableModel);
    table.setRowHeight(24);

    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
    cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);  // Group
    table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);  // Roll No.
table.getColumnModel().getColumn(3).setCellRenderer(cellRenderer);  // Gender
        JPanel wrapper = new JPanel(new BorderLayout(5, 5));
        wrapper.add(new JLabel("Generated Groups (Preview)"), BorderLayout.NORTH);
        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);
        wrapper.add(infoLabel, BorderLayout.SOUTH);
        return new JScrollPane(wrapper);
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        JButton backButton = new JButton("\u2190 Back");
        backButton.addActionListener(e -> mainFrame.showCard("roster"));

        JButton nextButton = new JButton("Next Step \u2192");
        nextButton.addActionListener(e -> goNext());

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.add(backButton);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.add(nextButton);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }

    /*
     * Called by MainFrame whenever this panel is shown, to pick up the latest roster.
     */
    public void refresh() {
        List<Student> roster = mainFrame.getActiveRoster();
        infoLabel.setText(roster == null ? "No active roster yet." : roster.size() + " active student(s) available.");
    }

    private void createGroups() {
        List<Student> roster = mainFrame.getActiveRoster();
        if (roster == null || roster.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Go back to the Roster page and load an active roster first.",
                    "No Roster", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String strategyName = (String) strategyCombo.getSelectedItem();
        int groupSize = (Integer) groupSizeCombo.getSelectedItem();

        GroupingStrategy strategy = strategies.get(strategyName);
        List<List<Student>> groups = strategy.allocate(roster, groupSize);

        groupTableModel.setGroups(groups);
        mainFrame.setGeneratedGroups(groups, strategyName, groupSize);

        infoLabel.setText(groups.size() + " group(s) created using \"" + strategyName
                + "\" strategy (target size " + groupSize + ").");
    }

    private void goNext() {
        if (mainFrame.getGeneratedGroups() == null || mainFrame.getGeneratedGroups().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Click \"Create Groups\" before moving to the next step.",
                    "No Groups Yet", JOptionPane.WARNING_MESSAGE);
            return;
        }
        mainFrame.showCard("summary");
    }
}
