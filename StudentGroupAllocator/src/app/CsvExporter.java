package app;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public final class CsvExporter {

    private CsvExporter() {
    }

    public static void export(List<List<Student>> groups, File file) throws IOException {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Group,RollNumber,Name,Gender\n");
        for (int g = 0; g < groups.size(); g++) {
                int groupNumber = g + 1;
          for (Student s : groups.get(g)) {
              writer.write(groupNumber + ","
                 + s.getRollNumber() + ","
                 + escape(s.getName()) + ","
                 + s.getGender().name()+ "\n");
                }
            }
        }
    }

    private static String escape(String value) {
        if (value == null) {
            return "";
        }
        if (value.contains(",") || value.contains("\"")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }
}
