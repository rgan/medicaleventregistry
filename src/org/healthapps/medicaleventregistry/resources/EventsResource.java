package org.healthapps.medicaleventregistry.resources;

import com.google.appengine.repackaged.com.google.common.collect.Lists;
import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.User;
import org.healthapps.medicaleventregistry.utils.JsonUtils;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.Date;

public class EventsResource extends GuardedResource {

    // Flex will not pass auth headers for a GET request.
    @Post
    public Representation searchViaPost() throws IOException {
        return search();
    }

    @Get("json")
    public Representation search() throws IOException {
        try {
            Date fromDate = EventResource.EVENT_DATE_FORMAT.parse(getAttributeValue("fromDate"));
            Date toDate = EventResource.EVENT_DATE_FORMAT.parse(getAttributeValue("toDate"));
            Long typeId = Long.valueOf((String) getRequest()
                    .getAttributes().get("typeId"));
            final User user = getUser(getRequest());
            final Long userId = user != null ? user.getId() : null;
            final Collection<MedicalEvent> events = dao.searchEvents(typeId, fromDate, toDate);
            Collection adaptedEvents = Lists.newArrayList();
            for (MedicalEvent event : events) {
                if (event.getCreatedById().equals(userId)) {
                    adaptedEvents.add(new MedicalEventAdaptor(event, dao));
                } else {
                    adaptedEvents.add(new MedicalEventSummaryAdaptor(event, dao));
                }
            }
            return new StringRepresentation(JsonUtils.toJsonArray(adaptedEvents));
        } catch (Exception ex) {
            ex.printStackTrace();
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return new StringRepresentation("Error occurred. Check inputs.", MediaType.TEXT_PLAIN);
        }
    }

}
