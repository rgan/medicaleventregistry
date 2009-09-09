package org.healthapps.medicaleventregistry.model;

import org.healthapps.utils.CSquareCode;

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
    @Persistent
    private Long createdById;
    @Persistent
    private Integer csTens;
    @Persistent
    private Integer csUnits;
    @Persistent
    private Integer csTenths;

    public MedicalEvent(String name, Date date, Double lat, Double lon, MedicalEventType eventType, User createdBy) {
        this.who = name;
        this.when = date;
        this.lat = lat;
        this.lon = lon;
        CSquareCode code = CSquareCode.from(lat, lon);
        this.csTens = code.getTens();
        this.csUnits = code.getUnits();
        this.csTenths = code.getTenths();
        this.createdById = createdBy.getId();
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

    public Long getCreatedById() {
        return createdById;
    }

    public CSquareCode getCSCode() {
        return new CSquareCode(csTens, csUnits, csTenths);
    }
}
