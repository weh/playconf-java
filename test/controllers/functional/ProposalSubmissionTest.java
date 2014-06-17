package controllers.functional;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

import org.junit.Test;
import play.libs.F;
import play.test.TestBrowser;

import java.util.HashMap;
import java.util.Map;

import controllers.routes;


/**
 * Created by weh on 6/17/14.
 */
public class ProposalSubmissionTest {

    @Test
    public void proposalSubmissionFlow() {
        Map<String, Object> dbSettings = new HashMap<>();
        dbSettings.put("db.default.url", "jdbc:mysql://localhost:3306/playconf_test");
        running(testServer(3333, fakeApplication(dbSettings)), HTMLUNIT, new F.Callback<TestBrowser>() {
            @Override
            public void invoke(TestBrowser browser) throws Throwable {
                browser.goTo("http://localhost:3333" + routes.Application.newProposal().url());

                // check the Browser Title
                assertThat(browser.title()).isEqualTo("PlayConf 2014 - Submit new talk");

                // now fill the form
                browser.fill("#title").with("This is a test play presentation");
                browser.fill("#proposal").with("This presentation is about testing in play");
                browser.fill("#speaker_name").with("Peter Parker");
                browser.fill("#speaker_email").with("peter@daily-buggle.com");
                browser.fill("#speaker_bio").with("Peter is a guy that has many secret Weapons");
                browser.fill("#speaker_pictureUrl").with("peter_parker.jpg");
                browser.fill("#speaker_twitterId").with("peepa");
                browser.submit("#submitForm");

                // now check for Flash message
                assertThat(browser.findFirst("#message").getText()).isEqualTo("Thanks for submitting a proposal");
            }

        });
    }
}
