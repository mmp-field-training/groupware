package com.mmp.groupware.domain.business.bsn;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.staff.dto.staffDto;

@Mapper
@Repository
public interface bsnMapper {

	// 업무 수 조회
	int getBsnCnt(Map<String, Object> search);

	// 업무 목록 조회
	List<bsnDto> getBsnList(Map<String, Object> search);
	
	// 업무 상세 조회
	bsnDto getBsnDetail(long bsnNo);
}
