package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.model.MedicalEvent;

public class MedicalEventAdaptor {
    private MedicalEvent medicalEvent;

    public MedicalEventAdaptor(MedicalEvent medicalEvent) {
        this.medicalEvent = medicalEvent;
    }

    public String getWho() {
        return medicalEvent.getWho();
    }

    public String getWhen() {
        return EventResource.EVENT_DATE_FORMAT.format(medicalEvent.getWhen());
    }

    public Double getLat() {
        return medicalEvent.getLat();
    }

    public Double getLon() {
        return medicalEvent.getLon();
    }
}
