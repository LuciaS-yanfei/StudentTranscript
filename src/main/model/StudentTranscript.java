package model;

import java.util.ArrayList;
import java.util.List;

public class StudentTranscript {
    private String studentName;
    private int studentId;
    private List<Course> courses;
    private List<Award> awards;

    public StudentTranscript(String studentName, int studentId) {
        this.studentName = studentName;
        this.studentId = studentId;
        courses = new ArrayList<>();
        awards = new ArrayList<>();

    }

    //getters
    public String getStudentName() {
        return studentName;
    }

    public int getStudentID() {
        return studentId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public List<Award> getAwards() {
        return awards;
    }

    //setters

    //MODIFIES: this
    //EFFECTS: sets student name
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    //MODIFIES: this
    //EFFECTS: sets student id
    public void setStudentID(int studentId) {
        this.studentId = studentId;
    }

    //REQUIRES:
    //MODIFIES: this
    //EFFECTS: add a course to student transcript if that course is not in the list
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
        }
    }

    //REQUIRES: course to remove is already in the transcript
    //MODIFIES: this
    //EFFECTS: remove a course from student transcript
    public void removeCourse(Course course) {
        courses.remove(course);
    }


    //REQUIRES: a list of courses in the transcript
    //EFFECTS: returns course GPA in percentage, returns 0 if there's no courses in the transcript
    public int getAverage() {
        return calculateAverage(courses);
    }

    //EFFECTS: calculates average over all courses in the list;
    //         0 if List of Courses is empty
    public int calculateAverage(List<Course> courses) {
        int allGrades = 0;
        int allCredits = 0;

        if (!courses.isEmpty()) {
            for (Course next : courses) {
                allGrades += next.getGrade() * next.getCredits();
                allCredits += next.getCredits();
            }

            double result = (double) allGrades / allCredits;
            return (int) (result + 0.5);
        }
        return 0;

    }

    //REQUIRES: a list of courses in the transcript
    //EFFECTS: give all the courses in the selected semester
    public List<Course> getCourseBySemester(String semester) {
        List<Course> courseBySemester = new ArrayList<>();

        for (Course next : courses) {
            if (semester.equals(next.getSemester())) {
                courseBySemester.add(next);
            }
        }
        return courseBySemester;
    }

    //REQUIRES: a student transcript
    //EFFECTS: give student's transcript of the selected semester
    public StudentTranscript getTranscriptBySemester(String semester) {
        StudentTranscript transcriptBySemester = new StudentTranscript(studentName,studentId);
        transcriptBySemester.courses = getCourseBySemester(semester);
        transcriptBySemester.awards = awards;
        return transcriptBySemester;
    }

    //MODIFIES: this
    //EFFECTS: add an award to student transcript if that award is not in the list
    public void addAward(Award award) {
        if (!awards.contains(award)) {
            awards.add(award);
        }
    }

    //REQUIRES: award is already in the list
    //MODIFIES: this
    //EFFECTS: remove award to student transcript
    public void removeAward(Award award) {
        awards.remove(award);
    }

    //REQUIRES: a list of awards in the transcript
    //EFFECTS: returns a List of String of all the award's information in the list
    public List<String> getAwardsInformation() {
        List<String> awardsInformation = new ArrayList<>();
        for (Award next: awards) {
            String name = next.getAwardName();
            int date = next.getYear();
            double prize = next.getAwardMoney();

            awardsInformation.add(name);
            awardsInformation.add(" won in ");
            awardsInformation.add(String.valueOf(date));
            awardsInformation.add(" Money won: ");
            awardsInformation.add(String.valueOf(prize));
            awardsInformation.add("\n");
        }
        return awardsInformation;
    }

    //REQUIRES: a list of awards in the transcript
    //MODIFIES: this
    //EFFECTS: give the total money amount(in $) for all the awards in the list
    public int getAllMoney() {
        int money = 0;
        for (Award next: awards) {
            money += next.getAwardMoney();
        }
        return money;
    }








}