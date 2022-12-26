package com.mmp.groupware.domain.staff;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.staff.dto.loginDto;
import com.mmp.groupware.web.staff.dto.staffDto;

@Mapper
@Repository
public interface staffMapper {

	// 사원 계정 존재 여부 파악 
	int countById(loginDto loginForm);

	// 사원 정보 조회
	staffDto findByLoginForm(loginDto loginForm);

	// 사원 수 조회
	int getStfCnt(Map<String, Object> search);

	// 사원 목록 조회
	List<staffDto> getStfList(Map<String, Object> search);
	
	// 사원 상세 조회
	staffDto getStfDetail(long stfNo);
	
}
