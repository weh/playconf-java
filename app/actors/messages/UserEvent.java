package actors.messages;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Created by patrik on 11.06.2014.
 */
public interface UserEvent {
    public static final String MSG_TYPE = "messageType";
    public JsonNode json();
}
