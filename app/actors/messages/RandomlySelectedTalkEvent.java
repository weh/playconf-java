package actors.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Proposal;
import play.libs.Json;

/**
 * Created by weh on 6/13/14.
 */
public class RandomlySelectedTalkEvent implements UserEvent {

    private final Proposal proposal;

    public RandomlySelectedTalkEvent(Proposal proposal) {
        this.proposal = proposal;
    }
    @Override
    public JsonNode json() {
        ObjectNode result = Json.newObject();
        result.put(MSG_TYPE, "proposalSubmission");

        result.put("name", proposal.speaker.name);
        result.put("twitterId", proposal.speaker.twitterId);
        result.put("title", proposal.title);
        result.put("proposal", proposal.proposal);
        result.put("pictureUrl", proposal.speaker.pictureUrl);

        return result;
    }
}
