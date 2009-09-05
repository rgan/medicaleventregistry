package org.healthapps.medicaleventregistry.dao;

import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.MedicalEventType;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MedicalEventDaoImpl extends AbstractDao implements MedicalEventDao {

    public MedicalEventDaoImpl(PersistenceManagerFactory factory) {
        super(factory);
    }

    public MedicalEventDaoImpl() {
        this(PMF.get());
    }

    public void store(MedicalEventType eventType) {
        storeObject(eventType);
    }

    public MedicalEventType findEventTypeByName(String name) {
        return (MedicalEventType) findByName(name, MedicalEventType.class.getName());
    }

    public Collection<MedicalEventType> allEventTypes() {
        return all(MedicalEventType.class.getName());
    }

    public void store(MedicalEvent event) {
        storeObject(event);
    }

    public Collection<MedicalEvent> searchEvents(Long typeId, Date fromDate, Date toDate) {
        PersistenceManager pm = getPM();
        String queryString = "select from " + MedicalEvent.class.getName() + " where eventTypeId == typeId && when < toDate && when > fromDate PARAMETERS long typeId, java.util.Date toDate, java.util.Date fromDate order by when ascending";
        try {
            Query query = pm.newQuery(queryString);
            query.setRange(0, 100);
            List<MedicalEvent> events = (List<MedicalEvent>) query.executeWithArray(typeId, toDate, fromDate);
            return pm.detachCopyAll(events);
        } finally {
            pm.close();
        }
    }
}
