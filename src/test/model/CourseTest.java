package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

//represents tests for course
public class CourseTest {

    private Course testCourse;

    @BeforeEach
    public void setup() {
        testCourse = new Course("TEST101", "2023W1", 4, 99, 88);
    }

    @Test
    public void testConstructor() {
        assertEquals("TEST101", testCourse.getCourseName());
        assertEquals("2023W1", testCourse.getSemester());
        assertEquals(4, testCourse.getCredits());
        assertEquals(99, testCourse.getGrade());
        assertEquals(88, testCourse.getCourseAverage());

    }

    @Test
    public void testSetters() {
        testCourse.setCourseName("TEST200");
        testCourse.setSemester("2023W2");
        testCourse.setCredits(3);
        testCourse.setGrade(90);
        testCourse.setCourseAverage(90);

        assertEquals("TEST200", testCourse.getCourseName());
        assertEquals("2023W2", testCourse.getSemester());
        assertEquals(3, testCourse.getCredits());
        assertEquals(90, testCourse.getGrade());
        assertEquals(90, testCourse.getCourseAverage());
    }

    @Test
    public void testToString() {
        testCourse.setCourseName("TEST200");
        testCourse.setSemester("2023W2");
        testCourse.setCredits(3);
        testCourse.setGrade(90);
        testCourse.setCourseAverage(90);
        assertEquals("TEST200 in 2023W2 is worth 3 credits  student's grade: 90 course average: 90\n",
                testCourse.toString());
//        courseName + " in " + semester + " is worth " + credits + " credits "
//                + " student's grade: " + grade + " course average: " + courseAverage + "\n"
    }



}