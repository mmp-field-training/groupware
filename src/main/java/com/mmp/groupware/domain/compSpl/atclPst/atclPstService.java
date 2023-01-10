package com.mmp.groupware.domain.compSpl.atclPst;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.compSpl.dto.atclPstDto;
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
public class atclPstService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final atclPstMapper apMapper;
    private final atclPstRepository apRepo;

    // 물품위치 수 조회
    public int getAtclPstCnt(Map<String, Object> search) {
        return apMapper.getAtclPstCnt(search);
    }

    // 물품위치 목록 조회
    public List<atclPstDto> getAtclPstList(Map<String, Object> search) {
        return apMapper.getAtclPstList(search);
    }

    // 물품위치 상세 조회
    public atclPstDto getAtclPstDetail(long atclPstNo) {
        atclPstDto atclPstDto = null;
        try{
            atclPstDto = apMapper.getAtclPstDetail(atclPstNo);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return atclPstDto;
    }

    // 물품위치 삭제
    public Map<String, Object> deleteAtclPst(Map<String, Object> param) {
        Map<String,Object> result = new HashMap<String, Object>();

        try {
            Optional<atclPst> optAtclPst = apRepo.findById(Long.parseLong(param.get("atclPstNo").toString()));

            if(optAtclPst.isPresent()) {
                atclPst ap = optAtclPst.get();
                ap.setDeleteDt(LocalDateTime.now());
                apRepo.save(ap);

                result.put("code", "success");
                result.put("url", "/atclPst/list");
            }

        }catch (Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 물품위치 등록
    public Map<String, Object> addAtclPst(String insertForm, List<MultipartFile> files, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            // 1. 물품위치 글 등록
            atclPst ap = atclPst.builder()
                    .atclPstNm(param.get("atclPstNm").toString().trim())
                    .atclPstUsedYn(param.get("atclPstUsedYn").toString())
                    .atclPstComm(param.get("atclPstComm").toString())
                    .createDt(LocalDateTime.now())
                    .build();

            Long atclPstNo = apRepo.save(ap).getAtclPstNo();

            result.put("code", "success");
            result.put("msg", "물품 위치를 등록하였습니다.");
            result.put("url", "/atclPst/detail?atclPstNo="+atclPstNo);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "물품 위치 등록에 실패하였습니다");
        }

        return result;
    }

    // 물품 위치 수정
    public Map<String, Object> editAtclPst(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<atclPst> optAp = apRepo.findById(Long.parseLong(updateForm.get("atclPstNo").toString()));

            if(optAp.isEmpty()) {
                result.put("code","fail");
                result.put("msg","물품 구분 수정에 실패하였습니다.");
            }

            atclPst ap = optAp.get();

            ap.setUpdateDt(LocalDateTime.now());
            ap.setAtclPstNm(updateForm.get("atclPstNm").toString());
            ap.setAtclPstUsedYn(updateForm.get("atclPstUsedYn").toString());
            ap.setAtclPstComm(updateForm.get("atclPstComm").toString());

            apRepo.save(ap);

            result.put("code", "success");
            result.put("url", "/atclPst/detail?atclPstNo=" + updateForm.get("atclPstNo"));

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","물품 위치 수정에 실패하였습니다.");
        }

        return result;
    }

}
