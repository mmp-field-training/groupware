package com.mmp.groupware.domain.calendar.calRefer;

import com.mmp.groupware.web.calendar.dto.calReferDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface calReferMapper {

    List<calReferDto> getCalReferList(long calNo);

}
