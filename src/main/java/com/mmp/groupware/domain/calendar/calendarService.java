package com.mmp.groupware.domain.calendar;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.business.bsnRefer.bsnRefer;
import com.mmp.groupware.domain.business.bsnRefer.bsnReferMapper;
import com.mmp.groupware.domain.business.bsnRefer.bsnReferRepository;
import com.mmp.groupware.domain.calendar.calRefer.calRefer;
import com.mmp.groupware.domain.calendar.calRefer.calReferRepository;
import com.mmp.groupware.domain.calendar.calRefer.calReferMapper;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.business.dto.bsnReferDto;
import com.mmp.groupware.web.calendar.dto.calReferDto;
import com.mmp.groupware.web.calendar.dto.calendarDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class calendarService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final calendarMapper calendarMapper;
    private final calendarRepository calendarRepo;

    // 참조 관련
    private final calReferMapper calReferMapper;
    private final calReferRepository calReferRepo;

    // 일정 수 조회
    public int getCalendarCnt(Map<String, Object> search) {
        return calendarMapper.getCalendarCnt(search);
    }

    // 일정 목록 조회
    public List<calendarDto> getCalendarList(Map<String, Object> search) {
        return calendarMapper.getCalendarList(search);
    }

    // 일정 상세 조회
    public calendarDto getCalendarDetail(long calNo) {
        calendarDto calDto = null;
        try {
            calDto = calendarMapper.getCalendarDetail(calNo);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return calDto;
    }

    // 일정 삭제
    public Map<String, Object> deleteCalendar(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            Optional<calendar> optCal = calendarRepo.findById(Long.parseLong(param.get("calNo").toString()));

            if(optCal.isPresent()) {
                calendar cal = optCal.get();
                cal.setDeleteDt(LocalDateTime.now());
                calendarRepo.save(cal);

                result.put("code", "success");
                result.put("url", "/calendar/list");
            }
        }catch(Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 일정 등록
    public Map<String, Object> addCalendar(String insertForm) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            LocalDate calDate = LocalDate.parse((CharSequence) param.get("calDate"));
            LocalDate calStartTime = LocalDate.parse((CharSequence) param.get("calStartTime"));
            LocalDate calEndTime = LocalDate.parse((CharSequence) param.get("calEndTime"));

            // 1. 일정 등록
            calendar cal = calendar.builder()
                    .calNm(param.get("calNm").toString().trim())
                    .calContent(param.get("calContent").toString())
                    .calDate(calDate.atTime(0, 0, 0, 0))
                    .calStartTime(LocalTime.from(calStartTime.atTime(0, 0, 0, 0)))
                    .calEndTime(LocalTime.from(calEndTime.atTime(0, 0, 0, 0)))
                    .createDt(LocalDateTime.now())
                    .build();

            Long calNo = calendarRepo.save(cal).getCalNo();

            // 2. 참조인원 정보 등록
            List<Map<String,Object>> currRefList = (List<Map<String, Object>>) param.get("currRefList");

            for(int j=0; j<currRefList.size(); j++) {
                calRefer calf = calRefer.builder()
                        .calNo(calNo)
                        .calRefStfNo(Long.parseLong(currRefList.get(j).get("stfNo").toString()))
                        .createDt(LocalDateTime.now())
                        .build();

                calReferRepo.save(calf);
            }

            System.out.println(currRefList);

            result.put("code", "success");
            result.put("msg", "일정을 등록하였습니다.");
            result.put("url", "/calendar/detail?calNo="+calNo);

        }catch(Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "일정 등록에 실패하였습니다");
        }

        return result;
    }

    // 일정 수정
    public Map<String, Object> editCal(Map<String, Object> updateForm) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<calendar> optCal = calendarRepo.findById(Long.parseLong(updateForm.get("calNo").toString()));

            if(optCal.isEmpty()) {
                result.put("code","fail");
                result.put("msg","일정 정보 수정에 실패하였습니다.");
            }

            calendar cal = optCal.get();

            LocalDate calDate = LocalDate.parse((CharSequence) updateForm.get("calDate"));
            LocalDate calStartTime = LocalDate.parse((CharSequence) updateForm.get("calStartTime"));
            LocalDate calEndTime = LocalDate.parse((CharSequence) updateForm.get("calEndTime"));

            cal.setUpdateDt(LocalDateTime.now());
            cal.setCalNm(updateForm.get("calNm").toString());
            cal.setCalContent(updateForm.get("calContent").toString());
            cal.setCalDate(calDate.atTime(0, 0, 0, 0));
            cal.setCalStartTime(LocalTime.from(calStartTime.atTime(0, 0, 0, 0)));
            cal.setCalEndTime(LocalTime.from(calEndTime.atTime(0, 0, 0, 0)));

            calendarRepo.save(cal);

            if((boolean) updateForm.get("refChgYn")) {
                // 기존 참조 인원 정보 delete
                List<calReferDto> prevRefList = calReferMapper.getCalReferList(Long.parseLong(updateForm.get("calNo").toString()));
                for(int i=0; i<prevRefList.size(); i++) {
                    Optional<calRefer> optCalf = calReferRepo.findById(prevRefList.get(i).getCalRefNo());
                    if(optCalf.isPresent()) {
                        calRefer calf = optCalf.get();
                        calf.setDeleteDt(LocalDateTime.now());
                        calReferRepo.save(calf);
                    }
                }

                // 참조인원 정보 등록
                List<Map<String,Object>> currRefList = (List<Map<String, Object>>) updateForm.get("currRefList");

                for(int j=0; j<currRefList.size(); j++) {
                    calRefer calf = calRefer.builder()
                            .calNo(Long.parseLong(updateForm.get("calNo").toString()))
                            .calRefStfNo(Long.parseLong(currRefList.get(j).get("stfNo").toString()))
                            .createDt(LocalDateTime.now())
                            .build();

                    calReferRepo.save(calf);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","일정 정보 수정에 실패하였습니다.");
        }

        return result;
    }

}
