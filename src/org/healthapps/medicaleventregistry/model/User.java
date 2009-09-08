package org.healthapps.medicaleventregistry.model;

import org.healthapps.medicaleventregistry.dao.MedicalEventDao;

import javax.jdo.annotations.*;
import java.util.regex.Pattern;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User {
    private static final String NAME_REGEX = "\\A[\\s.a-zA-Z0-9_-]+\\Z";
    private static final String EMAIL_REGEX = "[.a-zA-Z0-9_-]+[@]{1}[.a-zA-Z0-9_-]+";

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    protected Long id;
    @Persistent
    protected String name;
    @Persistent
    private String password;
    @Persistent
    private boolean enabled;
    @Persistent
    protected String email;

    public User(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.enabled = true;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getEmail() {
        return email;
    }

    public boolean validate() {
        return validate(name, NAME_REGEX) &&
                validate(password, NAME_REGEX) &&
                validate(email, EMAIL_REGEX);
    }

    private boolean validate(String value, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(value).find();
    }

    public boolean passwordMatches(String password) {
        return password.equals(password);
    }

    public static boolean authenticate(MedicalEventDao dao, String username, String password) {
        final User user = dao.findUserByName(username);
        return user != null && user.passwordMatches(password);
    }

    public String getPassword() {
        return password;
    }
}
