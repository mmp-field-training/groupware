package com.mmp.groupware.domain.attachment;

import java.util.List;
import java.util.Map;

import com.mmp.groupware.web.appr.dto.apprDivFilesDto;
import com.mmp.groupware.web.appr.dto.apprFilesDto;
import com.mmp.groupware.web.board.dto.brdFilesDto;
import com.mmp.groupware.web.dailyWork.dto.dayWorkFilesDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.business.dto.bsnFilesDto;

@Mapper
@Repository
public interface attachMapper {

	// 업무 첨부파일 조회
	List<bsnFilesDto> getBsnFiles(Long bsnNo);

	// 게시판 첨부파일 조회
	List<brdFilesDto> getBrdFiles(Long brdNo);

	/*
	// 공금정보 첨부파일 조회
	List<pmiFilesDto> getPmiFiles(Long pmiNo);

	 */

	// 기안 첨부파일 조회
	List<apprFilesDto> getApprFiles(Long apprNo);

	// 전자결재 첨부파일 조회
	List<apprDivFilesDto> getApprDivFiles(Long apprDivNo);

	List<dayWorkFilesDto> getDayWorkFiles(Long dayWorkNo);
	
}
