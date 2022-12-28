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

<<<<<<< HEAD
    //월간 출퇴근 조회
    public List<commuteDto> getMonthlyList(LocalDate date) {

        return comMapper.getMonthlyList(date);
    }
=======

    // 출근 (등록)


    // 퇴근 (등록)
>>>>>>> acb2e4a022ee5ac9d78fde5b3e6051d52373a6e1



}