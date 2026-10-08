package app;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MaleFemaleBalancedStrategy implements GroupingStrategy {

    @Override
    public String getName() {
        return "Male-Female Balanced";
    }

    @Override
    public List<List<Student>> allocate(List<Student> students, int groupSize) {
        List<Student> males = new ArrayList<>();
        List<Student> females = new ArrayList<>();
        for (Student s : students) {
            if (s.getGender() == Gender.MALE) {
                males.add(s);
            } else {
                females.add(s);
            }
        }                       
        males.sort(Comparator.comparingInt(Student::getRollNumber));
        females.sort(Comparator.comparingInt(Student::getRollNumber));

        List<Student> zipped = new ArrayList<>();
        int i = 0, j = 0;
        while (i < males.size() || j < females.size()) {
            if (i < males.size()) {
                zipped.add(males.get(i++));
            }
            if (j < females.size()) {
                zipped.add(females.get(j++));
            }
        }
        return chunk(zipped, groupSize);
    }
}
