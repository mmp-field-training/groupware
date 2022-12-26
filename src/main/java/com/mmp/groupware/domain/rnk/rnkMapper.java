package com.mmp.groupware.domain.rnk;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.rnk.rnkDto;

@Mapper
@Repository
public interface rnkMapper {

	// 부서 수 조회
	int getRnkCnt(Map<String, Object> search);

	// 부서 목록 조회
	List<rnkDto> getRnkList(Map<String, Object> search);
}
