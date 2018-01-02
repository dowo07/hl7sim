package com.id.hl7sim;



public class HospitalImpl implements Hospital {

	private static int capacity;
	private static int occupiedBeds;
	private HL7Builder builder;
	private PatientRepository repository;

	
	public HospitalImpl(int capacity, HL7Builder builder, PatientRepository patientRepository) {
		if(capacity < 10 || capacity > 1000) {
			throw new IllegalArgumentException("Hospital with that value of beds not possible");
		}
		else {
			HospitalImpl.capacity = capacity;
		}
		HospitalImpl.occupiedBeds = 0;
		this.builder = builder;
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
		if (freeBedsCheck()) {
			Patient patient = repository.admitRandomPatient();
			addPatient();
			builder.createMessage(patient, Type.ADMISSION, Format.PIPE);
		}
		else {
				throw new RuntimeException("No more free beds");
		}
	}	
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#transferPatient()
	 */
	@Override
	public void transferPatient() {
		Patient patient = repository.transferRandomPatient();
		builder.createMessage(patient, Type.TRANSFER, Format.PIPE);
	}
	
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.Hospital#dischargePatient()
	 */
	@Override
	public void dischargePatient() {
		if(occupiedBedsCheck()) {
			Patient patient = repository.dischargeRandomPatient();
			removePatient();
			builder.createMessage(patient, Type.DISCHARGE, Format.PIPE);
		}
		else {
			throw new RuntimeException("No Patients in Hospital");
		}
	}
	
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