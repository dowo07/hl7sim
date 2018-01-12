package com.id.hl7sim.hospital;


import java.util.concurrent.atomic.AtomicInteger;
import com.id.hl7sim.database.PatientRepository;
import com.id.hl7sim.hl7.Format;
import com.id.hl7sim.hl7.HL7Builder;
import com.id.hl7sim.hl7.HL7Sender;
import com.id.hl7sim.hl7.Type;
import com.id.hl7sim.patient.Patient;


public class HospitalImpl implements Hospital {

	
	private static AtomicInteger capacity;
	private static AtomicInteger occupiedBeds;
	private HL7Builder builder;
	private HL7Sender sender;
	private PatientRepository repository;

	 
	public HospitalImpl(int capacity, HL7Builder builder, HL7Sender sender, PatientRepository patientRepository) {
		
		if(capacity < 10 || capacity > 1000) {
			throw new IllegalArgumentException("Hospital with that value of beds not possible");
		}
		else {
			HospitalImpl.capacity = new AtomicInteger(capacity);
		} 
		HospitalImpl.occupiedBeds = new AtomicInteger(0);
		this.builder = builder;
		this.sender = sender;
		this.repository = patientRepository; 
	}
 
	 
	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#getCapacity()
	 */
	@Override
	public int getCapacity() {
		
		return capacity.get();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#setCapacity(java.util.concurrent.atomic.AtomicInteger)
	 */
	@Override
	public void setCapacity(int capacity) {
		
		HospitalImpl.capacity.set(capacity);
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#getOccupiedBeds()
	 */
	@Override
	public int getOccupiedBeds() {
		
		return occupiedBeds.get();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#setOccupiedBeds(java.util.concurrent.atomic.AtomicInteger)
	 */
	@Override
	public void setOccupiedBeds(int occupiedBeds) {
		
		HospitalImpl.occupiedBeds.set(occupiedBeds);
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#addPatient()
	 */
	@Override
	public void addPatient() {
		
		HospitalImpl.capacity.decrementAndGet();
		HospitalImpl.occupiedBeds.incrementAndGet();
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#removePatient()
	 */
	@Override
	public void removePatient() {
		
		HospitalImpl.capacity.incrementAndGet();
		HospitalImpl.occupiedBeds.decrementAndGet();
	}
 
	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#freeBedsCheck()
	 */
	@Override
	public boolean freeBedsCheck() {
		
		return HospitalImpl.capacity.get() > 0 ? true : false;
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#occupiedBedsCheck()
	 */
	@Override
	public boolean occupiedBedsCheck() {
		
		return HospitalImpl.occupiedBeds.get() < 1  ? false : true;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#admitPatient()
	 */
	@Override
	public void admitPatient() {
	
			Patient patient = repository.admitRandomPatient();
			addPatient();
			String message = builder.createMessage(patient, Type.ADMISSION, Format.PIPE);
			sender.sendMessage(message);
	}	
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#transferPatient()
	 */
	@Override
	public void transferPatient() {
		
		Patient patient = repository.transferRandomPatient();
		String message = builder.createMessage(patient, Type.TRANSFER, Format.PIPE);
		sender.sendMessage(message);
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#dischargePatient()
	 */
	@Override
	public void dischargePatient() {

			Patient patient = repository.dischargeRandomPatient();
			removePatient();
			String message = builder.createMessage(patient, Type.DISCHARGE, Format.PIPE);
			sender.sendMessage(message);
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.hospital.Hospital#autoFillHospital()
	 */
	@Override
	public void autoFillHospital() {
	
		do {
			admitPatient();
		}
		while(freeBedsCheck());
	}
	
	
}