package com.mmp.groupware.domain.compSpl.atclDiv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.compSpl.dto.atclDivDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class atclDivService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final atclDivMapper adMapper;
    private final atclDivRepository adRepo;

    // 물품그룹 수 조회
    public int getAtclDivCnt(Map<String, Object> search) {
        return adMapper.getAtclDivCnt(search);
    }

    // 물품그룹 목록 조회
    public List<atclDivDto> getAtclDivList(Map<String, Object> search) {
        return adMapper.getAtclDivList(search);
    }

    // 물품그룹 상세 조회
    public atclDivDto getAtclDivDetail(long atclDivNo) {
        atclDivDto atclDivDto = null;
        try {
            atclDivDto = adMapper.getAtclDivDetail(atclDivNo);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return atclDivDto;
    }

    // 물품그룹 삭제
    public Map<String, Object> deleteAtclDiv(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<String, Object>();

        try {
            Optional<atclDiv> optAtclDiv = adRepo.findById(Long.parseLong(param.get("atclDivNo").toString()));

            if(optAtclDiv.isPresent()) {
                atclDiv ad = optAtclDiv.get();
                ad.setDeleteDt(LocalDateTime.now());
                adRepo.save(ad);

                result.put("code", "success");
                result.put("url", "/atclDiv/list");
            }

        }catch (Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 물품그룹 등록
    public Map<String, Object> addAtclDiv(String insertForm, List<MultipartFile> files, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            // 1. 물품그룹 글 등록
            atclDiv ad = atclDiv.builder()
                    .atclDivNm(param.get("atclDivNm").toString().trim())
                    .atclDivUsedYn(param.get("atclDivUsedYn").toString())
                    .atclDivComm(param.get("atclDivComm").toString())
                    .createDt(LocalDateTime.now())
                    .build();

            Long atclDivNo = adRepo.save(ad).getAtclDivNo();

            result.put("code", "success");
            result.put("msg", "물품 그룹을 등록하였습니다.");
            result.put("url", "/atclDiv/detail?atclDivNo="+atclDivNo);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "물품 그룹 등록에 실패하였습니다");
        }

        return result;
    }

    // 물품 그룹 수정
    public Map<String, Object> editAtclDiv(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<atclDiv> optAd = adRepo.findById(Long.parseLong(updateForm.get("atclDivNo").toString()));

            if(optAd.isEmpty()) {
                result.put("code","fail");
                result.put("msg","물품 구분 수정에 실패하였습니다.");
            }

            atclDiv ad = optAd.get();

            ad.setUpdateDt(LocalDateTime.now());
            ad.setAtclDivNm(updateForm.get("atclDivNm").toString());
            ad.setAtclDivUsedYn(updateForm.get("atclDivUsedYn").toString());
            ad.setAtclDivComm(updateForm.get("atclDivComm").toString());

            adRepo.save(ad);

            result.put("code", "success");
            result.put("url", "/atclDiv/detail?atclDivNo=" + updateForm.get("atclDivNo"));

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","물품 구분 수정에 실패하였습니다.");
        }

        return result;
    }

}
