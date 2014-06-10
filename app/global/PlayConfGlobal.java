package global;

import play.GlobalSettings;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Results;
import play.mvc.SimpleResult;

/**
 * Created by patrik on 10.06.2014.
 */
public class PlayConfGlobal extends GlobalSettings {

    @Override
    public F.Promise<SimpleResult> onError(Http.RequestHeader request, Throwable t) {
        return F.Promise.<SimpleResult>pure(Results.notFound(views.html.error.render()));
    }

    @Override
    public F.Promise<SimpleResult> onHandlerNotFound(Http.RequestHeader request) {
        return F.Promise.<SimpleResult>pure(Results.internalServerError(views.html.error.render()));
    }
}
