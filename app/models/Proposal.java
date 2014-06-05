package models;

import play.db.ebean.Model;

import javax.persistence.*;


/**
 * Created by weh on 6/4/14.
 */
@Entity
public class Proposal extends Model{
    @Id
    public Long id;

    public String title;

    @Column(length = 1000)
    public String proposal;

    public SessionType type = SessionType.OneHourTalk;

    public Boolean isApproved = false;

    public String keywords;

    @OneToOne(cascade = CascadeType.ALL)
    public Speaker speaker;
}
