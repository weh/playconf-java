package models;

import play.data.validation.Constraints.*;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created by weh on 6/4/14.
 */
@Entity
public class Speaker extends Model {
    @Id
    public Long id;

    @Required
    public String name;

    @Required
    @Email
    public String email;

    @Required
    @MinLength(value = 10)
    @MaxLength(value = 1000)
    @Column(length = 1000)
    public String bio;

    @Required
    public String twitterId;

    @Required
    public String pictureUrl;
}
