package model;

//represents a course
public class Course {
    private String courseName;
    private String semester;
    private int credits;
    private int grade;
    private int courseAverage;

    // EFFECTS: creates a course with courseName, semester, grade and courseAverage
    public Course(String courseName,String semester,int credits,int grade,int courseAverage) {
        this.courseName = courseName;
        this.semester = semester;
        this.credits = credits;
        this.grade = grade;
        this.courseAverage = courseAverage;
    }

    //getters
    public String getCourseName() {
        return courseName;
    }

    public String getSemester() {
        return semester;
    }


    public int getCredits() {
        return credits;
    }

    public int getGrade() {
        return grade;
    }

    public int getCourseAverage() {
        return courseAverage;
    }

    //setters
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setCourseAverage(int courseAverage) {
        this.courseAverage = courseAverage;
    }

}
