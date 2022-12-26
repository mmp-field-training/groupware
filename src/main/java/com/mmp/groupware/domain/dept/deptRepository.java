package com.mmp.groupware.domain.dept;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface deptRepository extends JpaRepository<dept, Long>{

	@Query(value="select * from dept where delete_dt is null", nativeQuery = true)
	List<dept> findAllWhereDeleteDtIsNull();
	
}
