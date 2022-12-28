package com.mmp.groupware.web.commute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mmp.groupware.domain.commute.commuteService;
import com.mmp.groupware.web.staff.dto.staffAddDto;
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
@RequestMapping("/commute")
@RequiredArgsConstructor
public class commuteController {

    private final sessionUtil sess;

    private final commuteService comServ;

    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }

        model.addAttribute("menu", "commute");
        return "commute/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(HttpServletRequest request,
                                   Model model,
                                   @RequestBody commuteAddDto addForm
    ) throws JsonMappingException, JsonProcessingException{
        // 상단 메뉴
        model.addAttribute("menu","commute");

        Map<String, Object> result = new HashMap<>();
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            result.put("code","fail");
            result.put("msg","로그인이 필요한 서비스 입니다. 로그인 하시겠습니까?");
            result.put("url","/staff/login");
            return result;
        }

        result = comServ.addCommute(addForm, request);

        return result;
    }

    @GetMapping("/list")
    public String list(HttpServletRequest request, Model model) {

        return "commute/list";
    }

    @GetMapping("/myList")
    public String myList(HttpServletRequest request, Model model) {

        return "commute/myList";
    }
}
