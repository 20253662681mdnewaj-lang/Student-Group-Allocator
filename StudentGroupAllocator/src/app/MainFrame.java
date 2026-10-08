package app;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.Image;

public class MainFrame extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel mainPanel = new JPanel(cardLayout);

    private final RosterPanel rosterPanel;
    private final AllocationPanel allocationPanel;
    private final SummaryPanel summaryPanel;

    private List<Student> activeRoster;
    private List<List<Student>> generatedGroups;
    private String lastStrategyName = "-";
    private int lastGroupSize = 0;

    public MainFrame() {
        super("Student Group Allocator");
        //adding icon
        try{
            Image icon=new ImageIcon(getClass().getResource("/Resources/app_logo_64.png")).getImage();
            setIconImage(icon);
        } catch (Exception ignored) {
        }
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 620);
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);

        rosterPanel = new RosterPanel(this);
        allocationPanel = new AllocationPanel(this);
        summaryPanel = new SummaryPanel(this);

        mainPanel.add(rosterPanel, "roster");
        mainPanel.add(allocationPanel, "allocate");
        mainPanel.add(summaryPanel, "summary");

        setContentPane(mainPanel);
        cardLayout.show(mainPanel, "roster");
    }

    public void showCard(String name) {
        cardLayout.show(mainPanel, name);
        if ("allocate".equals(name)) {
            allocationPanel.refresh();
        } else if ("summary".equals(name)) {
            summaryPanel.refresh();
        }
    }

    public List<Student> getActiveRoster() {
        return activeRoster;
    }

    public void setActiveRoster(List<Student> activeRoster) {
        this.activeRoster = activeRoster;
    }

    public List<List<Student>> getGeneratedGroups() {
        return generatedGroups;
    }

    public void setGeneratedGroups(List<List<Student>> generatedGroups, String strategyName, int groupSize) {
        this.generatedGroups = generatedGroups;
        this.lastStrategyName = strategyName;
        this.lastGroupSize = groupSize;
    }

    public String getLastStrategyName() {
        return lastStrategyName;
    }

    public int getLastGroupSize() {
        return lastGroupSize;
    }
}
