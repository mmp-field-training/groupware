package com.mmp.groupware.domain.dept;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mmp.groupware.web.dept.deptDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class deptService {

	private final deptRepository deptRepo;
	private final deptMapper deptMapper;
	
	// 부서 전체 조회
	public List<dept> findAll(){
		List<dept> deptList = null;
		try {
			deptList = deptRepo.findAllWhereDeleteDtIsNull();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return deptList;
	}

	// 부서 수 조회
	public int getDeptCnt(Map<String, Object> search) {
		return deptMapper.getDeptCnt(search);
	}
	
	// 부서 목록 조회
	public List<deptDto> getDeptList(Map<String, Object> search){
		return deptMapper.getDeptList(search);
	}
	
	// 부서 삭제
	public Map<String, Object> deleteDept(Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Optional<dept> optDept = deptRepo.findById(Long.parseLong(param.get("deptNo").toString()));
			
			if(optDept.isPresent()) {
				dept dept = optDept.get();
				dept.setDeleteDt(LocalDateTime.now());
				deptRepo.save(dept);
				
				result.put("code", "success");
				result.put("url", "/dept/list");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code", "fail");
			result.put("msg", "부서 정보 삭제에 실패했습니다.");
		}
		return result;
	}
	
	// 부서 수정
	public Map<String, Object> editDept(Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			Optional<dept> optDept = deptRepo.findById(Long.parseLong(param.get("deptNo").toString()));
			
			if(optDept.isPresent()) {
				dept dept = optDept.get();
				dept.setDeptNm(param.get("inputDept").toString());
				deptRepo.save(dept);
				
				result.put("code", "success");
				result.put("url", "/dept/list");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			result.put("code", "fail");
			result.put("msg", "부서 정보 수정에 실패했습니다.");
		}
		return result;
	}
	
	
	// 부서등록
	public Map<String, Object> addDept(Map<String, Object> addForm, HttpServletRequest request) 
			throws JsonMappingException, JsonProcessingException{
		Map<String, Object> result = new HashMap<String, Object>();
		
		//ObjectMapper mapper = new ObjectMapper();
		//Map<String,Object> addForm = mapper.readValue(addFormStr, Map.class);
		
		try {
			
			dept dpt = dept.builder()
					.deptNm(addForm.get("deptNm").toString())
					.createDt(LocalDateTime.now())
					.build();
			
			deptRepo.save(dpt);
			result.put("code", "success");
			result.put("url", "/dept/list");
		}catch(Exception e) {
			result.put("code", "fail");
			result.put("msg", "부서 정보 등록에 실패하였습니다.");
			
		}
		
		return result;
	}
	
}













