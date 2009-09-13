package org.healthapps.medicaleventregistry.client;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.ui.ListBox;

public class Utils {

    public static void setEventTypes(JSONArray eventTypesArray, ListBox eventTypeDropBox) {
        eventTypeDropBox.clear();
        for (int i = 0; i < eventTypesArray.size(); i++) {
            JSONObject jsonValue = eventTypesArray.get(i).isObject();
            eventTypeDropBox.addItem(jsonValue.get("label").isString().stringValue(),
                    jsonValue.get("data").toString());
        }
    }
}
