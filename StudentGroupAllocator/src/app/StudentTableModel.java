package app;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class StudentTableModel extends AbstractTableModel {

    private static final String[] COLUMNS = {"Roll No.", "Name", "Gender"};

    private List<Student> students = new ArrayList<>();

    public void setStudents(List<Student> students) {
        this.students = students;
        fireTableDataChanged();
    }

    public List<Student> getStudents() {
        return students;
    }

    @Override
    public int getRowCount() {
        return students.size();
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
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0: return Integer.class;
            case 2: return Gender.class;
            default: return String.class;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 1 || columnIndex == 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student s = students.get(rowIndex);
        switch (columnIndex) {
            case 0: return s.getRollNumber();
            case 1: return s.getName();
            case 2: return s.getGender();
            default: return null;
        }
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Student s = students.get(rowIndex);
        if (columnIndex == 1) {
            s.setName(String.valueOf(value));
        } else if (columnIndex == 2) {
            s.setGender((Gender) value);
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
