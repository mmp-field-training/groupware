package com.mmp.groupware.domain.commute;

import com.mmp.groupware.web.commute.commuteDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Mapper
@Repository
public interface commuteMapper {
    // 출퇴근 기록 목록 조회
    List<commuteDto> getComList(Map<String, Object> search);

    // 일간 출퇴근 조회
    List<commuteDto> getDailyComDetail(LocalDate date);

    //주간 출퇴근 기록 목록 조회
    List<commuteDto> getWeekComList(long atteNo);

    //월간 출퇴근 조회
    List<commuteDto> getMonthlyList(LocalDate date);

    // 개별 출퇴근 현황 조회
    List<commuteDto> getPersonalComList(long stfNo);
}
