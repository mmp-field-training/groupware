package com.mmp.groupware.domain.appr;

import com.mmp.groupware.web.appr.dto.apprDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface apprMapper {

    // 기안 수 조회
    int getApprCnt(Map<String, Object> search);

    // 기안 목록 조회
    List<apprDto> getApprList(Map<String, Object> search);

    // 기안 상세 조회
    apprDto getApprDetail(long apprNo);

}
