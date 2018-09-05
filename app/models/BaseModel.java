//package models;
//
//import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
//import java.io.Serializable;
//import java.lang.reflect.Field;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//import libs.JsonHelper;
//import libs.MySQLHelper;
//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.NumberUtils;
//import play.Logger;
//
//public abstract class BaseModel<T> implements Serializable {
//    public int save() {
//        StringBuilder sql = new StringBuilder("insert into `");
//        sql.append(getClass().getSimpleName()).append("` (");
//        Map<String, Object> map = Objects.toMap(this);
//        List<String> names = new ArrayList<String>();
//        List<Object> values = new ArrayList<Object>();
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            String key = entry.getKey();
////            if ("id".equals(key)) continue;
//            names.add("`" + key + "`");
//            values.add(entry.getValue());
//        }
//        sql.append(StringUtils.join(names, ",")).append(") values (");
//        sql.append(StringUtils.repeat("?", ",", names.size())).append(")");
//        try {
//            return MySQLHelper.executeUpdate(sql.toString(), values.toArray(new Object[values.size()]));
//        } catch (Exception e) {
//            if (!(e instanceof MySQLIntegrityConstraintViolationException)) {// 非约束键才进行打印日志
//                Logger.error(e, e.getMessage());
//            }
//            Logger.error(sql.toString());
//            Logger.error(JsonHelper.toString(values));
//            return -1;
//        }
//    }
//
//    public int update(String... keys) {
//        Map<String, Object> map = Objects.toMap(this);
//        List<String> names = new ArrayList<String>();
//        List<Object> values = new ArrayList<Object>();
//        if (keys == null || keys.length == 0) {
//            List<Field> fields0 = Objects.getFields0(getClass());
//            List<String> keyList = new ArrayList<>();
//            for (Field field : fields0) {
//                keyList.add(field.getName());
//            }
//            keyList.remove("id");
//            keys = keyList.toArray(new String[keyList.size()]);
//        }
//        for (String key : keys) {
//            names.add("`" + key + "`" + "=?");
//            if ("id".equals(key)) continue;
//            values.add(map.get(key));
//        }
//        values.add(map.get("id"));
//        String sql = "update `" + getClass().getSimpleName() + "` set " + StringUtils.join(names, ",") + " where id=?";
//        try {
//            return MySQLHelper.executeUpdate(sql, values.toArray(new Object[values.size()]));
//        } catch (SQLException e) {
//            Logger.error(e, e.getMessage());
//            Logger.error(sql);
//            Logger.error(JsonHelper.toString(values));
//            return -1;
//        }
//    }
//
//    public int delete() {
//        String sql = "delete from `" + getClass().getSimpleName() + "` where id=?";
//        try {
//            return MySQLHelper.executeUpdate(sql, getId());
//        } catch (SQLException e) {
//            Logger.error(e, e.getMessage());
//            Logger.error(sql);
//            Logger.error("id:" + getId());
//            return -1;
//        }
//    }
//
//    public static int delete(Class clazz, Map<String, Object> params) {
//        String sql = "";
//        List<Object> args = new ArrayList<Object>();
//        try {
//            List<String> conditions = new ArrayList<String>();
//            populateParams(params, conditions, args);
//            sql = "delete from `" + clazz.getSimpleName() + "` where " + StringUtils.join(conditions, " and ");
//            return MySQLHelper.executeUpdate(sql, args.toArray(new Object[args.size()]));
//        } catch (SQLException e) {
//            Logger.error(e, e.getMessage());
//            Logger.error(sql);
//            Logger.error(JsonHelper.toString(args));
//            return -1;
//        }
//    }
//
//    public static <T> T findById(Class<T> clazz, String id) {
//        Map map = MySQLHelper.findFirst("select * from `" + clazz.getSimpleName() + "` where id=?", id);
//        return restore(map, clazz);
//    }
//
//    public static <T> T find(Class<T> clazz, String key, String value) {
//        Map map = MySQLHelper.findFirst("select * from `" + clazz.getSimpleName() + "` where " + key + "=?", value);
//        return restore(map, clazz);
//    }
//
//    public static <T> T find(Class<T> clazz, Map<String, String> params, boolean and) {
//        if (params != null && !params.isEmpty()) {
//            Set<String> keySet = params.keySet();
//            int size = keySet.size();
//            String[] keys = new String[size];
//            String[] values = new String[size];
//            int i = 0;
//            for (String key : params.keySet()) {
//                keys[i] = key;
//                values[i] = params.get(key);
//                i++;
//            }
//            String separator = and? "and" : "or";
//            String where = StringUtils.join(keys, " =? "+separator + " ") + " = ?";
//            Map map = MySQLHelper.findFirst("select * from `" + clazz.getSimpleName() + "` where " + where, values);
//            return restore(map, clazz);
//        } else {
//            Map map = MySQLHelper.findFirst("select * from `" + clazz.getSimpleName() + "`");
//            return restore(map, clazz);
//        }
//    }
//
//    public static <T> List<T> list(Class<T> clazz, String key, Object value, String orderBy) {
//        List<Map> data = MySQLHelper.find("select * from `" + clazz.getSimpleName() + "` where " + key + "=? " + (StringUtils.isNotBlank(orderBy) ? (" order by " + orderBy) : ""), value);
//        List<T> result = new ArrayList<T>();
//        if (data != null) {
//            for (Map map : data) {
//                result.add(restore(map, clazz));
//            }
//        }
//        return result;
//    }
//
//    public static <T> List<T> list(Class<T> clazz, Map<String, Object> params, String orderBy) {
//        return list(clazz, params, orderBy, -1, -1);
//    }
//
//    public static <T> List<T> list(Class<T> clazz, Map<String, Object> params, String orderBy, int psize) {
//        return list(clazz, params, orderBy, psize, 0);
//    }
//
//    public static <T> List<T> list(Class<T> clazz, Map<String, Object> params, String orderBy, int psize, int page) {
//        List<String> conditions = new ArrayList<String>();
//        List<Object> args = new ArrayList<Object>();
//        populateParams(params, conditions, args);
//        String sql = "select * from `" + clazz.getSimpleName() + "`" + ((conditions.isEmpty()) ? "" : " where " + StringUtils.join(conditions, " and "))
//                + (StringUtils.isNotBlank(orderBy) ? (" order by " + orderBy) : "") + (psize > 0 ? (" limit " + Math.max(0, page * psize) + "," + psize) : "");
//        List<Map> data = MySQLHelper.find(sql, args.toArray(new Object[args.size()]));
//        List<T> result = new ArrayList<T>();
//        if (data != null) {
//            for (Map map : data) {
//                result.add(restore(map, clazz));
//            }
//        }
//        return result;
//    }
//
//    public static long count(Class clazz, Map<String, Object> params) {
//        List<String> conditions = new ArrayList<String>();
//        List<Object> args = new ArrayList<Object>();
//        populateParams(params, conditions, args);
//        String sql = "select count(*) as c from `" + clazz.getSimpleName() + "`" + ((conditions.isEmpty()) ? "" : " where " + StringUtils.join(conditions, " and "));
//        List<Map> data = MySQLHelper.find(sql, args.toArray(new Object[args.size()]));
//        if (data != null && data.size() > 0) {
//            return NumberUtils.toLong(data.get(0).get("c").toString());
//        }
//        return 0;
//    }
//
//    public static <T> T restore(Map map, Class<T> clazz) {
//        if (map != null) {
//            try {
//                T result = clazz.newInstance();
//                Objects.copy(result, map);
//                return result;
//            } catch (Exception e) {
//                Logger.error(e, e.getMessage());
//            }
//        }
//        return null;
//    }
//
//    public Map toDtoMap() {
//        Map map = Objects.toDtoMap(this);
//        for (Object o : map.keySet()) {
//            if (map.get(o) instanceof Date) {
//                map.put(o, ((Date)map.get(o)).getTime());
//            }
//        }
//        return map;
//    }
//
//    protected abstract Object getId();
//
//
//    private static void populateParams(Map<String, Object> params, List<String> conditions, List<Object> args) {
//        if (params != null) {
//            for (Map.Entry<String, Object> entry : params.entrySet()) {
//                String key = entry.getKey().trim();
//                if (!key.endsWith("=") && !key.endsWith(">") && !key.endsWith("<") && !key.endsWith(" like") && !key.endsWith(" in")) {
//                    key = key + "=";
//                }
//                Object value = entry.getValue();
//                if (value instanceof String[]) {
//                    value = Arrays.asList((String[])value);
//                }
//                if (!key.endsWith(" in")) {
//                    conditions.add(key + " ?");
//                    if (value != null && value.getClass().isEnum()) value = value.toString();
//                    args.add(value);
//                } else if (value instanceof Collection) {
//                    int len = ((Collection)value).size();
//                    if (len > 0) {
//                        conditions.add(key + " (" + StringUtils.substringBeforeLast(StringUtils.repeat("?,", len), ",") + ")");
//                        for (Object obj : ((Collection) value)) {
//                            args.add(obj);
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
