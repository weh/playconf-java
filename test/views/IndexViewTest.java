package views;

import static helpers.TestSetup.*;
import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;

import models.Proposal;
import models.Speaker;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import play.api.templates.Html;
import play.mvc.Http;
import views.html.index;

/**
 * Created by patrik on 17.06.2014.
 */
public class IndexViewTest {
    @Test
    public void indexViewShouldRenderKeynoteInformation() {
        running(fakeApplication(testGlobalSettings()), new Runnable() {
            @Override
            public void run() {
                Http.Context.current.set(testHttpContext());
                Proposal p = sampleProposal();
                Speaker s = sampleSpeaker();
                p.speaker = s;
                Html html = index.render(p);
                Document doc = Jsoup.parse(contentAsString(html));

                assertThat(doc.select("#title").text()).isEqualTo("Keynote - Best Java web development experience");
                assertThat(doc.select("#speakerName").text()).isEqualTo(s.name);
            }
        });
    }
}
