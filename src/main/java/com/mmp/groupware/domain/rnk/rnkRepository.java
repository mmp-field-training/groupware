package com.mmp.groupware.domain.rnk;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface rnkRepository extends JpaRepository<rnk, Long>{

	@Query(value="select * from rnk where delete_dt is null", nativeQuery = true)
	List<rnk> findAllWhereDeleteDtIsNull();
	
}
