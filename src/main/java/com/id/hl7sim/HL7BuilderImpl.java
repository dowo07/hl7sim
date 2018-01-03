package com.id.hl7sim;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HL7Exception;
import ca.uhn.hl7v2.HapiContext;
import ca.uhn.hl7v2.model.AbstractMessage;
import ca.uhn.hl7v2.model.DataTypeException;
import ca.uhn.hl7v2.model.v24.message.ADT_A01;
import ca.uhn.hl7v2.model.v24.message.ADT_A02;
import ca.uhn.hl7v2.model.v24.message.ADT_A03;
import ca.uhn.hl7v2.model.v24.segment.EVN;
import ca.uhn.hl7v2.model.v24.segment.MSH;
import ca.uhn.hl7v2.model.v24.segment.PID;
import ca.uhn.hl7v2.model.v24.segment.PV1;
import ca.uhn.hl7v2.parser.Parser;


public class HL7BuilderImpl implements HL7Builder {
	
	
	private static AtomicInteger messageControlId; 
	private List<String> allHL7s;

	
	public HL7BuilderImpl() {
		HL7BuilderImpl.messageControlId = new AtomicInteger(0);
		this.allHL7s = new ArrayList<String>();
	}
	
	
	public static int getMessageControlId() {
		return messageControlId.get(); 
	}

