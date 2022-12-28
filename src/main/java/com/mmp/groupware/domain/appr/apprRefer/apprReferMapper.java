package com.mmp.groupware.domain.appr.apprRefer;

import com.mmp.groupware.web.appr.dto.apprReferDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface apprReferMapper {

    List<apprReferDto> getApprReferList(long apprNo);

}
