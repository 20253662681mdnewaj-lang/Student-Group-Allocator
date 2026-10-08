package app;

import javax.swing.JTable;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

/**
 * A JTable that shows the Group column as ONE merged cell per group
 * (spanning all members of that group), with the group number centered
 * both horizontally and vertically inside it.
 *
 * Swing has no built-in cell merging, so after the normal table is painted
 * this class paints a merged rectangle over column 0 for each run of rows
 * that belong to the same group.
 */
public class GroupedTable extends JTable {

    private final GroupTableModel groupModel;

    public GroupedTable(GroupTableModel model) {
        super(model);
        this.groupModel = model;
        setRowHeight(24);
        // Keep the Group column fixed at position 0 so the merged painting lines up.
        getTableHeader().setReorderingAllowed(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            paintMergedGroupCells(g2);
        } finally {
            g2.dispose();
        }
    }

    private void paintMergedGroupCells(Graphics2D g2) {
        int rows = getRowCount();
        if (rows == 0 || getColumnCount() == 0) {
            return;
        }
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Rectangle clip = g2.getClipBounds();
        FontMetrics fm = g2.getFontMetrics(getFont());

        int start = 0;
        while (start < rows) {
            int group = groupModel.getGroupOfRow(start);
            int end = start;
            while (end + 1 < rows && groupModel.getGroupOfRow(end + 1) == group) {
                end++;
            }

            Rectangle first = getCellRect(start, 0, true);
            Rectangle last = getCellRect(end, 0, true);
            Rectangle merged = new Rectangle(first.x, first.y, first.width,
                    last.y + last.height - first.y);

            if (clip == null || clip.intersects(merged)) {
                // Cover the individual cells of this group in column 0.
                g2.setColor(getBackground());
                g2.fillRect(merged.x, merged.y, merged.width, merged.height);

                // Redraw only the outer edges (right + bottom) as grid lines.
                g2.setColor(getGridColor());
                g2.drawLine(merged.x, merged.y + merged.height - 1,
                        merged.x + merged.width - 1, merged.y + merged.height - 1);
                g2.drawLine(merged.x + merged.width - 1, merged.y,
                        merged.x + merged.width - 1, merged.y + merged.height - 1);

                // Group number, centered in the merged cell.
                String text = String.valueOf(group + 1);
                int tx = merged.x + (merged.width - fm.stringWidth(text)) / 2;
                int ty = merged.y + (merged.height - fm.getHeight()) / 2 + fm.getAscent();
                g2.setColor(getForeground());
                g2.setFont(getFont());
                g2.drawString(text, tx, ty);
            }

            start = end + 1;
        }
    }
}

