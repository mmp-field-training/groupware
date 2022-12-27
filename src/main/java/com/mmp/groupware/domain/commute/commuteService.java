package com.mmp.groupware.domain.commute;

import com.mmp.groupware.web.commute.commuteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class commuteService {
    private final commuteMapper comMapper;
    public List<commuteDto> getWeekComList(long atteNo) {

        return comMapper.getWeekComList(atteNo);
    }
}