package com.mmp.groupware.domain.dept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.dept.deptDto;

@Mapper
@Repository
public interface deptMapper {

	// 부서 수 조회
	int getDeptCnt(Map<String, Object> search);

	// 부서 목록 조회
	List<deptDto> getDeptList(Map<String, Object> search);
}
