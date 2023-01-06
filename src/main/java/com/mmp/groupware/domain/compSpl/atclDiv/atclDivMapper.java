package com.mmp.groupware.domain.compSpl.atclDiv;

import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.compSpl.dto.atclDivDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface atclDivMapper {

    // 물품그룹 수 조회
    int getAtclDivCnt(Map<String, Object> search);

    // 물품그룹 목록 조회
    List<atclDivDto> getAtclDivList(Map<String, Object> search);

    // 물품그룹 상세 조회
    atclDivDto getAtclDivDetail(long atclDivNo);

}
