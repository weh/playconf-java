package actors.messages;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by patrik on 11.06.2014.
 */
public interface UserEvent {
    public JsonNode json();
}
