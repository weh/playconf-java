package models;

import com.fasterxml.jackson.databind.JsonNode;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

import play.data.validation.Constraints.*;

import java.sql.Date;

/**
 * Created by patrik on 14.06.2014.
 */
@Entity
public class RegisteredUser extends Model {

    @Id
    public Long id;

    @Required
    public String name;

    @MaxLength(value = 200)
    public String description;

    @Required
    public String pictureUrl;

    @Required
    public String twitterId;

    @Required
    public Date registrationDate = new Date (System.currentTimeMillis());

    public static RegisteredUser fromJson(JsonNode jsonNode) {
        RegisteredUser u = new RegisteredUser();
        u.name = jsonNode.findPath("name").asText();
        u.twitterId = jsonNode.findPath("screen_name").asText();
        u.description = jsonNode.findPath("description").asText();
        u.pictureUrl = jsonNode.findPath("profile_image_url").asText();
        return u;
    }
}
