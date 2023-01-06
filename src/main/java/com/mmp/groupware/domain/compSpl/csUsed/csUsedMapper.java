package com.mmp.groupware.domain.compSpl.csUsed;

import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.compSpl.dto.csUsedDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface csUsedMapper {

    // 물품 사용이력 수 조회
    int getCsUsedCnt(Map<String, Object> search);

    // 물품 사용이력 목록 조회
    List<csUsedDto> getCsUsedList(Map<String, Object> search);

    // 물품 사용이력 상세 조회
    csUsedDto getCsUsedDetail(long csUsedNo);

}
