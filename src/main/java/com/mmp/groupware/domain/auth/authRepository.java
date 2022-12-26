package com.mmp.groupware.domain.auth;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface authRepository extends JpaRepository<auth, Long>{

	@Query(value="select * from auth where delete_dt is null", nativeQuery = true)
	List<auth> findAllWhereDeleteDtIsNull();
	
}
