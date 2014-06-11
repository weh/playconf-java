package actors.messages;

/**
 * Created by weh on 6/11/14.
 */
public class CloseConnectionEvent {

    private String uuid;

    public CloseConnectionEvent(String uuid) {
        this.uuid = uuid;
    }

    public String uuid() { return uuid; }
}
