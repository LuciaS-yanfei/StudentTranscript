package persistence;

import model.Award;
import model.Course;
import model.StudentTranscript;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads student transcript from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads StudentTranscript from file and returns it;
    // throws IOException if an error occurs reading data from file
    public StudentTranscript read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseStudentTranscript(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses student transcript from JSON object and returns it
    private StudentTranscript parseStudentTranscript(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int id = jsonObject.getInt("ID");
        StudentTranscript ts = new StudentTranscript(name, id);
        addCourses(ts, jsonObject);
        addAwards(ts, jsonObject);
        return ts;
    }

    // MODIFIES: ts
    // EFFECTS: parses courses from JSON object and adds them to student transcript
    private void addCourses(StudentTranscript ts, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("courses");
        for (Object json : jsonArray) {
            JSONObject nextCourse = (JSONObject) json;
            addCourse(ts, nextCourse);
        }
    }

    // MODIFIES: ts
    // EFFECTS: parses course from JSON object and adds it to student transcript
    private void addCourse(StudentTranscript ts, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String semester = jsonObject.getString("semester");
        int credits = jsonObject.getInt("credits");
        int grade = jsonObject.getInt("grade");
        int courseAverage = jsonObject.getInt("courseAverage");
        Course course = new Course(name,semester,credits,grade,courseAverage);
        ts.addCourse(course);
    }

    // MODIFIES: ts
    // EFFECTS: parses awards from JSON object and adds them to student transcript
    private void addAwards(StudentTranscript ts, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("awards");
        for (Object json : jsonArray) {
            JSONObject nextAward = (JSONObject) json;
            addAward(ts, nextAward);
        }
    }

    // MODIFIES: ts
    // EFFECTS: parses award from JSON object and adds it to student transcript
    private void addAward(StudentTranscript ts, JSONObject jsonObject) {
        String awardName = jsonObject.getString("awardName");
        int year = jsonObject.getInt("year");
        double awardMoney = jsonObject.getDouble("awardMoney");
        Award award = new Award(awardName,year,awardMoney);
        ts.addAward(award);
    }

}
