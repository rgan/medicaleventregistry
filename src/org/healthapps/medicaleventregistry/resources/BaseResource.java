package org.healthapps.medicaleventregistry.resources;

import org.restlet.resource.ServerResource;
import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;

import java.net.URLDecoder;

public class BaseResource extends ServerResource {
    protected MedicalEventDao dao;

    public BaseResource() {
        this.dao = new MedicalEventDaoImpl();
    }

    protected String getAttributeValue(String key) {
        return URLDecoder.decode((String) getRequest()
                .getAttributes().get(key));
    }
}
