package com.mmp.groupware.web.pmi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachMapper;
import com.mmp.groupware.domain.plcMnyInfo.pmi.pmiService;
import com.mmp.groupware.domain.staff.staffService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.pmi.dto.pmiDto;
import com.mmp.groupware.web.pmi.dto.pmiFilesDto;
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
@RequestMapping("/pmi")
@RequiredArgsConstructor
public class pmiController {

    private final sessionUtil sess;

    private final staffService stfServ;
    private final pmiService pmiServ;

    private final attachMapper atcMapper;

    // 공금정보 목록 조회 페이지 이동
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
        model.addAttribute("menu","pmi");

        try {
            Map<String, Object> search = new HashMap<String, Object>();
            search.put("keyword", keyword);

            // 사원 정보 개수 조회
            int cnt = pmiServ.getPmiCnt(search);

            // 페이징
            pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
            search.put("start", pgn.getStart());
            search.put("end", pgn.getCntPerPg());
            model.addAttribute("cntPerPg" , pgn.getCntPerPg());

            // 회원 목록 조회
            List<pmiDto> pmiList = pmiServ.getPmiList(search);

            model.addAttribute("pmiList", pmiList);

            // 완료 데이터 세팅 및 조회조건 바인드
            model.addAttribute("pgn", pgn);
            model.addAttribute("search",search);

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "pmi/list";
    }

    // 공금정보 상세 페이지 이동
    @GetMapping("/detail")
    public String detail(HttpServletRequest request, Model model, long pmiNo) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","pmi");

        // 글 자체
        pmiDto pmiDto = pmiServ.getPmiDetail(pmiNo);
        // 첨부파일 조회
        List<pmiFilesDto> pmiFilesList = atcMapper.getPmiFiles(pmiNo);

        model.addAttribute("pmiDto",pmiDto);
        model.addAttribute("pmiFilesList",pmiFilesList);

        return "pmi/detail";
    }

    // 공금정보 등록 페이지 이동
    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model,
                      @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 상단메뉴
        model.addAttribute("menu","pmi");
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));
        return "pmi/add";
    }

    // 공금정보 등록
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

        return pmiServ.addPmi(insertForm,files,request);
    }

    // 업무 삭제
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            result = pmiServ.deletePmi(param);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 공금정보 수정 페이지 이동
    @GetMapping("/edit")
    public String edit(HttpServletRequest request, Model model, long pmiNo,
                       @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","pmi");

        // 글 자체
        pmiDto pmiDto = pmiServ.getPmiDetail(pmiNo);

        model.addAttribute("pmiDto",pmiDto);

        return "pmi/edit";
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

            result = pmiServ.editPmi(param, request);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
