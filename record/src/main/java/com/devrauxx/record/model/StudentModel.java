package com.devrauxx.record.model;

public class StudentModel {

	private Integer studentId;
	
	private String studentName;
	

	private String email;
	
	private String password;
	
	private Integer standard;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public Integer getStandard() {
		return standard;
	}
	public void setStandard(Integer standard) {
		this.standard = standard;
	}
}
