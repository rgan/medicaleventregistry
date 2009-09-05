package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import org.healthapps.medicaleventregistry.utils.JsonUtils;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import java.io.IOException;

/**
 * Resource which has only one representation.
 */
public class EventTypeResource extends ServerResource {
    private MedicalEventDao dao;
    private MedicalEventType eventType;
    private String eventTypeName;

    public EventTypeResource() {
        this.dao = new MedicalEventDaoImpl();
    }

    @Override
    protected void doInit() throws ResourceException {
        eventTypeName = (String) getRequest()
                .getAttributes().get("typeName");
        eventType = dao.findEventTypeByName(eventTypeName);
        setExists(eventType != null);
    }

    @Get("json")
    public Representation getJson() throws IOException {
        StringRepresentation representation =
                new StringRepresentation(JsonUtils.toJson(eventType));
        return representation;
    }

    @Post
    public void storeEventType() {

        if (eventType == null) {
            eventType = new MedicalEventType(eventTypeName);
            dao.store(eventType);
            setStatus(Status.SUCCESS_CREATED);
        } else {
            setStatus(Status.SUCCESS_OK);
        }
    }
} 