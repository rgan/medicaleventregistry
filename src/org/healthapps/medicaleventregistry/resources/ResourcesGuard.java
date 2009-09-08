package org.healthapps.medicaleventregistry.resources;

import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Request;
import org.restlet.data.ChallengeResponse;
import org.restlet.security.Guard;
import org.healthapps.medicaleventregistry.model.User;
import org.healthapps.medicaleventregistry.dao.MedicalEventDaoImpl;
import org.healthapps.medicaleventregistry.dao.MedicalEventDao;

public class ResourcesGuard extends Guard {
    private MedicalEventDao dao;

    public ResourcesGuard(Context context) {
        this(context, ChallengeScheme.HTTP_BASIC, "MedicalEventRegistry");
    }

    public ResourcesGuard(Context context, ChallengeScheme challengeScheme, String s) throws IllegalArgumentException {
        super(context, challengeScheme, s);
        dao = new MedicalEventDaoImpl(); 
    }

    @Override
    public int authenticate(Request request) {
        final ChallengeResponse challengeResponse = request.getChallengeResponse();
        if (challengeResponse != null) {
            final String username = challengeResponse.getIdentifier();
            final char[] pwd = challengeResponse.getSecret();
            if (User.authenticate(dao, username, new String(pwd))) {
               return 1;
            }
            return -1;
        }
        return 0;
    }

}
