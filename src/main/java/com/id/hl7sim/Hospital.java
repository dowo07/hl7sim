package com.id.hl7sim;

public interface Hospital {

	int getCapacity();

	void setCapacity(int capacity);

	int getOccupiedBeds();

	void setOccupiedBeds(int occupiedBeds);

	void addPatient();

	void removePatient();

	boolean freeBedsCheck();

	boolean occupiedBedsCheck();

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#admitPatient()
	 */
	void admitPatient();

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#transferPatient()
	 */
	void transferPatient();

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#dischargePatient()
	 */
	void dischargePatient();

	//	/* (non-Javadoc)
	//	 * @see com.id.hl7sim.Hospital#autoFillHospital()
	//	 */
	void autoFillHospital();

}