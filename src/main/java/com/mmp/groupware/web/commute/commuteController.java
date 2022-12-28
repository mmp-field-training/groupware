package com.mmp.groupware.web.commute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mmp.groupware.domain.commute.commuteService;
import com.mmp.groupware.web.staff.dto.staffDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mmp.groupware.util.pagingUtil;
import com.mmp.groupware.util.sessionUtil;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/commute")
@RequiredArgsConstructor
public class commuteController {

    private final sessionUtil sess;

    private final commuteService comServ;

    //출퇴근 등록
    @GetMapping("/add")
    public String add(HttpServletRequest request, Model model) {

        return "commute/add";
    }

    //전체 출퇴근 조회
    @GetMapping("/list")
    public String list(Model model, @ModelAttribute pagingUtil pgn,HttpServletRequest request) {
//세션체크 일단 생략!
        // 상단메뉴
        model.addAttribute("menu","commute");
        try {

            // 회원 목록 조회
            List<commuteDto> comList = comServ.getWeekComList(1);//임시값

            model.addAttribute("comList", comList);

            /*
            // 결과 정보 개수 조회
            long cnt = comList[0];

            // 페이징
            pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
            model.addAttribute("cntPerPg" , pgn.getCntPerPg());
            */

            // 완료 데이터 세팅 및 조회조건 바인드
            model.addAttribute("pgn", pgn);

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
        return "commute/list";
    }

    //내 출근정보 조회

    @GetMapping("/myList")
    public String myList(HttpServletRequest request, Model model,long atte_no,
                         //@RequestParam(value="searchCode", defaultValue="1") int searchCode,
                         @ModelAttribute pagingUtil pgn) {

        // 세션 체크
        if(sess.sessionCheck(request, "stfNo")) {
            return "redirect:/staff/login";
        }

        // 상단메뉴
        model.addAttribute("menu","commute");

        try {
            //Map<String, Object> search = new HashMap<String, Object>();
            //search.put("searchCode", searchCode);

            /*  //페이징
            pgn = new pagingUtil(cnt, pgn.getNowPg() , pgn.getCntPerPg());
            search.put("start", pgn.getStart());
            search.put("end", pgn.getCntPerPg());
            model.addAttribute("cntPerPg" , pgn.getCntPerPg());
             */

            // 회원 목록 조회
            List<commuteDto> commuteList = comServ. getPersonalComList(atte_no);

            model.addAttribute("commuteRecord", commuteList);

            // 완료 데이터 세팅 및 조회조건 바인드
            //model.addAttribute("pgn", pgn);
            //model.addAttribute("search",search);

        }catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return "commute/myList";
    }

}
