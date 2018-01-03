package com.id.hl7sim;


import java.time.LocalDate;
import java.time.LocalDateTime;


public class Patient {

	private int id;
	private int caseId;
	private String lastname;
	private String firstname;
	private String gender;
	private LocalDate birthday;
	private String status;
	private String department; 
	private String ward;
	private String priorDepartment;
	private String priorWard;
	private LocalDateTime admissionDateTime;
	private LocalDateTime dischargeDateTime;
	private int lengthOfStay;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	} 

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	
	public String getPriorDepartment() {
		return priorDepartment;
	}

	public void setPriorDepartment(String priorDepartment) {
		this.priorDepartment = priorDepartment;
	}

	public String getPriorWard() {
		return priorWard;
	}

	public void setPriorWard(String priorWard) {
		this.priorWard = priorWard;
	}

	public LocalDateTime getAdmissionDateTime() {
		return admissionDateTime;
	}

	public void setAdmissionDateTime(LocalDateTime admissionDateTime) {
		this.admissionDateTime = admissionDateTime;
	}

	public LocalDateTime getDischargeDateTime() {
		return dischargeDateTime;
	}

	public void setDischargeDateTime(LocalDateTime dischargeDateTime) {
		this.dischargeDateTime = dischargeDateTime;
	}

	public int getLengthOfStay() {
		return lengthOfStay;
	}

	public void setLengthOfStay(int lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	public static class Builder {
		
//		private static final AtomicInteger count = new AtomicInteger(0);
//		private static final AtomicInteger countTwo = new AtomicInteger(0);
		private int id;
		private int caseId;
		private String lastname;
		private String firstname;
		private String gender;
		private LocalDate birthday;
		private String status;
		private String department;
		private String ward;
		private String priorDepartment;
		private String priorWard;
		private LocalDateTime admissionDateTime;
		private LocalDateTime dischargeDateTime;
		private int lengthOfStay;

		public Builder id(int id) {
			this.id = id;
			return this;
		}

		public Builder caseId(int caseId) {
			this.caseId = caseId;
			return this;
		}

		public Builder lastname(String lastname) {
			this.lastname = lastname;
			return this;
		}

		public Builder firstname(String firstname) {
			this.firstname = firstname;
			return this;
		}

		public Builder gender(String gender) {
			this.gender = gender;
			return this;
		}

		public Builder birthday(LocalDate birthday) {
			this.birthday = birthday;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public Builder ward(String ward) {
			this.ward = ward;
			return this;
		}

		public Builder department(String department) {
			this.department = department;
			return this;
		}
		
		public Builder priorWard(String priorWard) {
			this.priorDepartment = priorWard;
			return this;
		}
		
		public Builder priorDepartment(String priorDepartment) {
			this.priorDepartment = priorDepartment;
			return this;
		}
		
		public Builder admissionDateTime(LocalDateTime admissionDateTime) {
			this.admissionDateTime = admissionDateTime;
			return this;
		}

		public Builder dischargeDateTime(LocalDateTime dischargeDateTime) {
			this.dischargeDateTime = dischargeDateTime;
			return this;
		}

		public Builder lengthOfStay(int lengthOfStay) {
			this.lengthOfStay = lengthOfStay;
			return this;
		}

		public Patient build() {
			return new Patient(this);
		}

	}

	private Patient(Builder builder) {
		id = builder.id;
		caseId = builder.caseId;
		lastname = builder.lastname;
		firstname = builder.firstname;
		gender = builder.gender;
		birthday = builder.birthday;
		status = builder.status;
		ward = builder.ward;
		department = builder.department;
		priorDepartment = builder.priorDepartment;
		priorWard = builder.priorWard;
		admissionDateTime = builder.admissionDateTime;
		dischargeDateTime = builder.dischargeDateTime;
		lengthOfStay = builder.lengthOfStay;
	}
	
	public static Patient newInstance(Patient patient) {
		return new Patient.Builder()
				.id(patient.getId())
				.caseId(patient.getCaseId())
				.lastname(patient.getLastname())
				.firstname(patient.getFirstname())
				.gender(patient.getGender())
				.birthday(patient.getBirthday())
				.status(patient.getStatus())
				.ward(patient.getWard())
				.department(patient.getDepartment())
				.priorWard(patient.priorWard)
				.priorDepartment(patient.priorDepartment)
				.admissionDateTime(patient.getAdmissionDateTime())
				.dischargeDateTime(patient.getDischargeDateTime())
				.lengthOfStay(patient.getLengthOfStay())
				.build();
	}
	
	public boolean isValid() {
		if(this.getLastname() == null || this.getFirstname() == null || this.getGender() == null || this.getBirthday() == null ||
		   this.getLastname() == ""   || this.getFirstname() == ""   || this.getGender() == "") {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "| ID: " + this.id + " |CASE-ID: " + this.caseId + " | NAME: " + this.firstname + " " + this.lastname
				+ " | BIRTHDAY: " + this.birthday + " | GENDER: " + this.gender + "\n" + "ADMISSION: "
				+ this.admissionDateTime + " | DISCHARGE: " + this.dischargeDateTime + " |DEPARTMENT: "
				+ this.department + " | WARD: " + this.ward + " | Status: " + this.status + " |Length of Stay: "
				+ this.lengthOfStay;
	}
	
}