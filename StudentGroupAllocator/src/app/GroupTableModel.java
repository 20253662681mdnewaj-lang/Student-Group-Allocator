package app;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class GroupTableModel extends AbstractTableModel {

    private static final String[] COLUMNS = {"Group", "Roll No.", "Name", "Gender"};

    private List<List<Student>> groups = new ArrayList<>();
    private final List<int[]> rowIndex = new ArrayList<>(); // [groupIdx, studentIdx]

    public void setGroups(List<List<Student>> groups) {
        this.groups = groups;
        rowIndex.clear();
        for (int g = 0; g < groups.size(); g++) {
            for (int s = 0; s < groups.get(g).size(); s++) {
                rowIndex.add(new int[]{g, s});
            }
        }
        fireTableDataChanged();
    }

    public int getGroupOfRow(int row) {
        return rowIndex.get(row)[0];
    }

    @Override
    public int getRowCount() {
        return rowIndex.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public Object getValueAt(int rowIndex_, int columnIndex) {
        int[] idx = rowIndex.get(rowIndex_);
        Student s = groups.get(idx[0]).get(idx[1]);
        switch (columnIndex) {
            case 0: return "Group " + (idx[0] + 1);
            case 1: return s.getRollNumber();
            case 2: return s.getName();
            case 3: return s.getGender().name();
            default: return null;
        }
    }
}
