package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.model.User;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class SessionsResource extends ServerResource {
    private MedicalEventDao dao;

    public SessionsResource() {
        this.dao = new MedicalEventDaoImpl();
    }

    @Get
    public Representation dummy() {
        return new StringRepresentation("");
    }

    @Post
    public Representation createSession(Representation entity) {
        Form form = new Form(entity);
        final String username = form.getFirstValue("username");
        final String password = form.getFirstValue("password");
        
        if (User.authenticate(dao, username, password)) {
            setStatus(Status.SUCCESS_CREATED);
            return new StringRepresentation("User authenticated",
                    MediaType.TEXT_PLAIN);
        } else {
            setStatus(Status.CLIENT_ERROR_UNAUTHORIZED);
            return new StringRepresentation("Invalid credentials",
                    MediaType.TEXT_PLAIN);
        }
    }
}
