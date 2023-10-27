package persistence;

import model.Award;
import model.Course;
import model.StudentTranscript;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//represents tests for JsonWriter
public class JsonWriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            StudentTranscript ts = new StudentTranscript("Lucia Song", 68849942);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyStuentTranscript() {
        try {
            StudentTranscript ts = new StudentTranscript("Lucia Song", 68849942);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyStudentTranscript.json");
            writer.open();
            writer.write(ts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyStudentTranscript.json");
            ts = reader.read();
            assertEquals("Lucia Song", ts.getStudentName());
            assertEquals(68849942, ts.getStudentID());
            assertEquals(0, ts.getCourses().size());
            assertEquals(0, ts.getAwards().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralStudentTranscript() {
        try {
            StudentTranscript ts = new StudentTranscript("Lucia Song", 68849942);
            ts.addCourse(new Course("MATH100","2023W1",3,90,70));
            ts.addCourse(new Course("MATH110","2023W2",3,95,75));
            ts.addAward(new Award("Contest1st",2022,5000));
            ts.addAward(new Award("Scholarship1",2023,8000));

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralStudentTranscript.json");
            writer.open();
            writer.write(ts);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralStudentTranscript.json");
            ts = reader.read();
            assertEquals("Lucia Song", ts.getStudentName());
            assertEquals(68849942, ts.getStudentID());
            List<Course> courses = ts.getCourses();
            assertEquals(2, ts.getCourses().size());
            checkCourse("MATH100", courses.get(0));
            checkCourse("MATH110", courses.get(1));

            List<Award> awards = ts.getAwards();
            assertEquals(2, awards.size());
            checkAward("Contest1st", awards.get(0));
            checkAward("Scholarship1", awards.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
