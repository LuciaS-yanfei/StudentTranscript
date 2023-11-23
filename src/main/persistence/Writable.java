package persistence;

import org.json.JSONObject;

// Represents a interface for general toJson method
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
