package models;

import javax.validation.*;

import play.Logger;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.*;

import play.libs.Akka;
import play.libs.F.*;
import scala.concurrent.ExecutionContext;

/**
 * Created by weh on 6/4/14.
 */
@Entity
public class Proposal extends Model{
    @Id
    public Long id;

    @Required
    public String title;

    @Required
    @MinLength(value = 10)
    @MaxLength(value = 1000)
    @Column(length = 1000)
    public String proposal;

    @Required
    public SessionType type = SessionType.OneHourTalk;

    @Required
    public Boolean isApproved = false;

    public String keywords;

    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    public Speaker speaker;

    private static Finder<Long,Proposal> find = new Finder<Long, Proposal>(Long.class, Proposal.class);

    private static ExecutionContext ctx = Akka.system().dispatchers().lookup("akka.db-dispatcher");

    public static Promise<Proposal> findKeynote() {
        return Promise.promise(new Function0<Proposal>() {
            @Override
            public Proposal apply() throws Throwable {
                return find.where().eq("type", SessionType.Keynote).findUnique();
            }
        }, ctx).recover(new Function<Throwable, Proposal>() {

            @Override
            public Proposal apply(Throwable t) throws Throwable {
                Logger.error("failed to fetch keynot information", t);
                Proposal p = new Proposal();
                p.title = "COMING SOON!";
                p.proposal = "";
                Speaker speaker = new Speaker();
                speaker.name = "";
                speaker.pictureUrl = "";
                speaker.twitterId = "";
                p.speaker = speaker;
                return p;
            }
        }, ctx);
    }


    public Promise<Void> asyncSave() {
        return Promise.promise(new Function0<Void>() {
            @Override
            public Void apply() throws Throwable {
                save();
                return null;
            }
        }, ctx);
    }

    public static Promise<Proposal> selectRandomTalk() {
        return Promise.promise(new Function0<Proposal>() {

            @Override
            public Proposal apply() throws Throwable {
                Long randomId = (long) (1 + Math.random() * (5 - 1));
                return Proposal.find.byId(randomId);
            }
        });
    }
}
