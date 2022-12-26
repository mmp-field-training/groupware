package com.mmp.groupware.domain.business.bsnRefer;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.business.dto.bsnReferDto;

@Mapper
@Repository
public interface bsnReferMapper {

	List<bsnReferDto> getBsnReferList(long bsnNo);
	
}
