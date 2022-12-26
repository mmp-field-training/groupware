package com.mmp.groupware.web.staff;

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
import com.mmp.groupware.domain.auth.authService;
import com.mmp.groupware.domain.dept.deptService;
import com.mmp.groupware.domain.rnk.rnkService;
import com.mmp.groupware.domain.staff.staffService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.staff.dto.loginDto;
import com.mmp.groupware.web.staff.dto.staffAddDto;
import com.mmp.groupware.web.staff.dto.staffDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
public class staffController {
	
	private final sessionUtil sess;
	
	private final staffService stfServ;
	private final authService authServ;
	private final deptService deptServ;
	private final rnkService rnkServ;
	
	// 로그인 페이지로 이동
	@GetMapping("/login")
	public String login() {
		return "staff/login";
	}
	
	// 로그인 실행 메소드
	@PostMapping("/login")
	@ResponseBody
	public Map<String, Object> login(@RequestBody loginDto loginForm, HttpServletRequest request){
		return stfServ.login(loginForm, request);
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(Model model, HttpServletRequest request){
		sess.sessionOut(request);
		return "staff/login";
	}
	
	
	// 사원 목록 조회 페이지 이동
	@GetMapping("/list")
	public String list(Model model,
			@ModelAttribute pagingUtil pgn,
			@RequestParam(value="searchCode", defaultValue="1") int searchCode,
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
			search.put("searchCode", searchCode);
			
			// 사원 정보 개수 조회
			int cnt = stfServ.getStfCnt(search);

			// 페이징 
			pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
			search.put("start", pgn.getStart());
			search.put("end", pgn.getCntPerPg());
			model.addAttribute("cntPerPg" , pgn.getCntPerPg());

			// 회원 목록 조회
			List<staffDto> stfList = stfServ.getStfList(search);

			model.addAttribute("stfList", stfList);
			
			// 완료 데이터 세팅 및 조회조건 바인드
			model.addAttribute("pgn", pgn);
			model.addAttribute("search",search);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "staff/list";
	}

	// 사원 상세 페이지 이동
	@GetMapping("/detail")
	public String detail(HttpServletRequest request, Model model, long stfNo) {
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			return "redirect:/staff/login";
		}
		// 상단 메뉴
		model.addAttribute("menu","staff");
		
		staffDto stfDto = stfServ.getStfDetail(stfNo);
		model.addAttribute("stfDto",stfDto);
		return "staff/detail";
	}
	
	// 사원 등록 페이지 이동
	@GetMapping("/add")
	public String add(HttpServletRequest request, Model model) {
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			return "redirect:/staff/login";
		}
		model.addAttribute("authList", authServ.findAll());
		model.addAttribute("deptList", deptServ.findAll());
		model.addAttribute("rnkList", rnkServ.findAll());
		
		// 상단 메뉴
		model.addAttribute("menu","staff");
		return "staff/add";
	}
	
	// 사원 등록
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request, 
			Model model,
			@RequestBody staffAddDto addForm
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
		
		result = stfServ.addStaff(addForm, request);
		
		return result;
	}
	
	// 사원 정보 삭제
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result = stfServ.deleteStaff(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 사원 정보 수정
	@GetMapping("/edit")
	public String edit(HttpServletRequest request, Model model, long stfNo) {
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			return "redirect:/staff/login";
		}

		// 상단 메뉴
		model.addAttribute("menu","user");
		
		staffDto stfUptDto = stfServ.getStfDetail(stfNo);
		model.addAttribute("stfUptDto",stfUptDto);

		model.addAttribute("authList", authServ.findAll());
		model.addAttribute("deptList", deptServ.findAll());
		model.addAttribute("rnkList", rnkServ.findAll());
		
		return "staff/edit";
	}

	// 사원 정보 수정 실행
	@PostMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(HttpServletRequest request, Model model, @RequestBody staffDto updateForm){
		Map<String, Object> result = new HashMap<String, Object>();
		// 상단 메뉴
		model.addAttribute("menu","staff");
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			result.put("code","fail");
			result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
			result.put("url","/staff/login");
			return result;
		}
				
		try {
			result = stfServ.updateStaff(updateForm, request);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
}
































