package com.id.hl7sim.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlRootElement(name = "Firstnames")
public class Firstnames {
	
	@XmlElement(name="Firstname")
	private List<Firstname> firstnames = new ArrayList<>();

	public List<Firstname> getFirstnames() {
		return firstnames;
	}

	public void setFirstnames(List<Firstname> firstnames) {
		this.firstnames = firstnames;
	}

	

}