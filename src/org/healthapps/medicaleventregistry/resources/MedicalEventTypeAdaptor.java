package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.model.MedicalEventType;

public class MedicalEventTypeAdaptor {
    private MedicalEventType eventType;

    public MedicalEventTypeAdaptor(MedicalEventType eventType) {
        this.eventType = eventType;
    }

    public String getLabel() {
        return eventType.getName();
    }

    public Long getData() {
        return eventType.getId();
    }
}
