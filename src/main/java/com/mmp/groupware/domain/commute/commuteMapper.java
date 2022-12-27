package com.mmp.groupware.domain.commute;

import com.mmp.groupware.web.commute.commuteDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Mapper
@Repository
public interface commuteMapper {
    // 출퇴근 기록 목록 조회
    List<commuteDto> getComList(Map<String, Object> search);

    //주간 출퇴근 기록 목록 조회
    List<commuteDto> getWeekComList(long atteNo);

}