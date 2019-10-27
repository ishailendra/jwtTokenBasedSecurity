package com.devrauxx.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.devrauxx.record.entity.StudentEntity;

@Repository("studentRepository")
public interface StudentRepository extends JpaRepository<StudentEntity, Integer>{

	public StudentEntity findByEmail(String emailId);
	
	public StudentEntity findByStudentId(Integer id);

//	@Query("select max(studentId) from StudentEntity s")
//	Integer getMaxId();
	
	@Modifying
	@Query("DELETE FROM StudentEntity s WHERE s.email = ?1")
	public void deleteByEmail(String email);

	@Query("SELECT MAX(s.studentId) FROM StudentEntity s")
	public Integer getMaxId();

}
