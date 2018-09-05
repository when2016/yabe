package controllers;

import java.util.Date;
import play.jobs.Job;
import play.jobs.On;

/**
 * @author wanghongen
 * @date 9/2/18 7:52 PM
 */
//@OnApplicationStart
//@On("5/5 * * * * ?")
public class Bootstrap extends Job {

  public void doJob() {
    System.out.println(new Date());
    System.out.println("A root page has bean created.");
  }
}
