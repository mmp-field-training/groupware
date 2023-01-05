package com.mmp.groupware.domain.dailyWork.dayWork;

import com.mmp.groupware.web.dailyWork.dto.dayWorkDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface dayWorkMapper {

    // 일일보고 수 조회
    int getDayWorkCnt(Map<String, Object> search);

    // 일일보고 목록 조회
    List<dayWorkDto> getDayWorkList(Map<String, Object> search);

    // 일일보고 상세 조회
    dayWorkDto getDayWorkDetail(long dayWorkNo);

}
