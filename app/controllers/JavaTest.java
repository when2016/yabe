//package controllers;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Enumeration;
//import java.util.List;
//import libs.Objects;
//import models.BaseModel;
//import org.apache.commons.lang3.StringUtils;
//
//public class JavaTest {
//    public static void main(String[] args) throws Exception {
//        Class[] classes = getClasses("models");
//        for (Class aClass : classes) {
//            if (BaseModel.class.isAssignableFrom(aClass)) {
//                if (aClass.isAssignableFrom(BaseModel.class)) continue;
//                List<String> columns = new ArrayList<>();
//                List<Field> fields = Objects.getFields0(aClass);
//                for (Field field : fields) {
//                    Class<?> type = field.getType();
//                    String name = field.getName();
//                    if (type.isAssignableFrom(int.class)) {
//                        columns.add("`" + name + "` int(11) default 0");
//                    } else if (type.isAssignableFrom(Date.class)) {
//                        columns.add("`" + name + "` DATETIME");
//                    } else {
//                        columns.add("`" + name + "` varchar(255) default ''");
//                    }
//                }
//                System.out.println("create table `" + aClass.getSimpleName() + "` (" + StringUtils.join(columns, ",") + ", PRIMARY KEY (`id`))COLLATE='utf8mb4_general_ci' ENGINE=InnoDB");
//            }
//        }
//    }
//
//    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
//        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//        assert classLoader != null;
//        String path = packageName.replace('.', '/');
//        Enumeration<URL> resources = classLoader.getResources(path);
//        List<File> dirs = new ArrayList<File>();
//        while (resources.hasMoreElements()) {
//            URL resource = resources.nextElement();
//            dirs.add(new File(resource.getFile()));
//        }
//        ArrayList<Class> classes = new ArrayList<Class>();
//        for (File directory : dirs) {
//            classes.addAll(findClasses(directory, packageName));
//        }
//        return classes.toArray(new Class[classes.size()]);
//    }
//
//    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
//        List<Class> classes = new ArrayList<Class>();
//        if (!directory.exists()) {
//            return classes;
//        }
//        File[] files = directory.listFiles();
//        for (File file : files) {
//            if (file.isDirectory()) {
//                assert !file.getName().contains(".");
//                classes.addAll(findClasses(file, packageName + "." + file.getName()));
//            } else if (file.getName().endsWith(".class")) {
//                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
//            }
//        }
//        return classes;
//    }
//}
