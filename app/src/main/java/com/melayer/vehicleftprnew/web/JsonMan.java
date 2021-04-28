package com.melayer.vehicleftprnew.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by root on 9/3/15.
 */
public class JsonMan {
    public static <T> T toObject(String json, Class<T> cls) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        T t = mapper.readValue(json, cls);
        mapper = null;
        return t;
    }

    public static <T> String fromObject(T obj) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String data = mapper.writeValueAsString(obj);
        mapper = null;
        return data;
    }

    public static <T> T parseAnything(String json, TypeReference<T> type) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        T t = mapper.readValue(json, type);
        mapper = null;
        return t;
    }
}
