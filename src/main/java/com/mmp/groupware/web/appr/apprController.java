package com.mmp.groupware.web.appr;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.appr.apprRefer.apprReferService;
import com.mmp.groupware.domain.appr.apprService;
import com.mmp.groupware.domain.attachment.attachMapper;
import com.mmp.groupware.domain.business.bsnComm.bsnCommService;
import com.mmp.groupware.domain.business.bsnRefer.bsnReferService;
import com.mmp.groupware.domain.staff.staffService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.appr.dto.apprDto;
import com.mmp.groupware.web.appr.dto.apprFilesDto;
import com.mmp.groupware.web.appr.dto.apprReferDto;
import com.mmp.groupware.web.business.dto.bsnCommDto;
import com.mmp.groupware.web.business.dto.bsnDto;
import com.mmp.groupware.web.business.dto.bsnFilesDto;
import com.mmp.groupware.web.business.dto.bsnReferDto;
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
@RequestMapping("/appr")
@RequiredArgsConstructor
public class apprController {

    private final sessionUtil sess;

    private final staffService stfServ;

    private final apprService apprServ;

    private final apprReferService apprRefServ;

    private final attachMapper atcMapper;

    // 기안 목록 조회 페이지 이동
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
        model.addAttribute("menu","appr");

        try {
            Map<String, Object> search = new HashMap<String, Object>();
            search.put("keyword", keyword);

            // 사원 정보 개수 조회
            int cnt = apprServ.getApprCnt(search);

            // 페이징
            pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
            search.put("start", pgn.getStart());
            search.put("end", pgn.getCntPerPg());
            model.addAttribute("cntPerPg" , pgn.getCntPerPg());

            // 기안 목록 조회
            List<apprDto> apprList = apprServ.getApprList(search);

            model.addAttribute("apprList", apprList);

            // 완료 데이터 세팅 및 조회조건 바인드
            model.addAttribute("pgn", pgn);
            model.addAttribute("search",search);

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "appr/list";
    }

    // 기안 상세 페이지 이동
    @GetMapping("/detail")
    public String detail(HttpServletRequest request, Model model, long apprNo) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","appr");

        // 글 자체
        apprDto apprDto = apprServ.getApprDetail(apprNo);
        // 참조 인원
        List<apprReferDto> apprRefList = apprRefServ.getApprReferList(apprNo);
        // 첨부파일 조회
        List<apprFilesDto> apprFilesList = atcMapper.getApprFiles(apprNo);

        model.addAttribute("apprDto", apprDto);
        model.addAttribute("apprRefList", apprRefList);
        model.addAttribute("apprFilesList", apprFilesList);

        return "appr/detail";
    }

    // 기안 등록 페이지 이동
    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model,
                      @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 상단메뉴
        model.addAttribute("menu","appr");
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));
        return "appr/add";
    }

    // 기안 참조 인원 세팅
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

    // 기안 등록
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

        return apprServ.addAppr(insertForm,files,request);
    }

    // 업무 삭제
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            result = apprServ.deleteAppr(param);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 기안 수정 페이지 이동
    @GetMapping("/edit")
    public String edit(HttpServletRequest request, Model model, long apprNo,
                       @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","appr");

        // 참조 할 사람들 체크
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));

        // 글 자체
        apprDto apprDto = apprServ.getApprDetail(apprNo);
        // 참조 인원
        List<apprReferDto> apprRefList = apprRefServ.getApprReferList(apprNo);

        model.addAttribute("apprDto",apprDto);
        model.addAttribute("apprRefList",apprRefList);

        return "appr/edit";
    }

    // 기안 수정 실행
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

            result = apprServ.editAppr(param, request);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
