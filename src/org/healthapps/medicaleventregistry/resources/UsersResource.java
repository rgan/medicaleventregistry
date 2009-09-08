package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.model.User;
import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

public class UsersResource extends ServerResource {

    private MedicalEventDao dao;

    public UsersResource() {
        this.dao = new MedicalEventDaoImpl();
    }

    @Get
    public Representation dummy() {
        return new StringRepresentation("");
    }

    @Post
    public Representation addUser(Representation entity) {
        Form form = new Form(entity);
        final String username = form.getFirstValue("username");
        final String pwd = form.getFirstValue("password");
        final String email = form.getFirstValue("email");
        final User user = new User(username, pwd, email);
        if (user.validate()) {
            dao.store(user);
            setStatus(Status.SUCCESS_CREATED);
            Representation representation = new StringRepresentation("Event created",
                    MediaType.TEXT_PLAIN);
            representation.setIdentifier(getRequest().getResourceRef().getIdentifier()
                    + "/" + user.getId());
            return representation;
        }
        setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
        return new StringRepresentation("Error occurred.", MediaType.TEXT_PLAIN);
    }
}
