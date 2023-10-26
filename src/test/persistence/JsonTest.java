package persistence;

import model.Award;
import model.Course;

import static org.junit.jupiter.api.Assertions.assertEquals;

//represents tests for json
public class JsonTest {

    protected void checkCourse(String name, Course course) {
        assertEquals(name, course.getCourseName());
    }

    protected void checkAward(String awardName, Award award) {
        assertEquals(awardName, award.getAwardName());
    }

}