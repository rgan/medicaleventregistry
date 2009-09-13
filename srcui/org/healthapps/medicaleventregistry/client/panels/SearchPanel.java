package org.healthapps.medicaleventregistry.client.panels;

import com.google.gwt.i18n.client.Constants;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import org.healthapps.medicaleventregistry.client.Utils;

public class SearchPanel extends DecoratorPanel {
    private DateBox dateFromDateBox;
    private DateBox dateToDateBox;
    private ListBox eventTypeDropBox;

    public void setEventTypes(JSONArray eventTypesArray) {
        Utils.setEventTypes(eventTypesArray, eventTypeDropBox);
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
        layout.setWidget(1, 1, dateFromDateBox);

        layout.setHTML(2, 0, constants.searchPanelDateTo());
        dateToDateBox = new DateBox();
        layout.setWidget(2, 1, dateToDateBox);

        layout.setHTML(3, 0, constants.searchPanelTypesListBox());
        eventTypeDropBox = new ListBox(false);
        layout.setWidget(3, 1, eventTypeDropBox);

        Button searchSubmitBtn = new Button();
        searchSubmitBtn.setText(constants.searchPanelSearchSubmitButtonText());
        layout.setWidget(4, 0, searchSubmitBtn);
        cellFormatter.setColSpan(4, 0, 2);
        cellFormatter.setHorizontalAlignment(4, 0,
                HasHorizontalAlignment.ALIGN_CENTER);
    }
}
