package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.utils.CSquareCode;
import org.healthapps.utils.GeoLocation;

import java.util.List;

public class MedicalEventSummaryAdaptor {
    private MedicalEvent medicalEvent;
    private MedicalEventType eventType;

    public MedicalEventSummaryAdaptor(MedicalEvent medicalEvent, MedicalEventDao dao) {
        this.medicalEvent = medicalEvent;
        this.eventType = (MedicalEventType)dao.findById(eventType.getId(), MedicalEventType.class.getName());
    }

    public String getWho() {
        return "";
    }

    public String getWhen() {
        return EventResource.EVENT_DATE_FORMAT.format(medicalEvent.getWhen());
    }

    public List<GeoLocation> getVertices() {
        final CSquareCode csCode = medicalEvent.getCSCode();
        return CSquareCode.boundingBoxfrom(csCode.getTens(), csCode.getUnits()).getVertices();
    }

    public String getEventType() {
        return eventType.getName();
    }
}
