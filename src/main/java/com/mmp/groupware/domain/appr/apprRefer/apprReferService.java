package com.mmp.groupware.domain.appr.apprRefer;

import com.mmp.groupware.web.appr.dto.apprReferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class apprReferService {

    private final apprReferMapper apprRefMapper;

    // 참조자 가져오기
    public List<apprReferDto> getApprReferList(long apprNo){
        return apprRefMapper.getApprReferList(apprNo);
    }

    // 참조자 등록하기

}
