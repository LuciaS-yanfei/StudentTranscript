package persistence;

import model.StudentTranscript;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

// Represents a writer that writes JSON representation of student transcript to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws IOException {
        writer = new PrintWriter(destination);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of student transcript to file
    public void write(StudentTranscript ts) {
        JSONObject json = ts.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.append(json);
    }
}
