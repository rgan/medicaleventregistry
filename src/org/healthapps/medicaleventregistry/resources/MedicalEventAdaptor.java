package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import org.healthapps.utils.GeoLocation;

import java.util.List;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class MedicalEventAdaptor {
    private MedicalEvent medicalEvent;
    private MedicalEventType eventType;

    public MedicalEventAdaptor(MedicalEvent medicalEvent, MedicalEventDao dao) {
        this.medicalEvent = medicalEvent;
        this.eventType = (MedicalEventType) dao.findById(medicalEvent.getEventTypeId(), MedicalEventType.class.getName());
    }

    public String getWho() {
        return medicalEvent.getWho();
    }

    public String getWhen() {
        return EventResource.EVENT_DATE_FORMAT.format(medicalEvent.getWhen());
    }

    public List<GeoLocation> getVertices() {
        return Lists.newArrayList(new GeoLocation(medicalEvent.getLat(), medicalEvent.getLon()));
    }

    public String getEventType() {
        return eventType.getName();
    }

}
