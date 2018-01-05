package com.id.hl7sim;



public class HospitalImpl implements Hospital {

	private static int capacity;
	private static int occupiedBeds;
	private HL7Builder builder;
	public HL7Sender sender;
	private PatientRepository repository;

	
	public HospitalImpl(int capacity, HL7Builder builder, HL7Sender sender, PatientRepository patientRepository) {
		if(capacity < 10 || capacity > 1000) {
			throw new IllegalArgumentException("Hospital with that value of beds not possible");
		}
		else {
			HospitalImpl.capacity = capacity;
		}
		HospitalImpl.occupiedBeds = 0;
		this.builder = builder;
		this.sender = sender;
		this.repository = patientRepository; 
	}
 
	 
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#getCapacity()
	 */
	@Override
	public int getCapacity() {
		return capacity;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#setCapacity(int)
	 */
	@Override
	public void setCapacity(int capacity) {
		HospitalImpl.capacity = capacity;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#getOccupiedBeds()
	 */
	@Override
	public int getOccupiedBeds() {
		return occupiedBeds;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#setOccupiedBeds(int)
	 */
	@Override
	public void setOccupiedBeds(int occupiedBeds) {
		HospitalImpl.occupiedBeds = occupiedBeds;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#addPatient()
	 */
	@Override
	public void addPatient() {
		setCapacity(capacity - 1);
		setOccupiedBeds(occupiedBeds + 1);
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#removePatient()
	 */
	@Override
	public void removePatient() {
		setCapacity(capacity + 1);
		setOccupiedBeds(occupiedBeds - 1);
	}
 
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#freeBedsCheck()
	 */
	@Override
	public boolean freeBedsCheck() {
		return HospitalImpl.capacity > 0 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#occupiedBedsCheck()
	 */
	@Override
	public boolean occupiedBedsCheck() {
		return HospitalImpl.occupiedBeds < 1  ? false : true;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#admitPatient()
	 */
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#admitPatient()
	 */
	@Override
	public void admitPatient() {
		
			Patient patient = repository.admitRandomPatient();
			addPatient();
			String message = builder.createMessage(patient, Type.ADMISSION, Format.PIPE);
			sender.sendMessage(message);
	}	
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#transferPatient()
	 */
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#transferPatient()
	 */
	@Override
	public void transferPatient() {
		Patient patient = repository.transferRandomPatient();
		String message = builder.createMessage(patient, Type.TRANSFER, Format.PIPE);
		sender.sendMessage(message);
	}
	
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#dischargePatient()
	 */
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#dischargePatient()
	 */
	@Override
	public void dischargePatient() {

			Patient patient = repository.dischargeRandomPatient();
			removePatient();
			String message = builder.createMessage(patient, Type.DISCHARGE, Format.PIPE);
			sender.sendMessage(message);
	}
	
//	/* (non-Javadoc)
//	 * @see com.id.hl7sim.Hospital#autoFillHospital()
//	 */
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#autoFillHospital()
	 */
	@Override
	public void autoFillHospital() {
		do {
			admitPatient();
		}
		while(freeBedsCheck());
	}
	
	
}