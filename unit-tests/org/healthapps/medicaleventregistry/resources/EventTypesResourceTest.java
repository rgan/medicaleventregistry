package org.healthapps.medicaleventregistry.resources;

import org.restlet.data.Method;
import org.restlet.data.Reference;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.restlet.representation.Representation;

import java.io.IOException;

public class EventTypesResourceTest extends ResourceTestCase {
    private EventTypesResource resource;
    private Response response;
    private Request request;

    public void setUp() throws Exception {
        super.setUp();
        resource = new EventTypesResource();
        request = new Request(Method.POST, new Reference(URI));
        response = new Response(request);
    }

    public void testShouldReturnAllEventTypes() throws IOException {
        createEventType("test");
        final Representation representation = resource.getAll();
        assertEquals("[{\"data\":1,\"label\":\"test\"}]", representation.getText());
    }
}
