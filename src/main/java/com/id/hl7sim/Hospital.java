package com.id.hl7sim;

public interface Hospital {

	void addPatient();
	
	void removePatient();
	
	int getCapacity();
	
	void setCapacity(int capacity);
	
	int getOccupiedBeds();
	
	void setOccupiedBeds(int occupiedBeds);
	
	boolean freeBedsCheck();
	
	boolean occupiedBedsCheck();
	
	void admitPatient();

	void transferPatient();

	void dischargePatient();

	void autoFillHospital();

}