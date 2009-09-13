package org.healthapps.medicaleventregistry.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import org.healthapps.medicaleventregistry.client.panels.EventPanel;
import org.healthapps.medicaleventregistry.client.panels.LoginPanel;
import org.healthapps.medicaleventregistry.client.panels.SearchPanel;

public class MedicalEventRegistryUI implements EntryPoint {
    private MapWidget map;
    private JSONArray eventTypesArray;
    private EventPanel eventPanel;
    private SearchPanel searchPanel;
    private static final String URL_GET_TYPES = "/rest/types/";

    // GWT module entry point method.
    public void onModuleLoad() {
// Create the constants
        MedicalEventRegistryUIConstants constants = (MedicalEventRegistryUIConstants) GWT.create(MedicalEventRegistryUIConstants.class);

        LatLng mapCenter = LatLng.newInstance(0, 0);

        map = new MapWidget(mapCenter, 1);
        map.setSize("500px", "300px");

        // Add some controls for the zoom level
        map.addControl(new LargeMapControl());

        // Add the map to the HTML host page

        HorizontalPanel mainPanel = new HorizontalPanel();
        TabPanel tabPanel = new TabPanel();
        eventPanel = new EventPanel(constants);
        searchPanel = new SearchPanel(constants);
        eventPanel.disable();
        searchPanel.disable();
        LoginPanel loginPanel = new LoginPanel(constants, eventPanel, searchPanel);
        tabPanel.add(loginPanel, constants.getLoginPanelTitle());
        tabPanel.add(eventPanel, constants.getEventPanelTitle());
        tabPanel.add(searchPanel, constants.getSearchPanelTitle());
        tabPanel.selectTab(0);

        mainPanel.add(tabPanel);
        mainPanel.add(map); // TODO placeholder for map
        map.addMapClickHandler(new MapClickHandler() {

            public void onClick(MapClickEvent mapClickEvent) {
                LatLng latLng = mapClickEvent.getLatLng();
                eventPanel.setLatLng(latLng.getLatitude(), latLng.getLongitude());
            }
        });
        RootPanel.get("main").add(mainPanel);
        getEventTypes();
    }

    public void getEventTypes() {
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.GET, URL.encode(URL_GET_TYPES));
        requestBuilder.setCallback(new BaseRequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                String responseText = response.getText();
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