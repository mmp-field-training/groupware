package com.mmp.groupware.domain.attachment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.business.dto.bsnFilesDto;
import com.mmp.groupware.web.staff.dto.staffDto;

@Mapper
@Repository
public interface attachMapper {

	// 업무 첨부파일 조회
	List<bsnFilesDto> getBsnFiles(Long bsnNo);
	
}
