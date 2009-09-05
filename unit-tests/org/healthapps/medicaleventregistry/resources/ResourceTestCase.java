package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.LocalDatastoreTestCase;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.DatastoreServiceFactory;

public class ResourceTestCase extends LocalDatastoreTestCase {
    protected final String URI = "http://test";
    protected MedicalEventType eventType;
    protected MedicalEventDao dao = new MedicalEventDaoImpl();

    protected Long getSavedEntityId(Class clazz) {
        final PreparedQuery preparedQuery = getPreparedQuery(clazz.getSimpleName());
        assertEquals(1, preparedQuery.countEntities());
        final Entity entity = preparedQuery.asSingleEntity();
        Long id = entity.getKey().getId();
        return id;
    }

    private PreparedQuery getPreparedQuery(String className) {
        Query query = new Query(className);
        final PreparedQuery preparedQuery = DatastoreServiceFactory.getDatastoreService().prepare(query);
        return preparedQuery;
    }

    protected MedicalEventType createEventType(String name) {
        MedicalEventType eventType = new MedicalEventType(name);
        dao.store(eventType);
        return eventType;
    }
}
