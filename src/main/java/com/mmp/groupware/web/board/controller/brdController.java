package com.mmp.groupware.web.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachMapper;
import com.mmp.groupware.domain.board.brd.brdService;
import com.mmp.groupware.domain.board.brdReply.brdReplyService;
import com.mmp.groupware.domain.staff.staffService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.board.dto.brdDto;
import com.mmp.groupware.web.board.dto.brdFilesDto;
import com.mmp.groupware.web.board.dto.brdReplyDto;
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
@RequestMapping("/brd")
@RequiredArgsConstructor
public class brdController {

    private final sessionUtil sess;

    private final staffService stfServ;
    private final brdService brdServ;
    private final brdReplyService brServ;

    private final attachMapper atcMapper;

    // 게시판 목록 조회 페이지 이동
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
        model.addAttribute("menu","brd");

        try {
            Map<String, Object> search = new HashMap<String, Object>();
            search.put("keyword", keyword);

            // 사원 정보 개수 조회
            int cnt = brdServ.getBrdCnt(search);

            // 페이징
            pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
            search.put("start", pgn.getStart());
            search.put("end", pgn.getCntPerPg());
            model.addAttribute("cntPerPg" , pgn.getCntPerPg());

            // 회원 목록 조회
            List<brdDto> brdList = brdServ.getBrdList(search);

            model.addAttribute("brdList", brdList);

            // 완료 데이터 세팅 및 조회조건 바인드
            model.addAttribute("pgn", pgn);
            model.addAttribute("search",search);

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "brd/list";
    }

    // 게시판 상세 페이지 이동
    @GetMapping("/detail")
    public String detail(HttpServletRequest request, Model model, long brdNo) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","brd");

        // 글 자체
        brdDto brdDto = brdServ.getBrdDetail(brdNo);
        // 첨부파일 조회
        List<brdFilesDto> brdFilesList = atcMapper.getBrdFiles(brdNo);
        // 댓글
        List<brdReplyDto> brList = brServ.getBrdReplyList(brdNo);

        model.addAttribute("brdDto",brdDto);
        model.addAttribute("brdFilesList",brdFilesList);
        model.addAttribute("brList",brList);

        return "brd/detail";
    }

    // 게시판 등록 페이지 이동
    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model,
                      @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 상단메뉴
        model.addAttribute("menu","brd");
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));
        return "brd/add";
    }

    // 게시판 등록
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

        return brdServ.addBrd(insertForm,files,request);
    }

    // 게시판 삭제
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            result = brdServ.deleteBrd(param);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 댓글 등록
    @PostMapping("/addBrdReply")
    @ResponseBody
    public Map<String, Object> addBrdComm(@RequestBody Map<String, Object> param, HttpServletRequest request){
        Map<String,Object> result = new HashMap<String, Object>();
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            result.put("code","fail");
            result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
            result.put("url","/staff/login");
            return result;
        }
        return brServ.addBrdReply(param, request);
    }

    // 댓글 삭제
    @PostMapping("/deleteBrdReply")
    @ResponseBody
    public Map<String, Object> deleteBrdReply(@RequestBody Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            result = brServ.deleteBrdReply(param);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 게시판 수정 페이지 이동
    @GetMapping("/edit")
    public String edit(HttpServletRequest request, Model model, long brdNo,
                       @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","brd");

        // 참조 할 사람들 체크
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));

        // 글 자체
        brdDto brdDto = brdServ.getBrdDetail(brdNo);

        model.addAttribute("brdDto",brdDto);

        return "brd/edit";
    }

    // 게시판 수정 실행
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

            result = brdServ.editBrd(param, request);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
