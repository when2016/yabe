import models.User;
import play.jobs.Job;
import play.test.Fixtures;

/**
 * @author wanghongen
 * @date 9/2/18 8:34 AM
 */
public class Bootstarp extends Job {


  public void  doJob() {
    if(User.count()==0) {
      Fixtures.loadModels("data.yml");
    }
  }

}
