package com.mmp.groupware.domain.business.bsnRefer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mmp.groupware.web.business.dto.bsnReferDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class bsnReferService {

	private final bsnReferMapper bsnRefMapper;
	
	// 참조자 가져오기
	public List<bsnReferDto> getBsnReferList(long bsnNo){
		return bsnRefMapper.getBsnReferList(bsnNo);
	}
	
	// 참조자 등록하기
	
}
