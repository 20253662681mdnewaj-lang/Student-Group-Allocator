package app;

import java.util.ArrayList;
import java.util.List;

public interface GroupingStrategy {
// method without implementation
    String getName(); // each strategyNAME

    List<List<Student>> allocate(List<Student> students, int groupSize);
// implemented class will contain these
    default List<List<Student>> chunk(List<Student> ordered, int groupSize) {
        List<List<Student>> groups = new ArrayList<>();
        for (int i = 0; i < ordered.size(); i += groupSize) {
            int end = Math.min(i + groupSize, ordered.size());
            groups.add(new ArrayList<>(ordered.subList(i, end)));
        }
        return groups;
    }
}
