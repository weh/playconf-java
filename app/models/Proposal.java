package models;

import javax.validation.*;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.*;

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

    public static Proposal findKeynote() {
        return find.where().eq("type", SessionType.Keynote).findUnique();
    }
}
