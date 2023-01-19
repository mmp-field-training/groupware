package com.mmp.groupware.web.takeoff;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mmp.groupware.domain.dept.deptService;
import com.mmp.groupware.domain.takeoff.takeoffService;
import com.mmp.groupware.util.pagingUtil;

import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.business.dto.bsnCommDto;
import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.business.dto.bsnFilesDto;
import com.mmp.groupware.web.business.dto.bsnReferDto;
import com.mmp.groupware.web.staff.dto.staffAddDto;
import com.mmp.groupware.web.staff.dto.staffDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/takeoff")
@RequiredArgsConstructor
public class takeoffController {
    private final sessionUtil sess;
    private final takeoffService tofServ;
    private final deptService deptServ;

    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model) {
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        model.addAttribute("deptList", deptServ.findAll());
        // 상단메뉴
        model.addAttribute("menu","takeoff");
        System.out.println("controller-add");
        return "takeoff/add";
    }
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request,
                                   Model model,
                                   @RequestBody takeoffAddDto addForm
    ) throws JsonMappingException, JsonProcessingException {
        // 상단 메뉴
        model.addAttribute("menu","takeoff");

        Map<String,Object> result = new HashMap<String, Object>();
        // 세션 체크
        System.out.println("controller5-add2");
        if(sess.sessionCheck(request, "stfNo")) {
            result.put("code","fail");
            result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
            result.put("url","/staff/login");
            return result;
        }
        System.out.println("controller-add3");
        return tofServ.addTof(addForm,request);
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
        model.addAttribute("menu","takeoff");

        try {
            Map<String, Object> search = new HashMap<String, Object>();
            search.put("keyword", keyword);
            search.put("searchCode", searchCode);

            // 사원 정보 개수 조회
            int cnt = tofServ.getTofCnt(search);

            // 페이징
            pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
            search.put("start", pgn.getStart());
            search.put("end", pgn.getCntPerPg());
            model.addAttribute("cntPerPg" , pgn.getCntPerPg());

            // 회원 목록 조회
            List<takeoffDto> tofList = tofServ.getTofList(search);

            model.addAttribute("tofList", tofList);

            // 완료 데이터 세팅 및 조회조건 바인드
            model.addAttribute("pgn", pgn);
            model.addAttribute("search",search);

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "takeoff/list";
    }
    @GetMapping("/detail")
    public String detail(HttpServletRequest request, Model model, long tofNo) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","takeoff");

        // 글 자체
        takeoffDto tofDto = tofServ.getTofDetail(tofNo);

        model.addAttribute("takeoffDto",tofDto);

        return "takeoff/detail";
    }
/*
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            result = tofServ.deleteTakeoff(param);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }*/

    // 사원 정보 수정
    @GetMapping("/edit")
    public String edit(HttpServletRequest request, Model model, long tofNo) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }

        // 상단 메뉴
        model.addAttribute("menu","takeoff");

        takeoffDto tofUptDto = tofServ.getTofDetail(tofNo);
        model.addAttribute("tofUptDto",tofUptDto);

        return "takeoff/edit";
    }

    // 사원 정보 수정 실행
    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(HttpServletRequest request, Model model, @RequestBody takeoffDto updateForm){
        Map<String, Object> result = new HashMap<String, Object>();
        // 상단 메뉴
        model.addAttribute("menu","takeoff");
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            result.put("code","fail");
            result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
            result.put("url","/staff/login");
            return result;
        }

        try {
            result = tofServ.updateTakeoff(updateForm, request);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
