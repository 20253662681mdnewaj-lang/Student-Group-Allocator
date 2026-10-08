package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EvenOddStrategy implements GroupingStrategy {

    @Override
    public String getName() {
        return "Even-Odd";
    }

    @Override
    public List<List<Student>> allocate(List<Student> students, int groupSize) {
        List<Student> evens = new ArrayList<>();
        List<Student> odds = new ArrayList<>();
        for (Student s : students) {
            if (s.getRollNumber() % 2 == 0) {
                evens.add(s);
            } else {
                odds.add(s);
            }
        }
        evens.sort(Comparator.comparingInt(Student::getRollNumber));
        odds.sort(Comparator.comparingInt(Student::getRollNumber));

        List<Student> zipped = new ArrayList<>();
        int i = 0, j = 0;
        while (i < evens.size() || j < odds.size()) {
            if (i < evens.size()) {
                zipped.add(evens.get(i++));
            }
            if (j < odds.size()) {
                zipped.add(odds.get(j++));
            }
        }
        return chunk(zipped, groupSize);
    }
}
