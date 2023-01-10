package com.mmp.groupware.domain.compSpl.cs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.attachment.attachRepository;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.domain.compSpl.csFile.csFiles;
import com.mmp.groupware.domain.compSpl.csFile.csFilesRepository;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.compSpl.dto.csDto;
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
public class csService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final csMapper csMapper;
    private final csRepository csRepo;

    // 파일 관련
    private final attachRepository atcRepo;
    private final csFilesRepository csFileRepo;

    // 물품 수 조회
    public int getCsCnt(Map<String, Object> search) {
        return csMapper.getCsCnt(search);
    }

    // 물품 목록 조회
    public List<csDto> getCsList(Map<String, Object> search) {
        return csMapper.getCsList(search);
    }

    // 물품 상세 조회
    public csDto getCsDetail(long csNo) {
        csDto csDto = null;
        try {
            csDto = csMapper.getCsDetail(csNo);
        }catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return csDto;
    }

    // 물품 삭제
    public Map<String, Object> deleteCs(Map<String, Object> param) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Optional<cs> optCs = csRepo.findById(Long.parseLong(param.get("csNo").toString()));

            if(optCs.isPresent()) {
                cs cs = optCs.get();
                cs.setDeleteDt(LocalDateTime.now());
                csRepo.save(cs);

                result.put("code", "success");
                result.put("url", "/cs/list");
            }
        }catch (Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 물품 등록
    public Map<String, Object> addCs(String insertForm, List<MultipartFile> files, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            LocalDate csPcsDt = LocalDate.parse((CharSequence) param.get("csPcsDt"));

            // 1. 물품 글 등록
            cs cscs = cs.builder()
                    .atclDivNo((Long) param.get("atclDivNo"))
                    .atclPstNo((Long) param.get("atclPstNo"))
                    .csNm(param.get("csNm").toString().trim())
                    .csStd(param.get("csStd").toString())
                    .csCnt((Long) param.get("csCnt"))
                    .csPcsDt(csPcsDt.atTime(0, 0, 0, 0))
                    .csPcsPay((Long) param.get("csPcsPay"))
                    .csComm(param.get("csComm").toString())
                    .createDt(LocalDateTime.now())
                    .build();

            Long csNo = csRepo.save(cscs).getCsNo();

            // 2. 물품 글 파일 등록
            if(files != null) {
                for(int i=0; i<files.size(); i++) {
                    String ftpName = fileutil.uploadFile(files.get(i), "csFiles");
                    attachment atc = attachment.builder()
                            .atcOriNm(files.get(i).getOriginalFilename())
                            .atcUpNm(ftpName)
                            .createDt(LocalDateTime.now())
                            .build();

                    Long atcNo = atcRepo.save(atc).getAtcNo();

                    csFiles csFile = csFiles.builder()
                            .csNo(csNo)
                            .atcNo(atcNo)
                            .build();

                    csFileRepo.save(csFile);
                }
            }

            result.put("code", "success");
            result.put("msg", "물품을 등록하였습니다.");
            result.put("url", "/cs/detail?csNo="+csNo);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "물품 등록에 실패하였습니다");
        }

        return result;
    }

    // 물품 수정
    public Map<String, Object> editCs(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<cs> optCs = csRepo.findById(Long.parseLong(updateForm.get("csNo").toString()));

            if(optCs.isEmpty()) {
                result.put("code","fail");
                result.put("msg","물품 정보 수정에 실패하였습니다.");
            }

            cs cscs = optCs.get();
            LocalDate csPcsDt = LocalDate.parse((CharSequence) updateForm.get("csPcsDt"));

            cscs.setUpdateDt(LocalDateTime.now());
            cscs.setAtclDivNo((Long) updateForm.get("atclDivNo"));
            cscs.setAtclPstNo((Long) updateForm.get("atclPstNo"));
            cscs.setCsNm(updateForm.get("csNm").toString());
            cscs.setCsStd(updateForm.get("csStd").toString());
            cscs.setCsCnt((Long) updateForm.get("csCnt"));
            cscs.setCsPcsDt(csPcsDt.atTime(0, 0, 0, 0));
            cscs.setCsPcsPay((Long) updateForm.get("csPcsPay"));
            cscs.setCsComm(updateForm.get("csComm").toString());

            csRepo.save(cscs);

            result.put("code", "success");
            result.put("url", "/cs/detail?csNo=" + updateForm.get("csNo"));

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","물품 정보 수정에 실패하였습니다.");
        }

        return result;
    }

}
