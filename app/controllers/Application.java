package controllers;

import play.*;
import play.cache.Cache;
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


  @Before
  static void addDefaults() {
    renderArgs.put("blogTitle", Play.configuration.getProperty("blog.title"));
    renderArgs.put("blogBaseline", Play.configuration.getProperty("blog.baseline"));
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