package actors.messages;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Proposal;
import play.libs.Json;

/**
 * Created by patrik on 11.06.2014.
 */
public class NewProposalEvent implements UserEvent {

    private Proposal proposal;

    public NewProposalEvent(Proposal proposal) {
        this.proposal = proposal;
    }

    @Override
    public JsonNode json() {
        ObjectNode result = Json.newObject();
        result.put("messageType", "newProposal");

        result.put("speakerName", proposal.speaker.name);
        result.put("twitterId", proposal.speaker.twitterId);
        result.put("title", proposal.title);
        result.put("pictureUrl", proposal.speaker.pictureUrl);

        return result;
    }
}
