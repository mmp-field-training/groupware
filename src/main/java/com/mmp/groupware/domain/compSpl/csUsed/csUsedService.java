package com.mmp.groupware.domain.compSpl.csUsed;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.compSpl.dto.csUsedDto;
import lombok.RequiredArgsConstructor;
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
public class csUsedService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final csUsedMapper csUsedMapper;
    private final csUsedRepository csUsedRepo;

    // 물품 사용이력 수 조회
    public int getCsUsedCnt(Map<String, Object> search) {
        return csUsedMapper.getCsUsedCnt(search);
    }

    // 물품 사용이력 목록 조회
    public List<csUsedDto> getCsUsedList(Map<String, Object> search) {
        return csUsedMapper.getCsUsedList(search);
    }

    // 물품 사용이력 상세 조회
    public csUsedDto getCsUsedDetail(long csUsedNo) {
        csUsedDto cuDto = null;
        try {
            cuDto = csUsedMapper.getCsUsedDetail(csUsedNo);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return cuDto;
    }

    // 물품 사용이력 삭제
    public Map<String, Object> deleteCsUsed(Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            Optional<csUsed> optCsUsed = csUsedRepo.findById(Long.parseLong(param.get("csUsedNo").toString()));

            if(optCsUsed.isPresent()) {
                csUsed cu = optCsUsed.get();
                cu.setDeleteDt(LocalDateTime.now());
                csUsedRepo.save(cu);

                result.put("code", "success");
                result.put("url", "/csUsed/list");
            }

        }catch(Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 물품 사용이력 등록
    public Map<String, Object> addCsUsed(String insertForm, List<MultipartFile> files, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try{
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            LocalDate csUsedDt = LocalDate.parse((CharSequence) param.get("csUsedDt"));

            // 1. 물품 사용이력 글 등록
            csUsed cu = csUsed.builder()
                    .csNo((Long) param.get("csNo"))
                    .csUsedTit(param.get("csUsedTit").toString().trim())
                    .csUsedCont(param.get("csUsedCont").toString())
                    .csUsedDt(csUsedDt.atTime(0,0,0,0))
                    .createDt(LocalDateTime.now())
                    .csUsedStfNo(Long.parseLong(sess.sessionGet(request, "stfNo")))
                    .build();

            Long csUsedNo = csUsedRepo.save(cu).getCsUsedNo();

            result.put("code", "success");
            result.put("msg", "물품 사용이력을 등록하였습니다.");
            result.put("url", "/csUsed/detail?csUsedNo="+csUsedNo);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "물품 사용이력 등록에 실패하였습니다");
        }

        return result;
    }

    // 물품 사용이력 수정
    public Map<String, Object> editCsUsed(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<csUsed> optCu = csUsedRepo.findById(Long.parseLong(updateForm.get("csUsedNo").toString()));

            if(optCu.isEmpty()) {
                result.put("code","fail");
                result.put("msg","물품 사용이력 정보 수정에 실패하였습니다.");
            }

            csUsed cu = optCu.get();
            LocalDate csUsedDt = LocalDate.parse((CharSequence) updateForm.get("csUsedDt"));

            cu.setUpdateDt(LocalDateTime.now());
            cu.setCsNo((Long) updateForm.get("csNo"));
            cu.setCsUsedTit(updateForm.get("csUsedTit").toString());
            cu.setCsUsedCont(updateForm.get("csUsedCont").toString());
            cu.setCsUsedDt(csUsedDt.atTime(0,0,0,0));

            csUsedRepo.save(cu);

            result.put("code", "success");
            result.put("url", "/csUsed/detail?csUsedNo=" + updateForm.get("csUsedNo"));

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","물품 사용이력 정보 수정에 실패하였습니다.");
        }

        return result;
    }

}
