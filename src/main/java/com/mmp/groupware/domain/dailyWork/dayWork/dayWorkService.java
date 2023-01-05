package com.mmp.groupware.domain.dailyWork.dayWork;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachRepository;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.domain.dailyWork.dayWorkFile.dayWorkFiles;
import com.mmp.groupware.domain.dailyWork.dayWorkFile.dayWorkFilesRepository;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.dailyWork.dto.dayWorkDto;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.hibernate.sql.OracleJoinFragment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class dayWorkService {
    private final fileUtil fileUtil;
    private final sessionUtil sess;
    private final dayWorkMapper dayWorkMapper;
    private final dayWorkRepository dayWorkRepo;

    // 파일 관련
    private final attachRepository atcRepo;
    private final dayWorkFilesRepository dayWorkFileRepo;

    // 일일보고 수 조회
    public int getDayWorkCnt(Map<String, Object> search) {
        return dayWorkMapper.getDayWorkCnt(search);
    }

    // 일일보고 목록 조회
    public List<dayWorkDto> getDayWorkList(Map<String, Object> search) {
        return dayWorkMapper.getDayWorkList(search);
    }

    // 일일보고 상세 조회
    public dayWorkDto getDayWorkDetail(long dayWorkNo) {
        dayWorkDto dayWorkDto = null;
        try {
            dayWorkDto = dayWorkMapper.getDayWorkDetail(dayWorkNo);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return dayWorkDto;
    }

    // 일일보고 삭제
    public Map<String, Object> deleteDayWork(Map<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Optional<dayWork> optDayWork = dayWorkRepo.findById(Long.parseLong(param.get("dayWorkNo").toString()));

            if (optDayWork.isPresent()) {
                dayWork dw = optDayWork.get();
                dw.setDeleteDt(LocalDateTime.now());
                dayWorkRepo.save(dw);

                result.put("code", "success");
                result.put("url", "/dayWork/list");
            }
        } catch (Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 일일보고 등록
    public Map<String, Object> addDayWork(String insertForm, List<MultipartFile> files, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            dayWork dw = dayWork.builder()
                    .dayWorkTit(param.get("dayWorkTit").toString().trim())
                    .dayWorkCont(param.get("dayWorkCont").toString())
                    .dayWorkWrtDt(LocalDateTime.now())
                    .createDt(LocalDateTime.now())
                    .dayWorkWrtStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
                    .build();

            Long dayWorkNo = dayWorkRepo.save(dw).getDayWorkNo();

            if (files != null) {
                for (int i=0; i<files.size(); i++) {
                    String ftpName = fileUtil.uploadFile(files.get(i), "dayWorkFiles");
                    attachment atc = attachment.builder()
                            .atcOriNm(files.get(i).getOriginalFilename())
                            .atcUpNm(ftpName)
                            .createDt(LocalDateTime.now())
                            .build();

                    Long atcNo = atcRepo.save(atc).getAtcNo();

                    dayWorkFiles dayWorkFile = dayWorkFiles.builder()
                            .dayWorkNo(dayWorkNo)
                            .atcNo(atcNo)
                            .build();

                    dayWorkFileRepo.save(dayWorkFile);

                }
            }

            result.put("code", "succeess");
            result.put("msg", "업무를 등록하였습니다.");
            result.put("url", "/dayWork/detail?dayWorkNo="+dayWorkNo);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "일일 보고 등록에 실패하였습니다.");
        }
        return result;
    }

    // 일일보고 수정
    public Map<String, Object> editDayWork(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<dayWork> optDayWork = dayWorkRepo.findById(Long.parseLong(updateForm.get("dayWorkNo").toString()));

            if (optDayWork.isEmpty()) {
                result.put("code", "fail");
                result.put("msg", "일일 보고 수정에 실패하였습니다.");
            }
            dayWork dw = optDayWork.get();

            dw.setUpdateDt(LocalDateTime.now());
            dw.setDayWorkCont(updateForm.get("dayWorkCont").toString());
            dw.setDayWorkTit(updateForm.get("dayWorkTit").toString());

            dayWorkRepo.save(dw);

            result.put("code", "success");
            result.put("url", "/dayWork/detail?dayWorkNo=" + updateForm.get("dayWorkNo"));
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code", "fail");
            result.put("msg", "일일 보고 수정에 실패하였습니다.");
        }
         return result;
    }



}
