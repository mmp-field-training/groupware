package com.mmp.groupware.domain.commute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import com.mmp.groupware.web.commute.commuteAddDto;
import com.mmp.groupware.web.commute.commuteDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class commuteService {
    private final commuteMapper comMapper;

    private final commuteRepository comRepo;

    public List<commuteDto> getComList(Map<String, Object> search) {
        return comMapper.getComList(search);
    }

    public List<commuteDto> getDailyComDetail(LocalDate date) {
        return comMapper.getDailyComDetail(date);
    }

    public List<commuteDto> getWeekComList(long atteNo) {

        return comMapper.getWeekComList(atteNo);
    }

    //월간 출퇴근 조회
    public List<commuteDto> getMonthlyList(LocalDate date) {
        return comMapper.getMonthlyList(date);
    }

    public List<commuteDto> getPersonalComList(long stfNo) {
        return comMapper.getPersonalComList(stfNo);
    }


    // 출근 (등록)
    public Map<String, Object> addCommute(commuteAddDto addForm, @RequestParam(value="AtteCode", defaultValue="y") String AtteCode,
                                          HttpServletRequest request) throws JsonMappingException, JsonProcessingException{
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            commute com = commute.builder()
                    .stfNo(addForm.getStfNo())
                    .atteYn(addForm.getAtteYn())
                    .atteTime(addForm.getAtteTime())
                    .leavedTime(addForm.getLeavedTime())
                    .createDt(LocalDateTime.now())
                    .updateDt(null)
                    .deleteDt(null)
                    .build();


            Long atteNo = comRepo.save(com).getAtteNo();

            result.put("code", "success");
            result.put("url", "/commute?atteNo="+atteNo);

        }catch(Exception e) {
            e.printStackTrace();
            result.put("code", "fail");
            result.put("msg", "출근 등록에 실패하였습니다.");
        }

        return result;
    }


    // 퇴근 (등록)


    public Map<String, Object> updateLeavedTime(Long atteNo, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<>();


        try {
            Optional<commute> optCom = comRepo.findById(atteNo);
            // 존재하지 않을 시
            if(optCom.isEmpty()) {
                result.put("code","fail");
                result.put("msg","퇴근 저장에 실패하였습니다.");
            }

            commute com = optCom.get();
            if(com.getAtteYn().equals("y")){
                result.put("code","fail");
                result.put("msg","퇴근에 실패하였습니다. 출근정보가 없습니다.");
            }
            com.setLeavedTime(LocalDateTime.now());
            com.setUpdateDt(LocalDateTime.now());

            comRepo.save(com);

            result.put("code", "success");
            result.put("url", "/commute");// /detail?stfNo=" + atteNo);

        }catch(Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","퇴근 저장에 실패하였습니다.");
        }

        return result;
    }

}