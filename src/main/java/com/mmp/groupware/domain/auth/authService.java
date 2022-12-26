package com.mmp.groupware.domain.auth;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class authService {

	private final authRepository authRepo;
	
	// 권한 전체 조회
	public List<auth> findAll(){
		List<auth> authList = null;
		try {
			authList = authRepo.findAllWhereDeleteDtIsNull();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return authList;
	}
	
}
