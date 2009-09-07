package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.utils.JsonUtils;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.resource.Post;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.net.URLDecoder;

import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class EventsResource extends ServerResource {

    private MedicalEventDao dao;

    public EventsResource() {
        this.dao = new MedicalEventDaoImpl();
    }

    @Get("json")
    public Representation search() throws IOException {
        try {
            Date fromDate = EventResource.EVENT_DATE_FORMAT.parse(getAttributeValue("fromDate"));
            Date toDate = EventResource.EVENT_DATE_FORMAT.parse(getAttributeValue("toDate"));
            Long typeId = Long.valueOf((String) getRequest()
                    .getAttributes().get("typeId"));
            final Collection<MedicalEvent> events = dao.searchEvents(typeId, fromDate, toDate);
            Collection adaptedEvents = Lists.newArrayList();
            for(MedicalEvent event : events) {
               adaptedEvents.add(new MedicalEventAdaptor(event));
            }
            return new StringRepresentation(JsonUtils.toJsonArray(adaptedEvents));
        } catch (Exception ex) {
            ex.printStackTrace();
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return new StringRepresentation("Error occurred. Check inputs.", MediaType.TEXT_PLAIN);
        }
    }

    private String getAttributeValue(String key) {
        return URLDecoder.decode((String) getRequest()
                .getAttributes().get(key));
    }
}
