package com.mmp.groupware.domain.commute;

import com.mmp.groupware.web.commute.commuteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@Service
@RequiredArgsConstructor
public class commuteService {
    private final commuteMapper comMapper;

    public List<commuteDto> getComList(Map<String, Object> search) {
        return comMapper.getComList(search);
    }

    public List<commuteDto> getDailyComDetail(LocalDate date) {
        return comMapper.getDailyComDetail(date);
    }

    public List<commuteDto> getWeekComList(long atteNo) {

        return comMapper.getWeekComList(atteNo);
    }

    //월간 출퇴근 조회
    public List<commuteDto> getMonthlyList(LocalDate date) {
        return comMapper.getMonthlyList(date);
    }

    public List<commuteDto> getPersonalComList(long stfNo) {
        return comMapper.getPersonalComList(stfNo);
    }


    // 출근 (등록)


    // 퇴근 (등록)


}