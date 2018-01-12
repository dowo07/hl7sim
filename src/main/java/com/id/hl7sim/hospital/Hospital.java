package com.id.hl7sim.hospital;


public interface Hospital {

	int getCapacity();

	void setCapacity(int capacity);

	int getOccupiedBeds();

	void setOccupiedBeds(int occupiedBeds);

	void addPatient();

	void removePatient();

	boolean freeBedsCheck();

	boolean occupiedBedsCheck();

	void admitPatient();

	void transferPatient();

	void dischargePatient();

	void autoFillHospital();
}