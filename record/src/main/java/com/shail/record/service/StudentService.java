package com.shail.record.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.shail.record.entity.StudentAuthEntity;
import com.shail.record.entity.StudentEntity;
import com.shail.record.exceptionHandler.StudentException;
import com.shail.record.model.StudentModel;
import com.shail.record.repository.StudentAuthRepository;
import com.shail.record.repository.StudentRepository;
import com.shail.record.security.helper.TokenHelper;

@Service("studentService")
@Transactional
public class StudentService {
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private StudentAuthRepository studentAuthRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private TokenHelper tokenHelper;
	
	private Logger log = LoggerFactory.getLogger(StudentService.class);
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String registerStudent(StudentModel model) throws Exception{
		log.info("Register new student with email: {}",model.getEmail()); 

		String emailId = model.getEmail().toLowerCase();
		StudentEntity doc = studentRepository.findByEmail(emailId);
		if(doc!=null)
			throw new Exception("StudentService.EMAIL_ALREADY_REGISTERED");

		
		StudentEntity entity = studentModelToEntity(model);

		StudentAuthEntity studentAuth = new StudentAuthEntity();

		studentAuth.setUserName(emailId);
		studentAuth.setPassword(passwordEncoder.encode(model.getPassword()));
		studentAuth.setRole("STUDENT");

		studentAuthRepository.save(studentAuth);

		StudentEntity ent = studentRepository.save(entity);
		log.info("Student Registered with id: {} & email: {}",ent.getStudentId(), ent.getEmail());
		return  ent.getEmail();
	}

	public StudentModel findByEmailId(String email) throws Exception{
		log.info("Get Student record with email: {} ",email);
		String emailId = email.toLowerCase();
		StudentEntity doc = studentRepository.findByEmail(emailId);
		if(doc != null) {

			return studentEntityToModel(doc);
		}
		else
			throw new StudentException("StudentService.NO_RECORD");
	}

	public List<StudentModel> getStudentRecords() throws Exception{
		log.info("Get All student records");
		List<StudentEntity> entityList = studentRepository.findAll();
		List<StudentModel> modelList = new ArrayList<>();
		if(!entityList.isEmpty()) {
			for (StudentEntity entity : entityList) {
				StudentModel model = studentEntityToModel(entity);
				modelList.add(model);
			}
			return modelList;
		}
		else
			throw new StudentException("StudentService.NO_RECORD_FOUND");
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Integer updateStudentRecord(StudentModel model) throws Exception{
		log.info("Update Student Record with email: {}",model.getEmail());
		String email = model.getEmail();
		
		StudentEntity entity = studentRepository.findByEmail(email);
		StudentAuthEntity authEntity = studentAuthRepository.findByUserName(email);
		
		if(Objects.isNull(entity))
			throw new StudentException("WRONG_EMAIL");
		String regex = "[\\s]";
		if(model.getStudentName() ==null || model.getStudentName().isEmpty() || model.getStudentName().matches(regex)) {}
		else
			entity.setStudentName(model.getStudentName());
		if(model.getPassword() == null || model.getPassword().isEmpty() || model.getPassword().matches(regex)) {}
		else {
			authEntity.setPassword(passwordEncoder.encode(model.getPassword()));
		}
		
		studentAuthRepository.save(authEntity);
		studentRepository.save(entity);
		Integer id = entity.getStudentId();
		return id;


	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteStudent(String email) throws Exception {
		log.info("Delete student record with email: {}",email);
		if(!Objects.isNull(studentRepository.findByEmail(email))) {
			studentRepository.deleteByEmail(email);
			studentAuthRepository.deleteByUsername(email);
			log.info("Student deleted with email ",email);
		}
		else {
			log.info("Student doesn't exist with this email: {} ",email);
			throw new StudentException("StudentService.WRONG_EMAIL");
		}

	}



	private StudentModel studentEntityToModel(StudentEntity student) {
		log.info("Inside studentEntityToModel Service ",student.getEmail());
		StudentModel model = new StudentModel();
		model.setStudentId(student.getStudentId());
		model.setEmail(student.getEmail());
		model.setStudentName(student.getStudentName());
		model.setStandard(student.getStandard());

		return model;
	}

	private StudentEntity studentModelToEntity(StudentModel model) {
		log.info("Inside studentModelToEntity ",model.getEmail());
		StudentEntity entity = new StudentEntity();
		entity.setEmail(model.getEmail());
		entity.setStudentName(model.getStudentName());
		entity.setStandard(model.getStandard());
		entity.setStudentId(model.getStudentId());

		return entity;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String validateLogin(StudentModel student) throws Exception {
		log.info("Inside validate login Service");
		StudentAuthEntity authEntity = studentAuthRepository.findByUserName(student.getEmail());
		log.info("UserName: {}",authEntity.getUserName());
		if(!Objects.isNull(authEntity)) {
			if(passwordEncoder.matches(student.getPassword(),authEntity.getPassword())) {
				return tokenHelper.generateToken(authEntity.getUserName(), student.getPassword(), AuthorityUtils.createAuthorityList(authEntity.getRole()));
			}
			else {
				log.info("Wrong Password");
				throw new Exception("Wrong Password");
			}
		}
		else {
			log.info("Invalid Login Email");
			throw new Exception("Invalid Email");
		}
			
	}
	
	
	

}
