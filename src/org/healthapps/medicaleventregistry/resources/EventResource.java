package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.model.MedicalEvent;
import org.healthapps.medicaleventregistry.model.MedicalEventType;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventResource extends ServerResource {
    public final static SimpleDateFormat EVENT_DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");

    private MedicalEventDao dao;

    public EventResource() {
        this(new MedicalEventDaoImpl());
    }

    public EventResource(MedicalEventDao dao) {
        this.dao = dao;
    }

    @Post
    public Representation addEvent(Representation entity) {
        try {
            Form form = new Form(entity);
            final String name = form.getFirstValue("name");
            final String dateReported = form.getFirstValue("dateReported");
            final Date date = EVENT_DATE_FORMAT.parse(dateReported);
            final Double lat = Double.parseDouble(form.getFirstValue("lat"));
            final Double lon = Double.parseDouble(form.getFirstValue("lon"));
            final Long eventTypeId = Long.parseLong(form.getFirstValue("eventTypeId"));
            final MedicalEvent event = new MedicalEvent(name, date, lat, lon, (MedicalEventType)dao.findById(eventTypeId, MedicalEventType.class.getName()));
            dao.store(event);
            setStatus(Status.SUCCESS_CREATED);
            Representation representation = new StringRepresentation("Event created",
                    MediaType.TEXT_PLAIN);
            representation.setIdentifier(getRequest().getResourceRef().getIdentifier()
                    + "/" + event.getId());
            return representation;
        } catch (Exception ex) {
            ex.printStackTrace();
            setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
            return new StringRepresentation("Error occurred.", MediaType.TEXT_PLAIN);
        }
    }
}
