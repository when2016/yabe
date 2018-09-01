package models;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import play.db.jpa.Model;

/**
 * @author wanghongen
 * @date 8/31/18 5:19 PM
 */

@Entity
@Table(name = "blog_comment")
public class Comment extends Model {

  public String author;
  public Date postedAt;

  @Lob
  public String content;

  @ManyToOne
  public Post post;

  public Comment(Post post, String author, String content) {
    this.post = post;
    this.author = author;
    this.content = content;
    this.postedAt = new Date();
  }

}
