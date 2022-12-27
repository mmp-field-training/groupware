package com.mmp.groupware.web.calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.calendar.calRefer.calReferService;
import com.mmp.groupware.domain.calendar.calendarService;
import com.mmp.groupware.domain.staff.staffService;
import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.calendar.dto.calReferDto;
import com.mmp.groupware.web.calendar.dto.calendarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cal")
@RequiredArgsConstructor
public class calendarController {

    private final sessionUtil sess;

    private final staffService stfServ;

    private final calendarService calServ;
    private final calReferService calRefServ;

    // 일정 목록 조회 페이지 이동
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
        model.addAttribute("menu","calendar");

        try {
            Map<String, Object> search = new HashMap<String, Object>();
            search.put("keyword", keyword);

            // 일정 정보 개수 조회
            int cnt = calServ.getCalendarCnt(search);

            /*
            // 페이징
            pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
            search.put("start", pgn.getStart());
            search.put("end", pgn.getCntPerPg());
            model.addAttribute("cntPerPg" , pgn.getCntPerPg());
             */

            // 일정 목록 조회
            List<calendarDto> calList = calServ.getCalendarList(search);

            model.addAttribute("calList", calList);

            // 완료 데이터 세팅 및 조회조건 바인드
            model.addAttribute("pgn", pgn);
            model.addAttribute("search",search);

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "calendar/list";
    }

    // 일정 상세 페이지 이동
    @GetMapping("/detail")
    public String detail(HttpServletRequest request, Model model, long calNo) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","calendar");

        // 글 자체
        calendarDto calDto = calServ.getCalendarDetail(calNo);
        // 참조 인원
        List<calReferDto> calRefList = calRefServ.getCalReferList(calNo);

        model.addAttribute("calendarDto",calDto);
        model.addAttribute("calRefList",calRefList);

        return "calendar/detail";
    }

    // 일정 등록 페이지 이동
    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model,
                      @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 상단메뉴
        model.addAttribute("menu","calendar");
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));
        return "calendar/add";
    }

    // 일정 참조 인원 세팅
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

    // 일정 등록
    @PostMapping("/add")
    @ResponseBody
    public Map<String, Object> add(@RequestParam("formData") String insertForm
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

        return calServ.addCalendar(insertForm, request);
    }

    // 일정 삭제
    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> delete(@RequestBody Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            result = calServ.deleteCalendar(param);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 일정 수정 페이지 이동
    @GetMapping("/edit")
    public String edit(HttpServletRequest request, Model model, long calNo,
                       @RequestParam(value="keyword", defaultValue="") String keyword) {
        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }
        // 상단 메뉴
        model.addAttribute("menu","calendar");

        // 참조 할 사람들 체크
        Map<String, Object> search = new HashMap<String, Object>();
        search.put("keyword", keyword);
        search.put("stfNo", sess.sessionGet(request, "stfNo"));
        model.addAttribute("stfList", stfServ.getStfList(search));

        // 글 자체
        calendarDto calDto = calServ.getCalendarDetail(calNo);
        // 참조 인원
        List<calReferDto> calRefList = calRefServ.getCalReferList(calNo);

        model.addAttribute("calDto",calDto);
        model.addAttribute("calRefList",calRefList);

        return "calendar/edit";
    }

    // 일정 수정 실행
    @PostMapping("/edit")
    @ResponseBody
    public Map<String, Object> edit(HttpServletRequest request, Model model,
                                    @RequestParam("formData") String updateForm
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

            result = calServ.editCal(param, request);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

}
