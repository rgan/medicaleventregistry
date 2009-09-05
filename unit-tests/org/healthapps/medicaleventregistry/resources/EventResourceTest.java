package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.restlet.Context;
import org.restlet.data.*;
import org.restlet.representation.Representation;

public class EventResourceTest extends ResourceTestCase {
    private EventResource resource;
    private Response response;
    private Request request;

    public void setUp() throws Exception {
        super.setUp();
        resource = new EventResource();
        request = new Request(Method.POST, new Reference(URI));
        response = new Response(request);
        resource.init(new Context(), request, response);
        eventType = createEventType("test");
    }

    public void testShouldAddEvent() throws Exception {
        Form form = new Form();
        form.add("name", "test");
        form.add("dateReported", "09/09/2009");
        form.add("lat", "2.1");
        form.add("lon", "2.1");
        form.add("eventTypeId", eventType.getId().toString());
        final Representation representation =
                resource.addEvent(form.getWebRepresentation());
        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
        Long id = getSavedEntityId(MedicalEvent.class);
        assertEquals(new Reference(URI + "/" + id), representation.getIdentifier());
    }

    public void testShouldErrorOnIncorrectInput() throws Exception {
        Form form = new Form();
        form.add("name", "test");
        form.add("dateReported", "fdfdfa");
        form.add("lat", "dfasf");
        form.add("lon", "2.1");
        form.add("eventTypeId", eventType.getId().toString());
        final Representation representation =
                resource.addEvent(form.getWebRepresentation());
        assertEquals(Status.CLIENT_ERROR_BAD_REQUEST, response.getStatus());
    }
}
