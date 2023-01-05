package com.mmp.groupware.domain.appr.apprDiv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mmp.groupware.domain.appr.appr;
import com.mmp.groupware.domain.appr.apprDivFile.apprDivFiles;
import com.mmp.groupware.domain.appr.apprDivFile.apprDivFilesRepository;
import com.mmp.groupware.domain.appr.apprRefer.apprRefer;
import com.mmp.groupware.domain.attachment.attachRepository;
import com.mmp.groupware.domain.attachment.attachment;
import com.mmp.groupware.util.fileUtil;
import com.mmp.groupware.util.sessionUtil;
import com.mmp.groupware.web.appr.dto.apprDivDto;
import com.mmp.groupware.web.appr.dto.apprReferDto;
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
public class apprDivService {

    private final fileUtil fileutil;
    private final sessionUtil sess;

    private final apprDivMapper apprDivMapper;
    private final apprDivRepository apprDivRepo;

    // 파일 관련
    private final attachRepository atcRepo;
    private final apprDivFilesRepository apprDivFileRepo;

    // 전자결재 구분 수 조회
    public int getApprDivCnt(Map<String, Object> search) {
        return apprDivMapper.getApprDivCnt(search);
    }

    // 전자결재 구분 목록 조회
    public List<apprDivDto> getApprDivList(Map<String, Object> search){
        return apprDivMapper.getApprDivList(search);
    }

    // 전자결재 구분 상세 조회
    public apprDivDto getApprDivDetail(long apprDivNo) {
        apprDivDto apprDivDto = null;
        try {
            apprDivDto = apprDivMapper.getApprDivDetail(apprDivNo);
        }catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return apprDivDto;
    }

    // 전자결재 구분 삭제
    public Map<String, Object> deleteApprDiv(Map<String, Object> param){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            Optional<apprDiv> optApprDiv = apprDivRepo.findById(Long.parseLong(param.get("apprDivNo").toString()));

            if(optApprDiv.isPresent()) {
                apprDiv apd = optApprDiv.get();
                apd.setDeleteDt(LocalDateTime.now());
                apprDivRepo.save(apd);

                result.put("code", "success");
                result.put("url", "/apprDiv/list");
            }

        }catch(Exception e) {
            result.put("code", "fail");
        }
        return result;
    }

    // 전자결재 구분 등록
    public Map<String, Object> addApprDiv(String insertForm, List<MultipartFile> files, HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String,Object> param = mapper.readValue(insertForm, Map.class);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

            // 1. 전자결재 구분 글 등록
            apprDiv apd = apprDiv.builder()
                    .apprDivNm(param.get("apprDivNm").toString().trim())
                    .apprDivWrtDt(LocalDateTime.now())
                    .createDt(LocalDateTime.now())
                    .build();

            Long apprDivNo = apprDivRepo.save(apd).getApprDivNo();

            // 2. 기안 글 파일 등록
            if(files != null) {
                for(int i=0; i<files.size(); i++) {
                    String ftpName = fileutil.uploadFile(files.get(i), "apprDivFiles");
                    attachment atc = attachment.builder()
                            .atcOriNm(files.get(i).getOriginalFilename())
                            .atcUpNm(ftpName)
                            .createDt(LocalDateTime.now())
                            .build();

                    Long atcNo = atcRepo.save(atc).getAtcNo();

                    apprDivFiles apprDivFile = apprDivFiles.builder()
                            .apprDivNo(apprDivNo)
                            .atcNo(atcNo)
                            .build();

                    apprDivFileRepo.save(apprDivFile);
                }
            }

            result.put("code", "success");
            result.put("msg", "전자결재 구분을 등록하였습니다.");
            result.put("url", "/apprDiv/detail?apprDivNo="+apprDivNo);

        }catch (Exception e) {
            System.out.println(e.getMessage());
            result.put("code", "fail");
            result.put("msg", "전자결재 구분 등록에 실패하였습니다");
        }

        return result;
    }

    // 결재문서 구분 수정
    public Map<String, Object> editApprDiv(Map<String, Object> updateForm, HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            Optional<apprDiv> optApprDiv = apprDivRepo.findById(Long.parseLong(updateForm.get("apprDivNo").toString()));

            if(optApprDiv.isEmpty()) {
                result.put("code","fail");
                result.put("msg","전자결재 구분 정보 수정에 실패하였습니다.");
            }

            apprDiv apd = optApprDiv.get();

            apd.setUpdateDt(LocalDateTime.now());
            apd.setApprDivNm(updateForm.get("apprDivNm").toString());

            apprDivRepo.save(apd);

            result.put("code", "success");
            result.put("url", "/apprDiv/detail?apprDivNo=" + updateForm.get("apprDivNo"));

        }catch (Exception e) {
            e.printStackTrace();
            result.put("code","fail");
            result.put("msg","전자결재 구분 정보 수정에 실패하였습니다.");
        }

        return result;
    }

}
