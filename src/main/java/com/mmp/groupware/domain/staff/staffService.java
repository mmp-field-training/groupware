package com.mmp.groupware.domain.staff;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mmp.groupware.util.cryptoUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.staff.dto.loginDto;
import com.mmp.groupware.web.staff.dto.staffAddDto;
import com.mmp.groupware.web.staff.dto.staffDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class staffService {

	private final cryptoUtil crypto;
	private final sessionUtil sess;
	
	private final staffMapper stfMapper;
	private final staffRepository stfRepo;
	
	// 로그인 실행 메소드
	public Map<String, Object> login(loginDto loginForm, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			// 회원 계정 존재 여부 확인
			int cnt = stfMapper.countById(loginForm);
			
			// 존재하지 않을 시
			if(cnt == 0) {
				result.put("code", "fail");
                result.put("message", "계정이 존재하지 않습니다.");
                return result;
			}
			
			// 회원정보 조회
			staffDto stfDto = stfMapper.findByLoginForm(loginForm);
			
			if(stfDto == null) {
				result.put("code", "fail");
                result.put("message", "아이디 혹은 비밀번호가 틀렸습니다.");
                return result;
			}
			
			// 비밀번호 일치 여부 확인
			if(!crypto.strMatch(stfDto.getStfPwd(), loginForm.getStfPwd())) {
				result.put("code", "fail");
                result.put("message", "아이디 혹은 비밀번호가 틀렸습니다.");
                return result;
			}
			// session
			Map<String, Object> sessionMap = new HashMap<String, Object>();
			sessionMap.put("stfNo", stfDto.getStfNo());
			sessionMap.put("stfId", stfDto.getStfId());
			sessionMap.put("stfNm", stfDto.getStfNm());
			sessionMap.put("deptNm", stfDto.getDeptNm());
			sessionMap.put("rnkNm", stfDto.getRnkNm());
			sessionMap.put("authNo", stfDto.getAuthNo()); 
			sessionMap.put("authNm", stfDto.getAuthNm());
			
			if(!sess.sessionSet(request, sessionMap)) {
				result.put("code", "fail");
				result.put("message", "세션 등록 실패");
                return result;
			}
			
			// success
			result.put("code", "success");
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			result.put("code", "fail");
		}
		return result;
	}
	
	// 사원 수 조회
	public int getStfCnt(Map<String, Object> search) {
		return stfMapper.getStfCnt(search);
	}
	
	// 사원 목록 조회
	public List<staffDto> getStfList(Map<String, Object> search){
		return stfMapper.getStfList(search);
	}
	
	// 사원 상세 조회 
	public staffDto getStfDetail(long stfNo) {
		staffDto stfDto = null;
		try {
			stfDto = stfMapper.getStfDetail(stfNo);
		}catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
		return stfDto;
	}
	
	// 사원 등록
	public Map<String, Object> addStaff(staffAddDto addForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException{
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			staff stf = staff.builder()
					.authNo(addForm.getAuthNo())
					.deptNo(addForm.getDeptNo())
					.rnkNo(addForm.getRnkNo())
					.stfId(addForm.getStfId())
					.stfPwd(crypto.strEncoder(addForm.getStfPwd()))
					.stfNm(addForm.getStfNm())
					.stfEmail(addForm.getStfEmail())
					.stfPhone(addForm.getStfPhone())
					.stfBirth(addForm.getStfBirth().atTime(0, 0, 0, 0))
					.stfGender(addForm.getStfGender())
					.stfAnnual(addForm.getStfAnnual())
					.stfHiredDt(addForm.getStfHiredDt().atTime(0, 0, 0, 0))
					.stfLeavedDt(null)
					.stfProfile(null) // 나중에 파일 업로드 하면서
					.createDt(LocalDateTime.now())
					.updateDt(null)
					.deleteDt(null)
					.build();
			
			Long stfNo = stfRepo.save(stf).getStfNo();
			
			result.put("code", "success");
			result.put("url", "/staff/detail?stfNo="+stfNo);
			
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code", "fail");
			result.put("msg", "사원 등록에 실패하였습니다.");
		}
		
		return result;
	}
	
	// 사원 삭제
	public Map<String, Object> deleteStaff(Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Optional<staff> optStf = stfRepo.findById(Long.parseLong(param.get("stfNo").toString()));
			
			if(optStf.isPresent()) {
				staff stf = optStf.get();
				stf.setDeleteDt(LocalDateTime.now());
				stfRepo.save(stf);
				
				result.put("code", "success");
				result.put("url", "/staff/list");
			}
			
		}catch(Exception e) {
			result.put("code", "fail");
		}
		return result;
	}
		
	// 사원정보 수정 실행
	public Map<String, Object> updateStaff(staffDto updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
		Map<String, Object> result = new HashMap<>();
		
		try {
			Optional<staff> optStf = stfRepo.findById(updateForm.getStfNo());
			// 존재하지 않을 시 
			if(optStf.isEmpty()) {
				result.put("code","fail");
				result.put("msg","회원 정보 수정에 실패하였습니다.");
			}
			
			staff stf = optStf.get();
			
			stf.setUpdateDt(LocalDateTime.now());
			stf.setStfNm(updateForm.getStfNm());
			stf.setAuthNo(updateForm.getAuthNo());
			stf.setDeptNo(updateForm.getDeptNo());
			stf.setRnkNo(updateForm.getRnkNo());
			stf.setStfPhone(updateForm.getStfPhone());
			stf.setStfEmail(updateForm.getStfEmail());
			stf.setStfBirth(updateForm.getStfBirth().atTime(0, 0, 0, 0));
			stf.setStfHiredDt(updateForm.getStfHiredDt().atTime(0, 0, 0, 0));
			if(updateForm.getStfLeavedDt() != null) {
				stf.setStfLeavedDt(updateForm.getStfLeavedDt().atTime(0, 0, 0, 0));
			}
			stf.setStfGender(updateForm.getStfGender());
			stf.setStfAnnual(updateForm.getStfAnnual());
			
			
			if(!updateForm.getStfPwd().equals("")) {
				stf.setStfPwd(crypto.strEncoder(updateForm.getStfPwd()));
			}
			
			stfRepo.save(stf);

			result.put("code", "success");
			result.put("url", "/staff/detail?stfNo=" + updateForm.getStfNo());
					
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code","fail");
			result.put("msg","회원정보 수정에 실패하였습니다.");
		}
		
		return result;
	}
		
}










































