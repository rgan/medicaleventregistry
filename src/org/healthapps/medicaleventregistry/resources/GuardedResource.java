package org.healthapps.medicaleventregistry.resources;

import org.healthapps.medicaleventregistry.dao.MedicalEventDao;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.model.User;
import org.restlet.data.ChallengeResponse;
import org.restlet.data.Request;
import org.restlet.resource.ServerResource;

public class GuardedResource extends BaseResource {

    protected User getUser(Request request) {
        final ChallengeResponse challengeResponse = request.getChallengeResponse();
        if (challengeResponse != null) {
            final String username = challengeResponse.getIdentifier();
            return dao.findUserByName(username);
        }
        return null;
    }
}
