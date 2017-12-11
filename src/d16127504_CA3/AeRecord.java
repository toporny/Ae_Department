package d16127504_CA3;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import d16127504_CA3.Config.Constants;

public class AeRecord {
	
	private static int LIMIT_BIG = Constants.Limits.LIMIT_BIG;
	private static int LIMIT_SMALL = Constants.Limits.LIMIT_BIG;
	
	private String mySubStr(String s, int cut) {
		if (s == null) return "";
		return s.substring(0, Math.min(s.length(), cut));		
	}
	
	// STAGE1
	private String timeStamp;
	private String Name;
	private String Surname;
	private String PPS;
	private String DOB;
	private String Address1;
	private String Address2;
	private String Zipcode;
	private String Phone;
	private int Priority;
	private String BriefSummaryOfCondition;

	// STAGE2
	private String PatientsConditionAndVitalSigns;
	
	// STAGE3
	private String SummaryOfTheTreatment;
	

	// ----- SETTERS AND GETTERS -----

	// name
	public void setName(String Name) {
		this.Name  = mySubStr(Name, LIMIT_BIG);
	}
	public String getName() {
		return this.Name;
	}

	
	// surname
	public void setSurname(String Surname) {
		this.Surname = mySubStr(Surname, LIMIT_BIG);
	}
	public String getSurname() {
		return this.Surname;
	}

	
	// PPS
	public void setPPS(String PPS) {
		this.PPS = mySubStr(PPS, LIMIT_SMALL);
	}
	public String getPPS() {
		return this.PPS;
	}

	
	// date of birth
	public void setDOB(String DOB) {
		this.DOB = mySubStr(DOB, LIMIT_BIG);
	}
	public String getDOB() {
		return this.DOB;
	}

	
	// address 1
	public void setAddress1(String Address1) {
		this.Address1 = mySubStr(Address1, LIMIT_BIG);
	}
	public String getAddress1() {
		return this.Address1;
	}

	
	// address 2
	public void setAddress2(String Address2) {
		this.Address2 = mySubStr(Address2, LIMIT_BIG);
	}
	public String getAddress2() {
		return this.Address2;
	}

	
	// zipcode
	public void setZipcode(String Zipcode) {
		this.Zipcode = mySubStr(Zipcode, LIMIT_SMALL);
	}
	public String getZipcode() {
		return this.Zipcode;
	}

	
	// phone
	public void setPhone(String Phone) {
		this.Phone = mySubStr(Phone, LIMIT_SMALL);
	}
	public String getPhone() {
		return this.Phone;
	}

	// priority
	public void setPriority(int Priority) {
		this.Priority = Priority;
	}
	public int getPriority() {
		return this.Priority;
	}

	
	// step1 - summary of condition
	public void setBriefSummaryOfCondition(String BriefSummaryOfCondition) {
		this.BriefSummaryOfCondition = mySubStr(BriefSummaryOfCondition, LIMIT_BIG);
	}
	public String getBriefSummaryOfCondition() {
		return this.BriefSummaryOfCondition;
	}
	
	
	// step2 - patients condition and vitalSigns
	public void setPatientsConditionAndVitalSigns(String PatientsConditionAndVitalSigns) {
		this.PatientsConditionAndVitalSigns = mySubStr(PatientsConditionAndVitalSigns, LIMIT_BIG);
	}
	public String getPatientsConditionAndVitalSigns() {
		return this.PatientsConditionAndVitalSigns;
	}
	
	
	// step3 - summary of the treatment
	public void setSummaryOfTheTreatment(String SummaryOfTheTreatment) {
		this.SummaryOfTheTreatment = mySubStr(SummaryOfTheTreatment, LIMIT_BIG);
	}
	public String getSummaryOfTheTreatment() {
		return this.SummaryOfTheTreatment;
	}
 
	
	// timestamp
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = mySubStr(timeStamp, 16);
	}
	public String getTimeStamp() {
		return this.timeStamp;
	}
 	
	
	
	// first constructor (used when DDL is feeded by SQLite)
	public AeRecord (int Pr, String timeStamp, String Name, String Surname, String PPS, String DOB, String Phone, String Address1, String Address2, String Zipcode, String BriefSummaryOfCondition) {
		this.timeStamp = timeStamp;
		this.Name = Name;
		this.Surname = Surname;
		this.PPS = PPS;
		this.DOB = DOB;
		this.Phone = Phone;
		this.Address1 = Address1;
		this.Address2 = Address2;
		this.Zipcode = Zipcode;
		this.BriefSummaryOfCondition = BriefSummaryOfCondition;
		this.Priority = Pr;
	}

	
	// main constructor
	public AeRecord () {
		this.timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(Calendar.getInstance().getTime());
	}

	
	
	// for debug and testing purposes
	public String toString() { 
		String s = "\n";
		s += " timeStamp: " + this.timeStamp;
		s += " Name: " + this.Name;
		s += " Surname: " + this.Surname;
		s += " PPS: " + this.PPS;
		s += " Address1: " + this.Address1;
		s += " Address2: " + this.Address2;
		s += " Phone: " + this.Phone;
		s += " Zipcode: " + this.Zipcode;
		s += " BriefSummaryOfCondition: " + this.BriefSummaryOfCondition;
		s += " Priority: " + this.Priority;
	    return s;
	} 
}
