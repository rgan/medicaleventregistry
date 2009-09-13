package org.healthapps.medicaleventregistry.client;

import com.google.gwt.i18n.client.Constants;
import org.healthapps.medicaleventregistry.client.panels.LoginPanel;
import org.healthapps.medicaleventregistry.client.panels.EventPanel;
import org.healthapps.medicaleventregistry.client.panels.SearchPanel;

public interface MedicalEventRegistryUIConstants extends Constants,
        LoginPanel.UIConstants, EventPanel.UIConstants, SearchPanel.UIConstants {

    @DefaultStringValue("Login")
    String getLoginPanelTitle();

    @DefaultStringValue("Add Event")
    String getEventPanelTitle();

    @DefaultStringValue("Search")
    String getSearchPanelTitle();
}
