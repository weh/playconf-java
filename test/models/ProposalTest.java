package models;

import static helpers.TestSetup.*;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

import com.avaje.ebean.Ebean;
import org.junit.Test;

/**
 * Created by patrik on 17.06.2014.
 */
public class ProposalTest {

    @Test
    public void saveNewProposal() {
        running(fakeApplication(testGlobalSettings()), new Runnable() {

            @Override
            public void run() {
                Proposal s = sampleProposal();
                s.save();
                assertThat(rowCount()).isEqualTo(1);
            }

        });
    }


    @Test
    public void savingProposalAlsoSavesSpeaker() {
        running(fakeApplication(testGlobalSettings()), new Runnable() {
            @Override
            public void run() {
                Proposal s = sampleProposal();
                s.speaker = sampleSpeaker();
                s.save();
                assertThat(rowCount()).isEqualTo(1);
                assertThat(Ebean.find(Speaker.class).findUnique().name).isEqualTo("Nilanjan");
            }
        });
    }

    private int rowCount() {
        return Ebean.find(Proposal.class).findRowCount();
    }
}
