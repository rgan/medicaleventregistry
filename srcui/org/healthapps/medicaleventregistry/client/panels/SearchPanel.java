package org.healthapps.medicaleventregistry.client.panels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.i18n.client.Constants;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import org.healthapps.medicaleventregistry.client.BaseRequestCallback;
import org.healthapps.medicaleventregistry.client.Utils;

public class SearchPanel extends DecoratorPanel {
    private DateBox dateFromDateBox;
    private DateBox dateToDateBox;
    private ListBox eventTypeDropBox;
    private Button searchSubmitBtn;
    private String username;
    private String password;
    private static final String URL_SEARCH = "/rest/events/";
    private static final int MAX_ROWS = 10;
    private Grid reportGrid;

    public void setEventTypes(JSONArray eventTypesArray) {
        Utils.setEventTypes(eventTypesArray, eventTypeDropBox);
    }

    public void enable() {
        searchSubmitBtn.setEnabled(true);
    }

    public void disable() {
        searchSubmitBtn.setEnabled(false);
    }

    public void setUserPwd(String user, String pwd) {
        username = user;
        password = pwd;
    }


    public static interface UIConstants extends Constants {
        @DefaultStringValue("Search for events")
        String searchPanelSearchFormName();

        @DefaultStringValue("Search")
        String searchPanelSearchSubmitButtonText();

        @DefaultStringValue("Date From:")
        String searchPanelDateFrom();

        @DefaultStringValue("Date To:")
        String searchPanelDateTo();

        @DefaultStringValue("Event type:")
        String searchPanelTypesListBox();

        @DefaultStringValue("Search Results")
        String searchPanelReportTitle();
    }

    public SearchPanel(UIConstants constants) {
        FlexTable layout = new FlexTable();
        layout.setCellSpacing(6);
        addSearchForm(constants, layout);
        setWidget(layout);
    }

    private void addSearchForm(UIConstants constants, FlexTable layout) {
        FlexTable.FlexCellFormatter cellFormatter = layout.getFlexCellFormatter();

        layout.setHTML(0, 0, constants.searchPanelSearchFormName());
        cellFormatter.setColSpan(0, 0, 2);
        cellFormatter.setHorizontalAlignment(0, 0,
                HasHorizontalAlignment.ALIGN_CENTER);

        layout.setHTML(1, 0, constants.searchPanelDateFrom());
        dateFromDateBox = new DateBox();
        dateFromDateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
        layout.setWidget(1, 1, dateFromDateBox);

        layout.setHTML(2, 0, constants.searchPanelDateTo());
        dateToDateBox = new DateBox();
        dateToDateBox.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
        layout.setWidget(2, 1, dateToDateBox);

        layout.setHTML(3, 0, constants.searchPanelTypesListBox());
        eventTypeDropBox = new ListBox(false);
        layout.setWidget(3, 1, eventTypeDropBox);

        searchSubmitBtn = new Button();
        searchSubmitBtn.setText(constants.searchPanelSearchSubmitButtonText());
        layout.setWidget(4, 0, searchSubmitBtn);
        cellFormatter.setColSpan(4, 0, 2);
        cellFormatter.setHorizontalAlignment(4, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
        searchSubmitBtn.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                doSearch();
            }
        });

        layout.setHTML(5, 0, constants.searchPanelReportTitle());
        cellFormatter.setColSpan(5, 0, 2);
        cellFormatter.setHorizontalAlignment(5, 0,
                HasHorizontalAlignment.ALIGN_CENTER);

        reportGrid = new Grid(MAX_ROWS, 3);
        layout.setWidget(6, 0, reportGrid);
        cellFormatter.setColSpan(6, 0, 2);
        cellFormatter.setHorizontalAlignment(6, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
    }

    private void doSearch() {
        for (int row = 0; row < MAX_ROWS; ++row) {
            reportGrid.setText(row, 0, "");
            reportGrid.setText(row, 1, "");
            reportGrid.setText(row, 2, "");
        }
        if (!validate()) {
            return;
        }
        String typeId = eventTypeDropBox.getValue(eventTypeDropBox.getSelectedIndex());
        String url = URL_SEARCH + URL.encodeComponent(typeId) + "/" + URL.encodeComponent(dateFromDateBox.getTextBox().getText()) +
                "/" + URL.encodeComponent(dateToDateBox.getTextBox().getText());
        RequestBuilder requestBuilder = new RequestBuilder(RequestBuilder.POST, url);
        requestBuilder.setUser(username);
        requestBuilder.setPassword(password);
        requestBuilder.setRequestData("dummy"); // error otherwise
        requestBuilder.setCallback(new BaseRequestCallback() {

            public void onResponseReceived(Request request, Response response) {
                JSONValue value = JSONParser.parse(response.getText());
                JSONArray resultsArray = value.isArray();
                reportGrid.setText(0, 0, "Name");
                reportGrid.setText(0, 1, "Date");
                reportGrid.setText(0, 2, "Type");
                for (int row = 1; row < MAX_ROWS; ++row) {
                    JSONObject eventData = resultsArray.get(row).isObject();
                    JSONValue whoValue = eventData.get("who");
                    reportGrid.setText(row, 0, whoValue.isString().stringValue());
                    reportGrid.setText(row, 1, eventData.get("when").isString().stringValue());
                    reportGrid.setText(row, 2, eventData.get("eventType").isString().stringValue());
                }
            }
        });
        try {
            requestBuilder.send();
        } catch (RequestException e) {
            GWT.log(e.toString(), null);
        }
    }

    private boolean validate() {
        if (dateToDateBox.getTextBox().getText() == "" ||
                dateFromDateBox.getTextBox().getText() == "" ||
                eventTypeDropBox.getSelectedIndex() == -1) {
            return false;
        }
        return true;
    }
}
