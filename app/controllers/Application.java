package controllers;

import play.*;
import play.cache.Cache;
import play.data.validation.Required;
import play.libs.Images;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

  public static void index() {
    System.out.println("Yop");

    Post frontPost = Post.find("order by postedAt desc").first();
    List<Post> olderPosts = Post.find("order by postedAt desc").from(1).fetch();

    render(frontPost, olderPosts);
  }


  public static void sayHello(@Required String myName) {
    if(validation.hasErrors()) {
      flash.error("please enter your name");
    }
    render(myName);
  }

  //创建新的任务
  public static void createTask(String title) {
    Task task = new Task(title).save();
    renderJSON(task);
  }

  public static void show(Long id) {
    Post post = Post.findById(id);
    render(post);

  }

  public static void captcha(String id) {
    Images.Captcha captcha = Images.captcha();
    String code = captcha.getText("#E4EAFD");
    Cache.set(id,code,"10min");
    renderBinary(captcha);
  }

}