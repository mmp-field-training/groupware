package com.mmp.groupware.web.dept;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mmp.groupware.domain.dept.deptService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/dept")
@RequiredArgsConstructor
public class deptController {

	private final sessionUtil sess;
	
	private final deptService deptServ;
	
	// 부서 목록 조회 페이지 이동
	@GetMapping("/list")
	public String list(Model model,
			@ModelAttribute pagingUtil pgn,
			@RequestParam(value="keyword", defaultValue="") String keyword,
			HttpServletRequest request) {
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			return "redirect:/staff/login";
		}
		// 상단메뉴
		model.addAttribute("menu","staff");
		
		try {
			Map<String, Object> search = new HashMap<String, Object>();
			search.put("keyword", keyword);
			
			// 부서 정보 개수 조회
			int cnt = deptServ.getDeptCnt(search);

			// 페이징 
			pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
			search.put("start", pgn.getStart());
			search.put("end", pgn.getCntPerPg());
			model.addAttribute("cntPerPg" , pgn.getCntPerPg());

			// 부서 목록 조회
			List<deptDto> deptList = deptServ.getDeptList(search);

			model.addAttribute("deptList", deptList);
			
			// 완료 데이터 세팅 및 조회조건 바인드
			model.addAttribute("pgn", pgn);
			model.addAttribute("search",search);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "dept/list";
	}
	
	// 부서 삭제
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result = deptServ.deleteDept(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 부서 수정
	@PostMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(@RequestBody Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result = deptServ.editDept(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 부서 등록 페이지 이동
	@GetMapping("/add")
	public String add(HttpServletRequest request, Model model) {
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			return "redirect:/staff/login";
		}
		// 상단 메뉴
		model.addAttribute("menu","staff");
		return "dept/add";
	}
	
	// 부서 등록 
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request, 
			Model model,
			@RequestBody Map<String, Object> addForm
			) throws JsonMappingException, JsonProcessingException{
		// 상단 메뉴
		model.addAttribute("menu","staff");
		
		Map<String, Object> result = new HashMap<>();
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			result.put("code","fail");
			result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
			result.put("url","/staff/login");
			return result;
		}
		
		result = deptServ.addDept(addForm, request);		
		return result;
	}
	
}






























