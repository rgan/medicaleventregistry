package org.healthapps.medicaleventregistry.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONArray;
import org.healthapps.medicaleventregistry.client.panels.LoginPanel;
import org.healthapps.medicaleventregistry.client.panels.EventPanel;
import org.healthapps.medicaleventregistry.client.panels.SearchPanel;

public class MedicalEventRegistryUI implements EntryPoint {
    private MapWidget map;
    private JSONArray eventTypesArray;
    private EventPanel eventPanel;
    private SearchPanel searchPanel;

    // GWT module entry point method.
    public void onModuleLoad() {
// Create the constants
        MedicalEventRegistryUIConstants constants = (MedicalEventRegistryUIConstants) GWT.create(MedicalEventRegistryUIConstants.class);

//        LatLng cawkerCity = LatLng.newInstance(39.509, -98.434);
//        // Open a map centered on Cawker City, KS USA
//
//        map = new MapWidget(cawkerCity, 2);
//        map.setSize("500px", "300px");
//
//        // Add some controls for the zoom level
//        map.addControl(new LargeMapControl());
//
//        // Add a marker
//        map.addOverlay(new Marker(cawkerCity));

        // Add the map to the HTML host page

        HorizontalPanel mainPanel = new HorizontalPanel();
        TabPanel tabPanel = new TabPanel();
        tabPanel.add(new LoginPanel(constants), constants.getLoginPanelTitle());
        eventPanel = new EventPanel(constants);
        tabPanel.add(eventPanel, constants.getEventPanelTitle());
        searchPanel = new SearchPanel(constants);
        tabPanel.add(searchPanel, constants.getSearchPanelTitle());
        tabPanel.selectTab(0);

        mainPanel.add(tabPanel);
        mainPanel.add(new HorizontalPanel()); // TODO placeholder for map
        RootPanel.get("main").add(mainPanel);
        getEventTypes();
    }

    public void getEventTypes() {
        String url = "http://localhost:8080/rest/types/";
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, URL.encode(url));
        requestBuilder.setCallback(new BaseRequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                String responseText = response.getText();
                GWT.log(responseText, null);
                JSONValue value = JSONParser.parse(responseText);
                eventTypesArray = value.isArray();
                eventPanel.setEventTypes(eventTypesArray);
                searchPanel.setEventTypes(eventTypesArray);
            }
        });
        try {
            requestBuilder.send();
        } catch (RequestException e) {
            GWT.log(e.toString(), null);
        }
    }
    
}