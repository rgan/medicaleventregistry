package org.healthapps.medicaleventregistry;

import org.healthapps.medicaleventregistry.resources.EventResource;
import org.healthapps.medicaleventregistry.resources.EventTypeResource;
import org.healthapps.medicaleventregistry.resources.EventTypesResource;
import org.healthapps.medicaleventregistry.resources.EventsResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class MedicalEventsRegistryApplication extends Application {

    @Override
    public Restlet createRoot() {
        Router router = new Router(getContext());

        router.attach("/types/{typeName}", EventTypeResource.class);
        router.attach("/types/", EventTypesResource.class);
        router.attach("/events/{typeId}/{fromDate}/{toDate}",
                EventsResource.class);
        router.attach("/events/", EventResource.class);

        return router;
    }
}