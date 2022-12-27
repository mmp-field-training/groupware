package com.mmp.groupware.domain.calendar.calRefer;

import com.mmp.groupware.web.calendar.dto.calReferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class calReferService {

    private final calReferMapper calReferMapper;

    //참조자 가져오기
    public List<calReferDto> getCalReferList(long calNo) {
        return calReferMapper.getCalReferList(calNo);
    }

    //참조자 등록하기

}
