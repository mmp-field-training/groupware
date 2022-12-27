package com.mmp.groupware.domain.calendar;

import com.mmp.groupware.web.calendar.dto.calendarDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface calendarMapper {

    // 일정 수 조회
    int getCalendarCnt(Map<String, Object> search);

    // 일정 목록 조회
    List<calendarDto> getCalendarList(Map<String, Object> search);

    //일정 상세 조회
    calendarDto getCalendarDetail(long calNo);

}
