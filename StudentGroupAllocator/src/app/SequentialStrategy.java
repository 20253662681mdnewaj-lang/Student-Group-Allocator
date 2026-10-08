package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SequentialStrategy implements GroupingStrategy {

    @Override
    public String getName() {
        return "Sequential";
    }

    @Override
    public List<List<Student>> allocate(List<Student> students, int groupSize) {
        List<Student> ordered = new ArrayList<>(students);
        ordered.sort(Comparator.comparingInt(Student::getRollNumber));
        return chunk(ordered, groupSize);
    }
}
