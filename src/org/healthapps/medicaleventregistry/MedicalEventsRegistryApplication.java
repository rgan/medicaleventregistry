package org.healthapps.medicaleventregistry;

import org.healthapps.medicaleventregistry.resources.*;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Request;
import org.restlet.security.Guard;
import org.restlet.routing.Router;

public class MedicalEventsRegistryApplication extends Application {

    @Override
    public Restlet createRoot() {
        Router router = new Router(getContext());

        Router eventsRouter = new Router(getContext());
        eventsRouter.attach("/{typeId}/{fromDate}/{toDate}",
                EventsResource.class);
        eventsRouter.attach("/", EventResource.class);
        Guard eventsGuard = new ResourcesGuard(getContext());
        eventsGuard.setNext(eventsRouter);
        router.attach("/events", eventsGuard);

        router.attach("/types/", EventTypesResource.class);
        router.attach("/types/{typeName}", EventTypeResource.class);
        router.attach("/users/", UsersResource.class);
        router.attach("/sessions/", SessionsResource.class);

        return router;
    }
}