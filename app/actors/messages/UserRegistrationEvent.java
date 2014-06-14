package actors.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.RegisteredUser;
import play.libs.Json;

/**
 * Created by patrik on 14.06.2014.
 */
public class UserRegistrationEvent implements UserEvent {

    private final RegisteredUser user;

    public UserRegistrationEvent(RegisteredUser user) {
        this.user = user;
    }

    @Override
    public JsonNode json() {
        final ObjectNode result = Json.newObject();
        result.put(MSG_TYPE, "registeredUser");
        result.put("name", user.name);
        result.put("twitterId", user.twitterId);
        result.put("description", user.description);
        result.put("pictureUrl", user.pictureUrl);
        return result;
    }
}
