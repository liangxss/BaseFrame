package com.owen.base.utils;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by LiJiaZhi on 17/1/20.
 * <p>
 * Json通用类
 */
public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();


    public static Gson gson = null;

    public static Gson getGsonInstance() {
        if (gson == null) {
            synchronized (Gson.class) {
                if(gson == null){
                    gson = new GsonBuilder().create();
                }
            }
        }
        return gson;
    }

    private static JsonParser jsonParser = null;

    public static JsonParser getJsonParserInstance() {
        if (jsonParser == null) {
            synchronized (JsonParser.class) {
                if(jsonParser == null){
                    jsonParser = new JsonParser();
                }
            }

        }
        return jsonParser;
    }

    //时间格式化
    public static class LPDateDeserializer implements JsonDeserializer<Date>, JsonSerializer<Date> {

        @Override
        public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Date(json.getAsLong() * 1000);
        }

        @Override
        public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getTime() / 1000);
        }
    }

    public static <T> T parseString(String result) {
        return parseString(result, new TypeToken<T>() {}.getType());
    }

    public static <T> List<T> parseStringToList(String result, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = getJsonParserInstance().parse(result).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(getGsonInstance().fromJson(elem, cls));
        }
        return list;
    }

    public static <T> T parseString(String result, Type classOfT) {
        if (!TextUtils.isEmpty(result) && classOfT != null) {
            try {
                return getGsonInstance().fromJson(result, classOfT);
            } catch (JsonSyntaxException var3) {
                Log.e(TAG, "catch exception when format json str:" + result);
                throw var3;
            }
        } else {
            return null;
        }
    }

    public static String toString(Object obj) {
        return obj == null ? "" : getGsonInstance().toJson(obj);
    }

    public static <T> T parseJsonObject(JsonElement result, Class<T> classOfT) {
        if (null == result)
            return null;
        return getGsonInstance().fromJson(result, classOfT);
    }

    public static <T> T parseString(String result, Class<T> clazz) {
        if (TextUtils.isEmpty(result))
            return null;
        return getGsonInstance().fromJson(result, clazz);
    }

    // add ljz
    public static JsonObject toJsonObject(Object obj) {
        if (null == obj)
            return null;
        return getJsonParserInstance().parse(toString(obj)).getAsJsonObject();
    }






    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> gsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString,
                    new TypeToken<List<Map<String, T>>>() {
                    }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> gsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

    // -------

    /**
     * 按章节点得到相应的内容
     * @param jsonString json字符串
     * @param note 节点
     * @return 节点对应的内容
     */
    public static String getNoteJsonString(String jsonString,String note){
        if(TextUtils.isEmpty(jsonString)){
            throw new RuntimeException("json字符串");
        }
        if(TextUtils.isEmpty(note)){
            throw new RuntimeException("note标签不能为空");
        }
        JsonElement element = new JsonParser().parse(jsonString);
        if(element.isJsonNull()){
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        return element.getAsJsonObject().get(note).toString();
    }

    /**
     * 按照节点得到节点内容，然后传化为相对应的bean数组
     * @param jsonString 原json字符串
     * @param note 节点标签
     * @param beanClazz 要转化成的bean class
     * @return 返回bean的数组
     */
    public static <T> List<T> parserJsonToArrayBeans(String jsonString,String note,Class<T> beanClazz){
        String noteJsonString = getNoteJsonString(jsonString,note);
        return parserJsonToArrayBeans(noteJsonString,beanClazz);
    }
    /**
     * 按照节点得到节点内容，转化为一个数组
     * @param jsonString json字符串
     * @param beanClazz 集合里存入的数据对象
     * @return 含有目标对象的集合
     */
    public static <T> List<T> parserJsonToArrayBeans(String jsonString,Class<T> beanClazz){
        if(TextUtils.isEmpty(jsonString)){
            throw new RuntimeException("json字符串为空");
        }
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        if(jsonElement.isJsonNull()){
            throw new RuntimeException("得到的jsonElement对象为空");
        }
        if(!jsonElement.isJsonArray()){
            throw new RuntimeException("json字符不是一个数组对象集合");
        }
        JsonArray jsonArray = jsonElement.getAsJsonArray();
        List<T> beans = new ArrayList<T>();
        for (JsonElement jsonElement2: jsonArray) {
            T bean = new Gson().fromJson(jsonElement2, beanClazz);
            beans.add(bean);
        }
        return beans;
    }

    /**
     * 把相对应节点的内容封装为对象
     * @param jsonString json字符串
     * @param clazzBean  要封装成的目标对象
     * @return 目标对象
     */
    public static <T> T parserJsonToArrayBean(String jsonString,Class<T> clazzBean){
        if(TextUtils.isEmpty(jsonString)){
            throw new RuntimeException("json字符串为空");
        }
        JsonElement jsonElement = new JsonParser().parse(jsonString);
        if(jsonElement.isJsonNull()){
            throw new RuntimeException("json字符串为空");
        }
        if(!jsonElement.isJsonObject()){
            throw new RuntimeException("json不是一个对象");
        }
        return new Gson().fromJson(jsonElement, clazzBean);
    }
    /**
     * 按照节点得到节点内容，转化为一个数组
     * @param jsonString json字符串
     * @param note json标签
     * @param clazzBean 集合里存入的数据对象
     * @return 含有目标对象的集合
     */
    public static <T> T parserJsonToArrayBean(String jsonString,String note,Class<T> clazzBean){
        String noteJsonString = getNoteJsonString(jsonString, note);
        return parserJsonToArrayBean(noteJsonString, clazzBean);
    }
}

