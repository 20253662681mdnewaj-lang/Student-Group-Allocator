## Student Group Allocator

>> A Java Swing desktop application that loads a class roster, excludes absent students, splits the rest into groups using one of four allocation strategies, and exports the final result to CSV/TXT.

Built as a 3-member course project.

-------------------------------------

## How it works

** Roster: load the class list and exclude absent students by roll number.

** Allocate: pick a strategy (Sequential, Interleaved, Even-Odd, Male-Female Balanced) and a group size (2 or 3).

** Save: review the groups and export them as a CSV or TXT file.
Run

## Requires JDK 11+.

>bash
>javac -d out src/app/*.java
>java -cp out app.Main

* Or open the project in any Java IDE and run app.Main.

## Tech

Java, Swing (JTable, JComboBox, CardLayout), Strategy design pattern.

## Team
Member A: Page 1 (Roster)
Member B: Page 2 (Allocation strategies)
Member C: Page 3 (Export) and integration

-------------------------------------

## Features

- Load the class roster (51 students) with editable names
- Exclude absent students by typing their roll numbers (comma-separated, e.g. 15,42)
- Change a student's gender directly inside the table using radio buttons
- Four allocation strategies: Sequential, Interleaved, Even-Odd, Male-Female Balanced
- Choose a group size of 2 or 3
- Live preview of the generated groups, with one merged group-number cell per group
- Export the final allocation to **CSV** or **TXT** (opens directly in Excel)
- Friendly validation dialogs instead of crashes (invalid input, empty roster, nothing to save)

## Project Structure

student-group-allocator/
├── README.md
├── .gitignore
├── docs/
│   └── screenshots
└── src/
    └── app/
        ├── Main.java                      Entry point
        ├── MainFrame.java                 CardLayout host and shared state
        │
        ├── RosterPanel.java               Page 1 UI
        ├── AllocationPanel.java           Page 2 UI
        ├── SummaryPanel.java              Page 3 UI
        │
        ├── Student.java                   Data model
        ├── Gender.java                    Enum: MALE / FEMALE
        │
        ├── GroupingStrategy.java          Strategy interface + chunk() helper
        ├── SequentialStrategy.java
        ├── InterleavedStrategy.java
        ├── EvenOddStrategy.java
        ├── MaleFemaleBalancedStrategy.java
        │
        ├── StudentTableModel.java         Table model for Page 1
        ├── GroupTableModel.java           Table model for Pages 2 and 3
        ├── GroupedTable.java              JTable with merged group cells
        ├── GenderCellComponent.java       Radio-button cell renderer
        │
        └── CsvExporter.java               CSV/TXT writer
