package app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

public class SummaryPanel extends JPanel {

    private final MainFrame mainFrame;
    private final GroupTableModel groupTableModel = new GroupTableModel();
    private final JLabel summaryLabel = new JLabel(" ");

    public SummaryPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(buildHeader(), BorderLayout.NORTH);
        add(buildTable(), BorderLayout.CENTER);
        add(buildFooter(), BorderLayout.SOUTH);
    }

    private JPanel buildHeader() {
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        header.add(new JLabel("<html><b>Step 3: Final Allocation Summary</b></html>"));
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
        wrapper.add(summaryLabel, BorderLayout.NORTH);
        wrapper.add(new JScrollPane(table), BorderLayout.CENTER);
        return new JScrollPane(wrapper);
    }

    private JPanel buildFooter() {
        JPanel footer = new JPanel(new BorderLayout());
        JButton backButton = new JButton("\u2190 Back");
        backButton.addActionListener(e -> mainFrame.showCard("allocate"));

        JButton saveButton = new JButton("Save Group Allocation File");
        saveButton.addActionListener(e -> saveToCsv());

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.add(backButton);
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        right.add(saveButton);

        footer.add(left, BorderLayout.WEST);
        footer.add(right, BorderLayout.EAST);
        return footer;
    }

    public void refresh() {
        List<List<Student>> groups = mainFrame.getGeneratedGroups();
        groupTableModel.setGroups(groups == null ? new java.util.ArrayList<>() : groups);

        if (groups == null || groups.isEmpty()) {
            summaryLabel.setText("No groups generated yet — go back to Step 2.");
            return;
        }
        int totalStudents = 0;
        for (List<Student> g : groups) {
            totalStudents += g.size();
        }
        summaryLabel.setText(String.format(
                "Strategy: %s   |   Groups: %d   |   Target group size: %d   |   Total students: %d",
                mainFrame.getLastStrategyName(), groups.size(), mainFrame.getLastGroupSize(), totalStudents));
    }

    private void saveToCsv() {
        List<List<Student>> groups = mainFrame.getGeneratedGroups();
        if (groups == null || groups.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "There is nothing to save yet. Generate groups on the previous step first.",
                    "Nothing to Save", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FileNameExtensionFilter csvFilter = new FileNameExtensionFilter("CSV File (*.csv)", "csv");
        FileNameExtensionFilter txtFilter = new FileNameExtensionFilter("Text File (*.txt)", "txt");

        JFileChooser chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(csvFilter);
        chooser.addChoosableFileFilter(txtFilter);
        chooser.setFileFilter(csvFilter);
        chooser.setSelectedFile(new File("group_allocation.csv"));

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String extension = (chooser.getFileFilter() == txtFilter) ? ".txt" : ".csv";
        File file = chooser.getSelectedFile();
        if (!file.getName().toLowerCase().endsWith(extension)) {
            // Strip any other extension the user may have typed, then append the chosen one.
            String name = file.getName();
            int dot = name.lastIndexOf('.');
            if (dot > 0) {
                name = name.substring(0, dot);
            }
            file = new File(file.getParentFile(), name + extension);
        }

        try {
            CsvExporter.export(groups, file);
            JOptionPane.showMessageDialog(this,
                    "Saved to: " + file.getAbsolutePath(),
                    "Export Successful", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Could not save file: " + ex.getMessage(),
                    "Export Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}
