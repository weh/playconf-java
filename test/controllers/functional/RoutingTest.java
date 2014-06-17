package controllers.functional;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
import static helpers.TestSetup.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import play.api.test.FakeApplication;
import play.mvc.Http;
import play.mvc.Result;

/**
 * Created by weh on 6/17/14.
 */
public class RoutingTest {

    @Test
    public void routeToIndexAction() {

        running(fakeApplication(), new Runnable() {
            @Override
            public void run() {
                Result result = route(fakeRequest("GET", "/"));
                assertThat(status(result)).isEqualTo(Http.Status.OK);
                Document doc = Jsoup.parse(contentAsString(result));
                assertThat(doc.select("#title").text()).isEqualTo("Keynote - History of playframework");
                assertThat(doc.select("#speakerName").text()).isEqualTo("Guillaume Bort");
                assertThat(doc.select("#twitterId").text()).isEqualTo("guillaumebort");
            }
        });

    }
}
