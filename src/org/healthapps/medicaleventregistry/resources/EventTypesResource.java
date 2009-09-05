package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import org.healthapps.medicaleventregistry.utils.JsonUtils;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

public class EventTypesResource extends ServerResource {
    private MedicalEventDao dao;

    public EventTypesResource() {
        this.dao = new MedicalEventDaoImpl();
    }

    @Get("json")
    public Representation getAll() throws IOException {
        final Collection<MedicalEventType> types = dao.allEventTypes();
        Collection<MedicalEventTypeAdaptor> adaptedTypes = new ArrayList<MedicalEventTypeAdaptor>();
        for (MedicalEventType type : types) {
            adaptedTypes.add(new MedicalEventTypeAdaptor(type));
        }
        return new StringRepresentation(JsonUtils.toJsonArray(adaptedTypes));
    }
}
