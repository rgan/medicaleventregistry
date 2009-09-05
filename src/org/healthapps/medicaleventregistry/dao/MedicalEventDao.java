package org.healthapps.medicaleventregistry.dao;

import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.MedicalEventType;

import java.util.Collection;
import java.util.Date;

public interface MedicalEventDao {
    void store(MedicalEventType eventType);

    MedicalEventType findEventTypeByName(String name);

    Collection<MedicalEventType> allEventTypes();

    void store(MedicalEvent event);

    Collection<MedicalEvent> searchEvents(Long typeId, Date fromDate, Date toDate);

    Object findById(Long eventTypeId, String name);
}
