package models;

import javax.persistence.Entity;
import javax.persistence.Table;
import play.db.jpa.Model;

/**
 * @author wanghongen
 * @date 9/5/18 10:56 AM
 */
@Entity
@Table(name = "tb_tsk")
public class Task extends Model   {

  public String title;
  public boolean done;

  public Task(String title) {
    this.title = title;
  }
}
