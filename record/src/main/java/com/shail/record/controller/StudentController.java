  package com.shail.record.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shail.record.model.StudentModel;
import com.shail.record.service.StudentService;

@CrossOrigin
@RestController
@RequestMapping("student")
public class StudentController {
	
	@Autowired
	private StudentService service;

	@Autowired
	private Environment environment;

	private Logger log = LoggerFactory.getLogger(StudentController.class);
	
	@RequestMapping(value="/register", method = RequestMethod.POST)
	public ResponseEntity<?> registerStudent(@RequestBody StudentModel studentModel) throws Exception{
		log.info("Inside Register Student Controller ",studentModel.getEmail());
		String email = service.registerStudent(studentModel);
		String msg = environment.getProperty("StudentController.REGISTERED")+" workEmail = "+email;
		
		return new ResponseEntity<String>(msg, HttpStatus.OK);

	}
	
	@RequestMapping(value="/getall", method = RequestMethod.GET) 
	public ResponseEntity<List<StudentModel>> getStudentRecords() throws Exception{
		log.info("Inside getStudentRecords Controller");
		List<StudentModel> list = service.getStudentRecords();
		return new ResponseEntity<List<StudentModel>>(list, HttpStatus.OK); 
	}

	
	@RequestMapping(value="/", method=RequestMethod.DELETE) 
	public void deleteStudent(@RequestParam String email ) throws Exception {
		log.info("Inside deleteStudent Controller: {} ",email);
		service.deleteStudent(email); 
	}

	@RequestMapping(value="/update",method =RequestMethod.PATCH)
	public ResponseEntity<String> updateStudentRecord(@RequestBody StudentModel model) throws Exception{
		log.info("Inside updateStudentRecord Controller ",model.getEmail());
		Integer id = service.updateStudentRecord(model);
		String msg = id+" "+environment.getProperty("StudentController.UPDATED");
		return new ResponseEntity<String>(msg,HttpStatus.OK);
	}

		 
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public ResponseEntity<?> validateLogin(@RequestBody StudentModel student) throws Exception{
		log.info("Inside Validate login Controller: {} ",student.getEmail());
		String str = service.validateLogin(student);
		return new ResponseEntity<String>(str,HttpStatus.OK);
	}

	
}

