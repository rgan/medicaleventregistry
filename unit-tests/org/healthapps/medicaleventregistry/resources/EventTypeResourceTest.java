package org.healthapps.medicaleventregistry.resources;

import org.restlet.Context;
import org.restlet.data.*;

import java.util.Map;
import java.util.HashMap;

public class EventTypeResourceTest extends ResourceTestCase {
    private EventTypeResource resource;
    private Response response;
    private Request request;

    public void setUp() throws Exception {
        super.setUp();
        resource = new EventTypeResource();
        request = new Request(Method.POST, new Reference(URI));
        response = new Response(request);
    }

    public void testShouldCreateEventType() {

        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("typeName", "test");
        resource.init(new Context(), request, response);
        request.setAttributes(attributes);
        resource.storeEventType();
        assertEquals(Status.SUCCESS_CREATED, response.getStatus());
    }

    public void testShouldNotCreateEventTypeIfExists() {
        createEventType("test");
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("typeName", "test");
        request.setAttributes(attributes);
        resource.init(new Context(), request, response);
        resource.storeEventType();
        assertEquals(Status.SUCCESS_OK, response.getStatus());
    }
}

