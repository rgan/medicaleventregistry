package org.healthapps.medicaleventregistry.dao;

import com.google.apphosting.api.ApiProxy;

import java.util.HashMap;
import java.util.Map;

public class TestEnvironment implements ApiProxy.Environment {
    public String getAppId() {
        return "Unit Tests";
    }

    public String getVersionId() {
        return "1.0";
    }

    public void setDefaultNamespace(String s) {
    }

    public String getRequestNamespace() {
        return "gmail.com";
    }

    public Map<String, Object> getAttributes() {
        return new HashMap<String, Object>();
    }

    public String getDefaultNamespace() {
        return "";
    }

    public String getAuthDomain() {
        return "gmail.com";
    }

    public boolean isLoggedIn() {
        return false;
    }

    public String getEmail() {
        return "";
    }

    public boolean isAdmin() {
        return false;
    }
}

