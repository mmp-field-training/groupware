package com.mmp.groupware.domain.appr.apprDiv;

import com.mmp.groupware.web.appr.dto.apprDivDto;
import com.mmp.groupware.web.business.dto.bsnDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface apprDivMapper {

    // 전자결재 구분 수 조회
    int getApprDivCnt(Map<String, Object> search);

    // 전자결재 구분 목록 조회
    List<apprDivDto> getApprDivList(Map<String, Object> search);

    // 전자결재 구분 상세 조회
    apprDivDto getApprDivDetail(long apprDivNo);

}
