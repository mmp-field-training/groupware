package com.mmp.groupware.web.dailyWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachMapper;
import com.mmp.groupware.domain.dailyWork.dayWork.dayWorkService;
import com.mmp.groupware.domain.staff.staffService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.dailyWork.dto.dayWorkDto;
import com.mmp.groupware.web.dailyWork.dto.dayWorkFilesDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("dayWork")
@RequiredArgsConstructor
public class dayWorkController {

    private final sessionUtil sess;

    private final staffService stfServ;
    private final dayWorkService dayWorkServ;

    private final attachMapper atcMapper;

    // 일일보고 조회 페이지 이동
    @GetMapping("/list")
    public String list(Model model,
                       @ModelAttribute pagingUtil pgn,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword,
                       HttpServletRequest request) {
        if (sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }

        model.addAttribute("menu", "dayWork");

        try {
            Map<String, Object> search = new HashMap<String, Object>();
            search.put("keyword", keyword);

            int cnt = dayWorkServ.getDayWorkCnt(search);

            pgn = new pagingUtil(cnt, pgn.getNowPg(), pgn.getCntPerPg());
            search.put("start", pgn.getStart());
            search.put("end", pgn.getCntPerPg());
            model.addAttribute("cntPerPg", pgn.getCntPerPg());

            List<dayWorkDto> dayWorkList = dayWorkServ.getDayWorkList(search);

            model.addAttribute("dayWorkList", dayWorkList);

            model.addAttribute("pgn", pgn);
            model.addAttribute("search", search);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "dayWork/list";
    }

    // 일일보고 상세 페이지 이동
    @GetMapping("/detail")
    public String detail(HttpServletRequest request, Model model, long dayWorkNo) {
        if (sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }

        model.addAttribute("menu", "dayWork");

        dayWorkDto dayWorkDto = dayWorkServ.getDayWorkDetail(dayWorkNo);

        List<dayWorkFilesDto> dayWorkFilesList = atcMapper.getDayWorkFiles(dayWorkNo);

        model.addAttribute("dayWorkDto", dayWorkDto);
        model.addAttribute("dayWorkFilesList", dayWorkFilesList);

        return "dayWork/detail";
    }


    // 일일보고 등록 페이지 이동
    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model,
                      @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        model.addAttribute("menu", "dayWork");
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));

        return "dayWork/add";
    }

    // 일일보고 등록
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(@RequestParam("formData") String insertForm
        , @RequestParam(value = "files", required = false)List<MultipartFile> files
        , HttpServletRequest request
        , Model model) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (sess.sessionCheck(request, "stfNo")) {
            result.put("code", "fail");
            result.put("msg", "로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
            result.put("url", "/staff/login");
            return result;
        }

        return dayWorkServ.addDayWork(insertForm, files, request);
    }

    // 일일보고 삭제
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            result = dayWorkServ.deleteDayWork(param);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 일일보고 수정 페이지 이동
    @GetMapping("/edit")
    public String edit(HttpServletRequest request, Model model, long dayWorkNo,
                       @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        if (sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));

        model.addAttribute("menu", "dayWork");

        dayWorkDto dayWorkDto = dayWorkServ.getDayWorkDetail(dayWorkNo);

        model.addAttribute("dayWorkDto", dayWorkDto);

        return "dayWork/edit";
    }

    // 일일보고 수정 실행
    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(HttpServletRequest request, Model model,
                                    @RequestParam("formData") String updateForm,
                                    @RequestParam(value = "files", required = false) List<MultipartFile> files) {
        Map<String, Object> result = new HashMap<String, Object>();

        if (sess.sessionCheck(request, "stfNo")) {
            result.put("code", "fail");
            result.put("msg", "로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
            result.put("url", "/staff/login");
            return result;
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> param = mapper.readValue(updateForm, Map.class);

            result = dayWorkServ.editDayWork(param, request);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }


}
