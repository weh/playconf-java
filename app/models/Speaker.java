package models;

import play.db.ebean.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by weh on 6/4/14.
 */
@Entity
public class Speaker extends Model {
    @Id
    public Long id;

    public String name;

    public String email;

    @Column(length = 1000)
    public String bio;

    public String twitterId;

    public String pictureUrl;
}
