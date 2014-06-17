package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.*;
import static helpers.TestSetup.*;
import static org.mockito.Mockito.*;

import akka.actor.ActorRef;
import external.services.OAuthService;
import org.junit.Test;
import play.libs.F;
import play.libs.OAuth;
import play.mvc.Http;
import play.mvc.Result;
import play.test.Helpers;

/**
 * Created by weh on 6/17/14.
 */
public class ApplicationTest {

    @Test
    public void redirectToOAuthProviderForRegistration() {
        Http.Context.current.set(testHttpContext());
        OAuthService oauth = mock(OAuthService.class);
        F.Tuple<String, OAuth.RequestToken> t = new F.Tuple<>(
                "twitter.redirect.url", new OAuth.RequestToken("twitter.token", "twitter.secret"));
        when(oauth.retrieveRequestToken(anyString())).thenReturn(t);

        Application app = new Application(mock(ActorRef.class), oauth);

        Result result = app.register();

        assertThat(status(result)).isEqualTo(Http.Status.SEE_OTHER);
        assertThat(Helpers.redirectLocation(result)).isEqualTo("twitter.redirect.url");

        Http.Flash flash = Http.Context.current().flash();
        assertThat(flash.get("request_token")).isEqualTo("twitter.token");
        assertThat(flash.get("request_secret")).isEqualTo("twitter.secret");
    }
}
