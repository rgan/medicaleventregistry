package org.healthapps.medicaleventregistry.client.panels;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import org.healthapps.medicaleventregistry.client.Utils;

public class EventPanel extends DecoratorPanel {
    private TextBox typeNameTextBox;
    private Button addTypeSubmitBtn;
    private TextBox nameTextBox;
    private Button addEventSubmitBtn;
    private DateBox dateTextBox;
    private TextBox latTextBox;
    private TextBox lonTextBox;
    private ListBox eventTypeDropBox;

    public void setEventTypes(JSONArray eventTypesArray) {
        Utils.setEventTypes(eventTypesArray, eventTypeDropBox);
    }

    public static interface UIConstants extends Constants {
        @DefaultStringValue("Add Event")
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
    }
}
