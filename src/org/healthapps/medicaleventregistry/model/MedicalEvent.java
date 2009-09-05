package org.healthapps.medicaleventregistry.model;

import javax.jdo.annotations.*;
import java.util.Date;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MedicalEvent {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String who;
    @Persistent
    private Date when;
    @Persistent
    private Double lat;
    @Persistent
    private Double lon;
    @Persistent(defaultFetchGroup = "true")
    private Long eventTypeId;

    public MedicalEvent(String name, Date date, Double lat, Double lon, MedicalEventType eventType) {
        this.who = name;
        this.when = date;
        this.lat = lat;
        this.lon = lon;
        this.eventTypeId = eventType.getId();
    }

    public String getWho() {
        return who;
    }

    public Date getWhen() {
        return when;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }

    public Long getId() {
        return id;
    }

    public Long getEventTypeId() {
        return eventTypeId;
    }
}
