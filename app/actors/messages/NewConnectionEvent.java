package actors.messages;

import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.WebSocket.Out;

/**
 * Created by weh on 6/11/14.
 */
public class NewConnectionEvent {

    private String uuid;
    private Out<JsonNode> out;

    public NewConnectionEvent(String uuid, Out<JsonNode> out) {
        this.uuid = uuid;
        this.out = out;
    }

    public String uuid() {return uuid;}

    public Out<JsonNode> out() {return out;}
}
