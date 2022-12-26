package com.mmp.groupware.domain.rnk;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mmp.groupware.domain.dept.dept;
import com.mmp.groupware.web.rnk.rnkDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class rnkService {

	private final rnkRepository rnkRepo;
	private final rnkMapper rnkMapper;
	
	// 직급 전체 조회
	public List<rnk> findAll(){
		List<rnk> rnkList = null;
		try {
			rnkList = rnkRepo.findAllWhereDeleteDtIsNull();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return rnkList;
	}
	
	// 직급 수 조회
	public int getRnkCnt(Map<String, Object> search) {
		return rnkMapper.getRnkCnt(search);
	}
	
	// 직급 목록 조회
	public List<rnkDto> getRnkList(Map<String, Object> search){
		return rnkMapper.getRnkList(search);
	}
	
	// 직급 삭제
	public Map<String, Object> deleteRnk(Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Optional<rnk> optRnk = rnkRepo.findById(Long.parseLong(param.get("rnkNo").toString()));
			
			if(optRnk.isPresent()) {
				rnk rnk = optRnk.get();
				rnk.setDeleteDt(LocalDateTime.now());
				rnkRepo.save(rnk);
				
				result.put("code", "success");
				result.put("url", "/rnk/list");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code", "fail");
			result.put("msg", "직급 정보 삭제에 실패했습니다.");
		}
		return result;
	}
	
	// 직급 수정
	public Map<String, Object> editRnk(Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Optional<rnk> optRnk = rnkRepo.findById(Long.parseLong(param.get("rnkNo").toString()));
			
			if(optRnk.isPresent()) {
				rnk rnk = optRnk.get();
				rnk.setRnkNm(param.get("inputDept").toString());
				rnkRepo.save(rnk);
				
				result.put("code", "success");
				result.put("url", "/rnk/list");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code", "fail");
			result.put("msg", "직급 정보 수정에 실패했습니다.");
		}
		return result;
	}
	
	// 직급등록
	public Map<String, Object> addRnk(Map<String, Object> addForm, HttpServletRequest request) 
			throws JsonMappingException, JsonProcessingException{
		Map<String, Object> result = new HashMap<String, Object>();
		
		try {
			
			rnk rk = rnk.builder()
					.rnkNm(addForm.get("rnkNm").toString())
					.createDt(LocalDateTime.now())
					.build();
			
			rnkRepo.save(rk);
			result.put("code", "success");
			result.put("url", "/rnk/list");
		}catch(Exception e) {
			result.put("code", "fail");
			result.put("msg", "직급 정보 등록에 실패하였습니다.");
			
		}
		
		return result;
	}
		
}
