package com.id.hl7sim.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlRootElement(name = "Lastnames")
public class Lastnames {
	
	@XmlElement(name="Lastname")
	private List<Lastname> lastnames = new ArrayList<>();

	public List<Lastname> getLastnames() {
		return lastnames;
	}

	public void setLastnames(List<Lastname> lastnames) {
		this.lastnames = lastnames;
	}



}