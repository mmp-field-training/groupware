package com.mmp.groupware.domain.business.bsnComm;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.mmp.groupware.web.business.dto.bsnCommDto;

@Mapper
@Repository
public interface bsnCommMapper{

	public List<bsnCommDto> getBsnCommList(long bsnNo);
	
}
