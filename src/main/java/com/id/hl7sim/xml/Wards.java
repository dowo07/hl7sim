package com.id.hl7sim.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(value=XmlAccessType.FIELD)
@XmlRootElement(name = "Departments")
public class Wards {
	
	@XmlElement(name="Ward")
	private List<Ward> wards = new ArrayList<>();

	public List<Ward> getWards() {
		return wards;
	}

	public void setWards(List<Ward> wards) {
		this.wards = wards;
	}
	
	
	

}
