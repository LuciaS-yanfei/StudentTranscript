package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentTranscriptTest {

    private StudentTranscript testTranscript;
    private Award a1;
    private Award a2;
    private Course c1;
    private Course c2;

    @BeforeEach
    public void setup() {
        testTranscript = new StudentTranscript("Lucia", 001);
        List<Award>  awards = new ArrayList<>();
        List<Course> courses = new ArrayList<>();
        a1 = new Award("Contest1st", 2022, 5000);
        a2 = new Award("Scholarship1", 2023, 8000);
        c1 = new Course("CPSC210","2023W1",4,100, 90);
        c2 = new Course("CPSC110","2022W1",4,90, 80);

    }

    @Test
    public void testConstructor() {
        assertEquals("Lucia", testTranscript.getStudentName());
        assertEquals(001, testTranscript.getStudentID());
        assertEquals(0,testTranscript.getCourses().size());
        assertEquals(0,testTranscript.getAwards().size());

    }

    @Test
    public void testSetters() {
        testTranscript.setStudentName("Yanfei");
        testTranscript.setStudentID(002);

        assertEquals("Yanfei", testTranscript.getStudentName());
        assertEquals(002, testTranscript.getStudentID());
        assertEquals(0,testTranscript.getCourses().size());
        assertEquals(0,testTranscript.getAwards().size());

    }

    @Test
    public void testAdd() {
        testTranscript.addAward(a1);
        testTranscript.addCourse(c1);

        assertEquals("Lucia", testTranscript.getStudentName());
        assertEquals(001, testTranscript.getStudentID());
        assertEquals(1,testTranscript.getAwards().size());
        assertEquals(a1,testTranscript.getAwards().get(0));
        assertEquals(1,testTranscript.getCourses().size());
        assertEquals(c1,testTranscript.getCourses().get(0));

        testTranscript.addAward(a1);
        testTranscript.addAward(a2);
        testTranscript.addCourse(c1);
        testTranscript.addCourse(c2);

        assertEquals(2,testTranscript.getAwards().size());
        assertEquals(a1,testTranscript.getAwards().get(0));
        assertEquals(a2,testTranscript.getAwards().get(1));
        assertEquals(2,testTranscript.getCourses().size());
        assertEquals(c1,testTranscript.getCourses().get(0));
        assertEquals(c2,testTranscript.getCourses().get(1));


    }

    @Test
    public void testRemove() {
        testTranscript.addAward(a1);
        testTranscript.addAward(a2);
        testTranscript.addCourse(c1);
        testTranscript.addCourse(c2);

        assertEquals("Lucia", testTranscript.getStudentName());
        assertEquals(001, testTranscript.getStudentID());
        assertEquals(2,testTranscript.getAwards().size());
        assertEquals(a1,testTranscript.getAwards().get(0));
        assertEquals(a2,testTranscript.getAwards().get(1));
        assertEquals(2,testTranscript.getCourses().size());
        assertEquals(c1,testTranscript.getCourses().get(0));
        assertEquals(c2,testTranscript.getCourses().get(1));

        testTranscript.removeAward(a1);
        testTranscript.removeCourse(c1);

        assertEquals(1,testTranscript.getAwards().size());
        assertEquals(a2,testTranscript.getAwards().get(0));
        assertEquals(1,testTranscript.getCourses().size());
        assertEquals(c2,testTranscript.getCourses().get(0));

    }

    @Test
    public void testCalculator() {
        Course c3 = new Course("TEST300", "2024S1", 2, 98, 90);
        testTranscript.addCourse(c1);
        testTranscript.addCourse(c2);
        testTranscript.addCourse(c3);

        assertEquals(96, testTranscript.getAverage());

    }

    @Test
    public void testBySemester() {
        Course c3 = new Course("TEST300", "2023W1", 3, 98, 90);
        Course c4 = new Course("TEST400", "2023W1", 4, 98, 90);
        Course c5 = new Course("TEST400", "2023W2", 1, 90, 80);

        testTranscript.addCourse(c1);
        testTranscript.addCourse(c2);
        testTranscript.addCourse(c3);
        testTranscript.addCourse(c4);
        testTranscript.addCourse(c5);

        StudentTranscript newTranscript = testTranscript.getTranscriptBySemester("2023W1");

        assertEquals("Lucia", newTranscript.getStudentName());
        assertEquals(001, newTranscript.getStudentID());
        assertEquals(3,newTranscript.getCourses().size());
        assertTrue(newTranscript.getCourses().contains(c1));
        assertTrue(newTranscript.getCourses().contains(c3));
        assertTrue(newTranscript.getCourses().contains(c4));
        assertEquals(0,newTranscript.getAwards().size());

    }

    @Test
    public void testAwardsInfo() {
        Award a3 = new Award("Prize1",2023,1000);
        Award a4 = new Award("Prize2",2020,5000);

        testTranscript.addAward(a1);
        testTranscript.addAward(a2);
        testTranscript.addAward(a3);
        testTranscript.addAward(a4);

        assertTrue(testTranscript.getAwardsInformation().contains("Contest1st"));
        assertTrue(testTranscript.getAwardsInformation().contains("Scholarship1"));
        assertTrue(testTranscript.getAwardsInformation().contains("Prize1"));
        assertTrue(testTranscript.getAwardsInformation().contains("Prize2"));

        assertEquals(19000, testTranscript.getAllMoney());

    }



}