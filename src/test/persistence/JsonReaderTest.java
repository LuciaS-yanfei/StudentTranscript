package persistence;

import model.Award;
import model.Course;
import model.StudentTranscript;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//represents tests for JsonReader
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            StudentTranscript ts = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyStudentTranscript() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyStudentTranscript.json");
        try {
            StudentTranscript ts = reader.read();
            assertEquals("Lucia Song", ts.getStudentName());
            assertEquals(68849942, ts.getStudentID());
            assertEquals(0, ts.getCourses().size());
            assertEquals(0, ts.getAwards().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }


    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralStudentTranscript.json");
        try {
            StudentTranscript ts = reader.read();
            assertEquals("Lucia Song", ts.getStudentName());
            assertEquals(68849942, ts.getStudentID());
            List<Course> courses = ts.getCourses();
            assertEquals(2, courses.size());
            checkCourse("CPSC210", courses.get(0));
            checkCourse("CPSC110", courses.get(1));

            List<Award> awards = ts.getAwards();
            assertEquals(2, awards.size());
            checkAward("Contest1st", awards.get(0));
            checkAward("Scholarship1", awards.get(1));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
