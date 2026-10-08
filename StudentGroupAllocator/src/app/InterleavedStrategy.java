package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InterleavedStrategy implements GroupingStrategy {

    @Override
    public String getName() {
        return "Interleaved";
    }

    @Override
    public List<List<Student>> allocate(List<Student> students, int groupSize) {
        List<Student> ordered = new ArrayList<>(students);
        ordered.sort(Comparator.comparingInt(Student::getRollNumber));

        int total = ordered.size();
        if (total == 0) {
            return new ArrayList<>();
        }
        int numGroups = (int) Math.ceil(total / (double) groupSize);

        List<List<Student>> groups = new ArrayList<>();
        for (int i = 0; i < numGroups; i++) {
            groups.add(new ArrayList<>());
        }
        for (int i = 0; i < ordered.size(); i++) {
            groups.get(i % numGroups).add(ordered.get(i));
        }
        return groups;
    }
}
