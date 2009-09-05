package org.healthapps.medicaleventregistry.dao;

import com.google.appengine.tools.development.ApiProxyLocalImpl;
import com.google.apphosting.api.ApiProxy;
import junit.framework.TestCase;

import java.io.File;

public abstract class LocalServiceTestCase extends TestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        ApiProxy.setEnvironmentForCurrentThread(new TestEnvironment());
        ApiProxy.setDelegate(new ApiProxyLocalImpl(new File(".")) {
        });
    }

    @Override
    public void tearDown() throws Exception {
        // not strictly necessary to null these out but there's no harm either
        ApiProxy.setDelegate(null);
        ApiProxy.setEnvironmentForCurrentThread(null);
        super.tearDown();
    }
}

