package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import play.db.jpa.Model;

/**
 * @author wanghongen
 * @date 8/31/18 10:05 AM
 */
@Entity
@Table(name = "blog_user")
public class User extends Model {

  public String email;
  public String password;
  public String fullname;
  public boolean isAdmin;

  public User(String email, String password, String fullname) {
    this.email = email;
    this.password = password;
    this.fullname = fullname;
  }

  public static User connect(String email, String password) {
    return find("byEmailAndPassword", email, password).first();
  }
}