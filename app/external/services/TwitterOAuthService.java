package external.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.ning.http.util.Base64;
import common.Functions;
import play.libs.F.*;
import play.libs.F.Promise;
import play.libs.F.Tuple;
import play.libs.OAuth;
import play.libs.WS;
import play.libs.WS.WSRequestHolder;

/**
 * Created by patrik on 14.06.2014.
 */
public class TwitterOAuthService implements OAuthService {

    private final String consumerKey;
    private final String consumerSecret;
    private final OAuth.ConsumerKey key;
    private final OAuth oauthHelper;

    public TwitterOAuthService(String consumerKey, String consumerSecret) {

        this.consumerKey = consumerKey;
        this.consumerSecret = consumerSecret;
        this.key = new OAuth.ConsumerKey(consumerKey,consumerSecret);
        this.oauthHelper = new OAuth(new OAuth.ServiceInfo(
                "https://api.twitter.com/oauth/request_token",
                "https://api.twitter.com/oauth/access_token",
                "https://api.twitter.com/oauth/authorize",
                key
        ));
    }

    @Override
    public Tuple<String, OAuth.RequestToken> retrieveRequestToken(String callbackUrl) {
        OAuth.RequestToken rt = oauthHelper.retrieveRequestToken(callbackUrl);
        return new Tuple<String, OAuth.RequestToken>(oauthHelper.redirectUrl(rt.token), rt);
    }

    @Override
    public Promise<JsonNode> registeredUserProfile(OAuth.RequestToken token, String authVerifier) {
        OAuth.RequestToken accessToken = oauthHelper.retrieveAccessToken(token, authVerifier);
        WS.WSRequestHolder request = WS.url("https://api.twitter.com/1.1/account/settings.json");
        Promise<WS.Response> result = request.sign(new OAuth.OAuthCalculator(key,accessToken)).get();
        Promise<String> screenName = result.map(Functions.responseToJson).map(Functions.findTextElement("screen_name"));
        return screenName.flatMap(userProfile);
    }

    public Function<String, Promise<JsonNode>> userProfile = new Function<String, Promise<JsonNode>>() {
        public Promise<JsonNode> apply(final String screenName) {
            Promise<String> response = authenticateApplication().map(
                    Functions.responseToJson).map(Functions.findTextElement("access_token"));
            return response.flatMap(fetchProfile(screenName)).recover(Functions.fetchUserError);
        }
    };

    private static Function<String, Promise<JsonNode>> fetchProfile(
            final String screenName) {
        return new Function<String, Promise<JsonNode>>() {
            public Promise<JsonNode> apply(String accessToken) {
                WSRequestHolder req = WS
                        .url("https://api.twitter.com/1.1/users/show.json")
                        .setQueryParameter("screen_name", screenName)
                        .setHeader("Authorization", "Bearer " + accessToken);
                Promise<WS.Response> promise = req.get();
                return promise.map(Functions.responseToJson);
            }
        };
    }

    private Promise<WS.Response> authenticateApplication() {
        WSRequestHolder req = WS
                .url("https://api.twitter.com/oauth2/token")
                .setHeader("Authorization", "Basic " + bearerToken())
                .setContentType(
                        "application/x-www-form-urlencoded;charset=UTF-8");
        return req.post("grant_type=client_credentials");
    }

    private String bearerToken() {
        return Base64.encode((consumerKey + ":" + consumerSecret).getBytes());
    }
}
