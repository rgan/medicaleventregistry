package org.healthapps.medicaleventregistry.dao;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import org.healthapps.medicaleventregistry.model.User;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class MedicalEventDaoTest extends LocalDatastoreTestCase {
    private MedicalEventDao dao;
    private User createdBy;

    public void setUp() throws Exception {
        super.setUp();
        dao = new MedicalEventDaoImpl(PMF.get());
        createdBy = new User("test", "test", "test@f.com");
        dao.store(createdBy);
    }

    private PreparedQuery getPreparedQuery(String className) {
        Query query = new Query(className);
        final PreparedQuery preparedQuery = DatastoreServiceFactory.getDatastoreService().prepare(query);
        return preparedQuery;
    }

    public void testSearchShouldReturnValues() {
        final String name = "test";
        final double lat = 2.1;
        final double lon = 2.2;
        final MedicalEventType eventType = createEventType("test");
        final Date reportedDate = new Date();
        MedicalEvent event = new MedicalEvent(name, reportedDate, lat, lon, eventType, createdBy);
        dao.store(event);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(reportedDate);
        calendar.add(Calendar.MONTH, -1);
        Date fromDate = calendar.getTime();
        calendar.setTime(reportedDate);
        calendar.add(Calendar.MONTH, 1);
        Date toDate = calendar.getTime();
        final Collection<MedicalEvent> events = dao.searchEvents(eventType.getId(), fromDate, toDate);
        assertEquals(1, events.size());
    }

    public void testShouldSaveEvent() {
        final String name = "test";
        final double lat = 2.1;
        final double lon = 2.2;
        final MedicalEventType eventType = createEventType("test");
        MedicalEvent event = new MedicalEvent(name, new Date(), lat, lon, eventType, createdBy);
        dao.store(event);
        final PreparedQuery preparedQuery = getPreparedQuery(MedicalEvent.class.getSimpleName());
        assertEquals(1, preparedQuery.countEntities());
        final Entity entity = preparedQuery.asSingleEntity();
        assertEquals(name, entity.getProperty("who"));
        assertEquals(lat, entity.getProperty("lat"));
        assertEquals(lon, entity.getProperty("lon"));
        assertEquals(createdBy.getId(), entity.getProperty("createdById"));
        assertEquals(eventType.getId(), entity.getProperty("eventTypeId"));
        assertEquals(new Long(event.getCSCode().getTens()), entity.getProperty("csTens"));
    }

    public void testShouldSaveEventType() {
        final String name = "test";
        createEventType(name);
        final PreparedQuery preparedQuery = getPreparedQuery(MedicalEventType.class.getSimpleName());
        assertEquals(1, preparedQuery.countEntities());
    }

    private MedicalEventType createEventType(String name) {
        MedicalEventType eventType = new MedicalEventType(name);
        dao.store(eventType);
        return eventType;
    }
}
