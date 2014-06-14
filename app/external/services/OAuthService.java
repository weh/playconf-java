package external.services;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.F.Promise;
import play.libs.F.Tuple;
import play.libs.OAuth.RequestToken;

/**
 * Created by patrik on 14.06.2014.
 */
public interface OAuthService {
    public Tuple<String, RequestToken> retrieveRequestToken(String callbackUrl);

    public Promise<JsonNode> registeredUserProfile(RequestToken token, String authVerifier);
}
