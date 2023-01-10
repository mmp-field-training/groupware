package com.mmp.groupware.domain.compSpl.cs;

import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.compSpl.dto.csDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface csMapper {

    // 물품 수 조회
    int getCsCnt(Map<String, Object> search);

    // 물품 목록 조회
    List<csDto> getCsList(Map<String, Object> search);

    // 물품 상세 조회
    csDto getCsDetail(long csNo);

}
