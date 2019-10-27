package com.devrauxx.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.devrauxx.record.entity.StudentAuthEntity;

public interface StudentAuthRepository extends JpaRepository<StudentAuthEntity, String>{

	StudentAuthEntity findByUserName(String username);

	@Modifying
	@Query("DELETE FROM StudentAuthEntity s WHERE s.userName = ?1")
	public void deleteByUsername(String email);

}
