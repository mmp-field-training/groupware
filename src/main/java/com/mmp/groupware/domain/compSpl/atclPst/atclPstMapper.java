package com.mmp.groupware.domain.compSpl.atclPst;

import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.compSpl.dto.atclPstDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface atclPstMapper {

    // 물품위치 수 조회
    int getAtclPstCnt(Map<String, Object> search);

    // 물품위치 목록 조회
    List<atclPstDto> getAtclPstList(Map<String, Object> search);

    // 물품위치 상세 조회
    atclPstDto getAtclPstDetail(long atclDivNo);

}
