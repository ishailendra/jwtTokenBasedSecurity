package com.devrauxx.record.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
//import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "student")
@GenericGenerator(name = "pkgen", strategy = "increment")
public class StudentEntity {

	@Id
	@Column(name = "student_id")
	@GeneratedValue( generator = "pkgen")
	private Integer studentId;
	
	@Column(name = "student_name")
	private String studentName;
	
	private String email;
	
	private Integer standard;
	
	
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String name) {
		this.studentName = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getStandard() {
		return standard;
	}
	public void setStandard(Integer standard) {
		this.standard = standard;
	}
	
	
	
}
