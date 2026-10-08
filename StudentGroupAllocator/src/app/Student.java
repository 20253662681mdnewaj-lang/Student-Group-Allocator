package app;
//data model for every single student
public class Student {

    private final int rollNumber;
    private String name;
    private Gender gender;
    private boolean active;

  public Student(int rollNumber, String name, Gender gender) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.gender = gender;
        this.active = true;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Roll " + rollNumber + " - " + name;
    }
}
