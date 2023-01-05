package com.mmp.groupware.domain.plcMnyInfo.pmi;

import com.mmp.groupware.web.pmi.dto.pmiDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface pmiMapper {

    // 공금정보 수 조회
    int getPmiCnt(Map<String, Object> search);

    // 공금정보 목록 조회
    List<pmiDto> getPmiList(Map<String, Object> search);

    // 공금정보 상세 조회
    pmiDto getPmiDetail(long pmiNo);

}
