package com.mmp.groupware.web.business.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachMapper;
import com.mmp.groupware.domain.business.bsn.bsnService;
import com.mmp.groupware.domain.business.bsnComm.bsnCommService;
import com.mmp.groupware.domain.business.bsnRefer.bsnReferService;
import com.mmp.groupware.domain.staff.staffService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.business.dto.bsnCommDto;
import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.business.dto.bsnFilesDto;
import com.mmp.groupware.web.business.dto.bsnReferDto;
import com.mmp.groupware.web.staff.dto.staffDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/bsn")
@RequiredArgsConstructor
public class bsnController {

	private final sessionUtil sess;
	
	private final staffService stfServ;
	private final bsnService bsnServ;
	private final bsnReferService bsnRefServ;
	private final bsnCommService bcServ;
	
	private final attachMapper atcMapper;
	
	// 업무 목록 조회 페이지 이동
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
		model.addAttribute("menu","bsn");
		
		try {
			Map<String, Object> search = new HashMap<String, Object>();
			search.put("keyword", keyword);
			
			// 사원 정보 개수 조회
			int cnt = bsnServ.getBsnCnt(search);

			// 페이징 
			pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
			search.put("start", pgn.getStart());
			search.put("end", pgn.getCntPerPg());
			model.addAttribute("cntPerPg" , pgn.getCntPerPg());

			// 회원 목록 조회
			List<bsnDto> bsnList = bsnServ.getBsnList(search);

			model.addAttribute("bsnList", bsnList);
			
			// 완료 데이터 세팅 및 조회조건 바인드
			model.addAttribute("pgn", pgn);
			model.addAttribute("search",search);
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return "bsn/list";
	}
	
	// 업무 상세 페이지 이동
	@GetMapping("/detail")
	public String detail(HttpServletRequest request, Model model, long bsnNo) {
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			return "redirect:/staff/login";
		}
		// 상단 메뉴
		model.addAttribute("menu","bsn");
		
		// 글 자체
		bsnDto bsnDto = bsnServ.getBsnDetail(bsnNo);
		// 참조 인원
		List<bsnReferDto> bsnRefList = bsnRefServ.getBsnReferList(bsnNo);
		// 첨부파일 조회
		List<bsnFilesDto> bsnFilesList = atcMapper.getBsnFiles(bsnNo);
		// 댓글
		List<bsnCommDto> bcList = bcServ.getBsnCommList(bsnNo);
		
		model.addAttribute("bsnDto",bsnDto);
		model.addAttribute("bsnRefList",bsnRefList);
		model.addAttribute("bsnFilesList",bsnFilesList);
		model.addAttribute("bcList",bcList);
		
		return "bsn/detail";
	}
	
	// 업무 등록 페이지 이동
	@GetMapping("/add")
	public String add(HttpServletRequest request, Model model,
			@RequestParam(value="keyword", defaultValue="") String keyword) {
		// 상단메뉴
		model.addAttribute("menu","bsn");
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("keyword", keyword);
		search.put("stfNo", sess.sessionGet(request, "stfNo"));
		model.addAttribute("stfList", stfServ.getStfList(search));
		return "bsn/add";
	}
	
	// 업무 참조 인원 세팅
	@PostMapping("/getRefStf")
	@ResponseBody
	public Map<String,Object> getRefStf(@RequestBody Map<String, Object> search){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			result.put("stfList", stfServ.getStfList(search));
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return result;
	}
	
	// 업무 등록
	@PostMapping("/add")
	@ResponseBody
	public Map<String, Object> add(@RequestParam("formData") String insertForm
		,	@RequestParam(value="files", required=false) List<MultipartFile> files
		,	HttpServletRequest request
		,	Model model) {
		Map<String,Object> result = new HashMap<String, Object>();
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			result.put("code","fail");
			result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
			result.put("url","/staff/login");
			return result;
		}
		
		return bsnServ.addBsn(insertForm,files,request);
	}
	
	// 업무 삭제 
	@PostMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(@RequestBody Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result = bsnServ.deleteBsn(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 댓글 등록
	@PostMapping("/addBsnComm")
	@ResponseBody
	public Map<String, Object> addBsnComm(@RequestBody Map<String, Object> param,	HttpServletRequest request){
		Map<String,Object> result = new HashMap<String, Object>();
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			result.put("code","fail");
			result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
			result.put("url","/staff/login");
			return result;
		}
		return bcServ.addBsnComm(param, request);
	}
	// 댓글 삭제 
	@PostMapping("/deleteBsnComm")
	@ResponseBody
	public Map<String, Object> deleteBsnComm(@RequestBody Map<String, Object> param){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			result = bcServ.deleteBsnComm(param);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 업무 수정 페이지 이동
	@GetMapping("/edit")
	public String edit(HttpServletRequest request, Model model, long bsnNo,
			@RequestParam(value="keyword", defaultValue="") String keyword) {
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			return "redirect:/staff/login";
		}
		// 상단 메뉴
		model.addAttribute("menu","bsn");
		
		// 참조 할 사람들 체크
		Map<String, Object> search = new HashMap<String, Object>();
		search.put("keyword", keyword);
		search.put("stfNo", sess.sessionGet(request, "stfNo"));
		model.addAttribute("stfList", stfServ.getStfList(search));
		
		// 글 자체
		bsnDto bsnDto = bsnServ.getBsnDetail(bsnNo);
		// 참조 인원
		List<bsnReferDto> bsnRefList = bsnRefServ.getBsnReferList(bsnNo);
		
		model.addAttribute("bsnDto",bsnDto);
		model.addAttribute("bsnRefList",bsnRefList);
		
		return "bsn/edit";
	}
	
	// 업무 수정 실행
	@PostMapping("/edit")
	@ResponseBody
	public Map<String, Object> edit(HttpServletRequest request, Model model, 
			@RequestParam("formData") String updateForm,
		@RequestParam(value="files", required=false) List<MultipartFile> files
	){
		Map<String, Object> result = new HashMap<String, Object>();
		
		// 세션 체크
		if(sess.sessionCheck(request, "stfNo")) {
			result.put("code","fail");
			result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
			result.put("url","/staff/login");
			return result;
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			Map<String,Object> param = mapper.readValue(updateForm, Map.class);
			
			result = bsnServ.editBsn(param, request);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
				
		return result;
	}
		
}




















