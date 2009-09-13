package org.healthapps.medicaleventregistry.client.panels;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.*;
import com.google.gwt.core.client.GWT;
import org.healthapps.medicaleventregistry.client.Utils;
import org.healthapps.medicaleventregistry.client.BaseRequestCallback;

public class EventPanel extends DecoratorPanel {
    private TextBox typeNameTextBox;
    private Button addTypeSubmitBtn;
    private TextBox nameTextBox;
    private Button addEventSubmitBtn;
    private DateBox dateTextBox;
    private TextBox latTextBox;
    private TextBox lonTextBox;
    private ListBox eventTypeDropBox;
    private static final String URL_ADD_EVENT_TYPE = "/rest/types/";
    private static final String URL_ADD_EVENT = "/rest/events/";
    private String username;
    private String password;

    public void setEventTypes(JSONArray eventTypesArray) {
        Utils.setEventTypes(eventTypesArray, eventTypeDropBox);
    }

    public void setUserPwd(String user, String pwd) {
        this.username = user;
        this.password = pwd;
    }

    public void setLatLng(double latitude, double longitude) {
        latTextBox.setText(Double.toString(latitude));
        lonTextBox.setText(Double.toString(longitude));
    }

    public static interface UIConstants extends Constants {
        @DefaultStringValue("Add Event (All fields are required. <br> Click on map to popluate lat/lng)")
        String eventPanelAddEventFormName();

        @DefaultStringValue("Add Event Type")
        String eventPanelAddEventTypeFormName();

        @DefaultStringValue("Add Type")
        String eventPanelAddTypeSubmitButtonText();

        @DefaultStringValue("Name:")
        String eventPanelEventTypeName();

        @DefaultStringValue("Who:")
        String eventPanelPersonName();

        @DefaultStringValue("Add Event")
        String eventPanelAddEventSubmitButtonText();

        @DefaultStringValue("Date:")
        String eventPanelDate();

        @DefaultStringValue("Lat:")
        String eventPanelLat();

        @DefaultStringValue("Lon:")
        String eventPanelLon();

        @DefaultStringValue("Event type:")
        String eventPanelTypesListBox();
    }

    public EventPanel(UIConstants constants) {
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        addEventForm(constants, layout);
        addEventTypeForm(constants, layout);
        setWidget(layout);
    }

    public void enable() {
        addTypeSubmitBtn.setEnabled(true);
        addEventSubmitBtn.setEnabled(true);
    }

    public void disable() {
        addTypeSubmitBtn.setEnabled(false);
        addEventSubmitBtn.setEnabled(false);
    }

    private void addEventTypeForm(UIConstants constants, FlexTable layout) {
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(7, 0, constants.eventPanelAddEventTypeFormName());
        cellFormatter.setColSpan(7, 0, 2);
        cellFormatter.setHorizontalAlignment(7, 0,
                HasHorizontalAlignment.ALIGN_CENTER);

        layout.setHTML(8, 0, constants.eventPanelEventTypeName());
        typeNameTextBox = new TextBox();
        layout.setWidget(8, 1, typeNameTextBox);
        addTypeSubmitBtn = new Button();
        addTypeSubmitBtn.setText(constants.eventPanelAddTypeSubmitButtonText());
        layout.setWidget(9, 0, addTypeSubmitBtn);
        cellFormatter.setColSpan(9, 0, 2);
        cellFormatter.setHorizontalAlignment(9, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
        addTypeSubmitBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doAddEventType();
            }
        });
    }

    private void doAddEventType() {
        String typeName = typeNameTextBox.getText();
        if (typeName == "") {
            return;
        }
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, URL.encode(URL_ADD_EVENT_TYPE + typeName));
        requestBuilder.setRequestData("dummy"); // error otherwise
        requestBuilder.setCallback(new BaseRequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                typeNameTextBox.setText("");
            }
        });
        try {
            requestBuilder.send();
        } catch (RequestException e) {
            GWT.log(e.toString(), null);
        }
    }

    private void addEventForm(UIConstants constants, FlexTable layout) {
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(0, 0, constants.eventPanelAddEventFormName());
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(0, 0,
                HasHorizontalAlignment.ALIGN_CENTER);

        layout.setHTML(1, 0, constants.eventPanelPersonName());
        nameTextBox = new TextBox();
        layout.setWidget(1, 1, nameTextBox);

        layout.setHTML(2, 0, constants.eventPanelDate());
        dateTextBox = new DateBox();
        dateTextBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
        layout.setWidget(2, 1, dateTextBox);

        layout.setHTML(3, 0, constants.eventPanelLat());
        latTextBox = new TextBox();
        layout.setWidget(3, 1, latTextBox);

        layout.setHTML(4, 0, constants.eventPanelLon());
        lonTextBox = new TextBox();
        layout.setWidget(4, 1, lonTextBox);

        layout.setHTML(5, 0, constants.eventPanelTypesListBox());
        eventTypeDropBox = new ListBox(false);
        layout.setWidget(5, 1, eventTypeDropBox);

        addEventSubmitBtn = new Button();
        addEventSubmitBtn.setText(constants.eventPanelAddEventSubmitButtonText());
        layout.setWidget(6, 0, addEventSubmitBtn);
        cellFormatter.setColSpan(6, 0, 2);
        cellFormatter.setHorizontalAlignment(6, 0,
                HasHorizontalAlignment.ALIGN_CENTER);

        addEventSubmitBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doAddEvent();
            }
        });
    }

    private void doAddEvent() {
        if (!validateEventData()) {
            return;
        }
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, URL.encode(URL_ADD_EVENT));
        requestBuilder.setRequestData(getPostData());
        requestBuilder.setUser(username);
        requestBuilder.setPassword(password);
        requestBuilder.setCallback(new BaseRequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                nameTextBox.setText("");
                latTextBox.setText("");
                lonTextBox.setText("");
                dateTextBox.getTextBox().setText("");
                eventTypeDropBox.setSelectedIndex(-1);
            }
        });
        try {
            requestBuilder.send();
        } catch (RequestException e) {
            GWT.log(e.toString(), null);
        } 
    }

    private boolean validateEventData() {
        if (nameTextBox.getText() == "" ||
                latTextBox.getText() == ""
                || lonTextBox.getText() == ""
                || dateTextBox.getTextBox().getText() == ""
                || eventTypeDropBox.getSelectedIndex() == -1) {
            return false;
        }
        return true;
    }

    private String getPostData() {
        return "name=" + nameTextBox.getText()
                + "&lat=" + latTextBox.getText()
                + "&lon=" +lonTextBox.getText()
                + "&dateReported=" + dateTextBox.getTextBox().getText()
                + "&eventTypeId=" + eventTypeDropBox.getValue(eventTypeDropBox.getSelectedIndex());
    }
}
