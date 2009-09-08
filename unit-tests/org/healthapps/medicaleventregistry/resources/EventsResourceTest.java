package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import org.healthapps.medicaleventregistry.model.User;
import org.restlet.Context;
import org.restlet.data.*;
import org.restlet.representation.Representation;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventsResourceTest extends ResourceTestCase {

    private EventsResource resource;
    private Response response;
    private Request request;
    private User createdBy;

    public void setUp() throws Exception {
        super.setUp();
        resource = new EventsResource();
        createdBy = new User("test", "test", "test@f.com");
        dao.store(createdBy);
        request = new Request(Method.GET, new Reference(URI));
        ChallengeResponse challengeResponse = new ChallengeResponse(ChallengeScheme.HTTP_BASIC,
                createdBy.getName(), createdBy.getPassword());
        request.setChallengeResponse(challengeResponse);
        response = new Response(request);
        eventType = createEventType("test");

    }

    public void testShouldGetSearchResults() throws IOException {
        final String name = "test";
        final double lat = 2.1;
        final double lon = 2.2;
        final MedicalEventType eventType = createEventType("test");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2009, 1, 1);
        final Date reportedDate = calendar.getTime();
        MedicalEvent event = new MedicalEvent(name, reportedDate, lat, lon, eventType, createdBy);
        dao.store(event);

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("fromDate", "01/01/2001");
        attributes.put("toDate", "01/01/2010");
        attributes.put("typeId", eventType.getId().toString());
        request.setAttributes(attributes);
        resource.init(new Context(), request, response);
        final Representation representation = resource.search();
        assertEquals(Status.SUCCESS_OK, response.getStatus());
        assertEquals("[{\"lat\":2.1,\"lon\":2.2,\"when\":\"02/01/2009\",\"who\":\"test\"}]", representation.getText());
    }

}
