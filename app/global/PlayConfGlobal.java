package global;

import actors.EventPublisher;
import actors.messages.RandomlySelectedTalkEvent;
import akka.actor.ActorRef;
import models.Proposal;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Results;
import play.mvc.SimpleResult;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

/**
 * Created by patrik on 10.06.2014.
 */
public class PlayConfGlobal extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        super.onStart(app);
        Akka.system().scheduler().schedule(
                Duration.create(1, TimeUnit.SECONDS),
                Duration.create(10, TimeUnit.SECONDS),
                selectRandomTalks(),
                Akka.system().dispatcher());
    }

    private Runnable selectRandomTalks() {
        return new Runnable() {
            @Override
            public void run() {
                F.Promise<Proposal> proposal = Proposal.selectRandomTalk();
                proposal.onRedeem(new F.Callback<Proposal>() {
                    public void invoke(Proposal p) {
                        EventPublisher.ref.tell(new RandomlySelectedTalkEvent(p), ActorRef.noSender());
                    }
                });
            }
        };
    }

    @Override
    public F.Promise<SimpleResult> onError(Http.RequestHeader request, Throwable t) {
        return F.Promise.<SimpleResult>pure(Results.notFound(views.html.error.render()));
    }

    @Override
    public F.Promise<SimpleResult> onHandlerNotFound(Http.RequestHeader request) {
        return F.Promise.<SimpleResult>pure(Results.internalServerError(views.html.error.render()));
    }
}
