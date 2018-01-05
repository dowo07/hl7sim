package com.id.hl7sim;



public class HospitalImpl implements Hospital {

	private static int capacity;
	private static int occupiedBeds;
	private HL7Builder builder;
	private PatientRepository repository;
	public HL7Sender sender;
	
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

	 
	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		HospitalImpl.capacity = capacity;
	}

	public int getOccupiedBeds() {
		return occupiedBeds;
	}

	public void setOccupiedBeds(int occupiedBeds) {
		HospitalImpl.occupiedBeds = occupiedBeds;
	}

	public void addPatient() {
		setCapacity(capacity - 1);
		setOccupiedBeds(occupiedBeds + 1);
	}

	public void removePatient() {
		setCapacity(capacity + 1);
		setOccupiedBeds(occupiedBeds - 1);
	}
 
	public boolean freeBedsCheck() {
		return HospitalImpl.capacity > 0 ? true : false;
	}
	
	public boolean occupiedBedsCheck() {
		return HospitalImpl.occupiedBeds < 1  ? false : true;
	}

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
	@Override
	public void transferPatient() {
		Patient patient = repository.transferRandomPatient();
		String message = builder.createMessage(patient, Type.TRANSFER, Format.PIPE);
		sender.sendMessage(message);
	}
	
	
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
	@Override
	public void autoFillHospital() {
		do {
			admitPatient();
		}
		while(freeBedsCheck());
	}
	
	
}