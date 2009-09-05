package org.healthapps.medicaleventregistry.utils;

import static net.sf.json.JSONObject.fromObject;

import java.util.Collection;

public class JsonUtils {

    private JsonUtils() {
    }


    public static String toJson(Object obj) {
        if (obj == null) {
            return "{}";
        }
        return fromObject(obj).toString();
    }

    public static String toJsonArray(Collection obj) {
        if (obj == null) {
            return "[]";
        }
        return net.sf.json.JSONArray.fromObject(obj).toString();
    }
}
