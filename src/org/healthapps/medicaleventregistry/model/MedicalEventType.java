package org.healthapps.medicaleventregistry.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.jdo.annotations.*;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class MedicalEventType {
    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Long id;
    @Persistent
    private String name;

    public MedicalEventType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void addToXmlDoc(Document document) {
        final Element element = document.createElement("medicalEventType");
        final Element idElement = document.createElement("id");
        idElement.appendChild(document.createTextNode(id != null ? id.toString() : ""));
        element.appendChild(idElement);
        final Element nameElement = document.createElement("name");
        nameElement.appendChild(document.createTextNode(name));
        element.appendChild(nameElement);
        document.getDocumentElement().appendChild(element);
    }
}