	public static void setMessageControlId(AtomicInteger messageControlId) {
		HL7BuilderImpl.messageControlId = messageControlId;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HL7Builder#getAllHL7s()
	 */
	@Override
	public List<String> getAllHL7s() {
		return allHL7s;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HL7Builder#setAllHL7s(java.util.List)
	 */
	@Override
	public void setAllHL7s(List<String> allHL7s) {
		this.allHL7s = allHL7s;
	}
	
	/* (non-Javadoc)
	 * @see com.id.hl7sim.HL7Builder#createMessage(com.id.hl7sim.Patient, com.id.hl7sim.Type, com.id.hl7sim.Format)
	 */
	@Override
	public String createMessage(Patient patient, Type type, Format format) {
		if (patient == null) {
			throw new IllegalArgumentException("No Patient");
		}
		if (type == null) {
			throw new IllegalArgumentException("No Messagetype");
		}
		if (format == null) {
			throw new IllegalArgumentException("Argument 'format' must not be null");
		}
		String message = "";
		if (format == Format.PIPE) {
			message = createPipeMessage(patient, type);
		} else if (format == Format.XML) {

		} else {
			throw new IllegalArgumentException("Format '" + format.name() + "' is not supported.");
		}
		return message;
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HL7Builder#createPipeMessage(com.id.hl7sim.Patient, com.id.hl7sim.Type)
	 */
	@Override
	public String createPipeMessage(Patient patient, Type type) {
		if(type == null) {
			throw new IllegalArgumentException("Type may not be null");
		}
		if(patient == null) {
			throw new IllegalArgumentException("No Patients");
		}
		switch (type) {
		case ADMISSION:
			return createPipeMessageAdmission(patient, type);
		case DISCHARGE:
			return createPipeMessageDischarge(patient, type);
		case TRANSFER:
			return createPipeMessageTransfer(patient, type);
		default:
			throw new IllegalArgumentException("Unknown Type, Builder not working with this Type");
		}
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HL7Builder#createPipeMessageAdmission(com.id.hl7sim.Patient, com.id.hl7sim.Type)
	 */
	@Override
	public String createPipeMessageAdmission(Patient patient, Type type) {
		ADT_A01 adt = new ADT_A01();
		patient.setAdmissionDateTime(LocalDateTime.now());
		try {
			adt.initQuickstart("ADT", "A01", "T");
			setMshSegment(adt.getMSH(), patient);
			setPidSegment(adt.getPID(), patient);
			setEvnSegment(adt.getEVN(), type);
			setPv1Segment(adt.getPV1(), patient);
			return parseMessage(adt);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Illegal Argument Exception", e);
		} catch (DataTypeException e) {
			throw new HL7BuilderException("Data type exception", e);
		} catch (HL7Exception e) {
			throw new HL7BuilderException("HL7 exception", e);
		} catch (IOException e) {
			throw new HL7BuilderException("IO exception", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HL7Builder#createPipeMessageTransfer(com.id.hl7sim.Patient, com.id.hl7sim.Type)
	 */
	@Override
	public String createPipeMessageTransfer(Patient patient, Type type) {
		ADT_A02 adt = new ADT_A02();
		try {
			adt.initQuickstart("ADT", "A02", "T");
			setMshSegment(adt.getMSH(), patient);
			setPidSegment(adt.getPID(), patient);
			setEvnSegment(adt.getEVN(), type);
			setPv1Segment(adt.getPV1(), patient);
			setPriorLocation(adt, patient);
			return parseMessage(adt);
		} catch (DataTypeException e) {
			throw new HL7BuilderException("Data Type Exception", e);
		} catch (HL7Exception e) {
			throw new HL7BuilderException("HL7 Exception", e);
		} catch (IOException e) {
			throw new HL7BuilderException("I/O Exception", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.id.hl7sim.HL7Builder#createPipeMessageDischarge(com.id.hl7sim.Patient, com.id.hl7sim.Type)
	 */
	@Override
	public String createPipeMessageDischarge(Patient patient, Type type) {
		ADT_A03 adt = new ADT_A03();
		patient.setDischargeDateTime(LocalDateTime.now());
		try {
			adt.initQuickstart("ADT", "A03", "T");
			setMshSegment(adt.getMSH(), patient);
			setPidSegment(adt.getPID(), patient);
			setEvnSegment(adt.getEVN(), type);
			setPv1Segment(adt.getPV1(), patient);
			return parseMessage(adt);
		} catch (DataTypeException e) {
			throw new HL7BuilderException("Data Type Exception", e);
		} catch (HL7Exception e) {
			throw new HL7BuilderException("HL7 Exception", e);
		} catch (IOException e) {
			throw new HL7BuilderException("I/O Exception", e);
		}
	}

	private void setMshSegment(MSH msh, Patient patient) throws DataTypeException {
		LocalDateTime now = LocalDateTime.now();
		msh.getSendingFacility().getNamespaceID().setValue("Testsender");
		msh.getSendingApplication().getNamespaceID().setValue("KIS");
		msh.getReceivingFacility().getNamespaceID().setValue("Testreceiver");
		msh.getReceivingApplication().getNamespaceID().setValue("HIS");
		msh.getDateTimeOfMessage().getTimeOfAnEvent().setDateMinutePrecision(now.getYear(), now.getMonthValue(),
				now.getDayOfMonth(), now.getHour(), now.getMinute());
		msh.getMessageControlID().setValue(String.valueOf(messageControlId.incrementAndGet()));
	}

	private void setEvnSegment(EVN evn, Type type) throws DataTypeException {
		LocalDateTime now = LocalDateTime.now();
		evn.getRecordedDateTime().getTimeOfAnEvent().setDateMinutePrecision(now.getYear(), now.getMonthValue(),
				now.getDayOfMonth(), now.getHour(), now.getMinute());
		if (type == Type.DISCHARGE) {
			evn.getEventTypeCode().setValue("A03");
		} else if (type == Type.TRANSFER) {
			evn.getEventTypeCode().setValue("A02");
		} else if (type == Type.ADMISSION) {
			evn.getEventTypeCode().setValue("A01");
		}
	}

	private void setPidSegment(PID pid, Patient patient) throws DataTypeException {
		pid.getPatientIdentifierList(0).getCx1_ID().setValue(String.valueOf(patient.getId()));
		pid.getAdministrativeSex().setValue(patient.getGender());
		pid.getPatientName(0).getGivenName().setValue(patient.getFirstname());
		pid.getPatientName(0).getFamilyName().getSurname().setValue(patient.getLastname());
		pid.getDateTimeOfBirth().getTimeOfAnEvent().setDatePrecision(patient.getBirthday().getYear(),
				patient.getBirthday().getMonthValue(), patient.getBirthday().getDayOfMonth());
	}

	private void setPv1Segment(PV1 pv1, Patient patient) throws DataTypeException {
		setPV1Time(pv1, patient);
		setPV1Location(pv1, patient);
	}

	private void setPV1Time(PV1 pv1, Patient patient) throws DataTypeException {
		if (patient.getAdmissionDateTime() != null) {
		pv1.getAdmitDateTime().getTimeOfAnEvent().setDateMinutePrecision(patient.getAdmissionDateTime().getYear(),
				patient.getAdmissionDateTime().getMonthValue(), patient.getAdmissionDateTime().getDayOfMonth(),
				patient.getAdmissionDateTime().getHour(), patient.getAdmissionDateTime().getMinute());
		}
		if (patient.getDischargeDateTime() != null) {
			pv1.getDischargeDateTime(0).getTimeOfAnEvent().setDateMinutePrecision(patient.getDischargeDateTime().getYear(),
					patient.getDischargeDateTime().getMonthValue(), patient.getDischargeDateTime().getDayOfMonth(),
					patient.getDischargeDateTime().getHour(), patient.getDischargeDateTime().getMinute());
		}
	}

	private void setPV1Location(PV1 pv1, Patient patient) throws DataTypeException {
		pv1.getServicingFacility().setValue(patient.getDepartment());
		pv1.getAssignedPatientLocation().getPl1_PointOfCare().setValue(patient.getWard());
		pv1.getAssignedPatientLocation().getPl4_Facility().getNamespaceID().setValue(patient.getDepartment());
		pv1.getPatientClass().setValue(patient.getStatus());
		pv1.getPv119_VisitNumber().getCx1_ID().setValue(String.valueOf(patient.getCaseId()));
	}

	private void setPriorLocation(ADT_A02 adt, Patient patient) throws DataTypeException {
		adt.getPV1().getPriorPatientLocation().getPl1_PointOfCare().setValue(patient.getPriorWard());
		adt.getPV1().getPriorPatientLocation().getPl4_Facility().getNamespaceID()
				.setValue(patient.getPriorDepartment());
	}

	private String parseMessage(AbstractMessage message) throws HL7Exception {
		try (HapiContext context = new DefaultHapiContext()) {
			context.getExecutorService();
			Parser parser = context.getPipeParser();
			final String result = parser.encode(message);
			allHL7s.add(result);
			return result;
		} catch (IOException e) {
			throw new HL7BuilderException("Error on parsing message.", e);
		}
	}

	
}