package org.healthapps.medicaleventregistry.model;

import junit.framework.TestCase;

public class UserTest extends TestCase {

    public void testShouldValidateEmail() {
        final User user = new User("name", "pwd", "invalidEmail");
        assertFalse(user.validate());
    }

    public void testShouldValidateName() {
        final User user = new User("<invalidname", "pwd", "foo@b.com");
        assertFalse(user.validate());
    }

    public void testShouldValidatePwd() {
        final User user = new User("", "<pwd", "foo@b.com");
        assertFalse(user.validate());
    }
}

